package ru.otus.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.command.ICommand;
import ru.otus.container.factories.IFactory;
import ru.otus.container.factories.RegisterFactory;
import ru.otus.container.factories.ScopeCurrentFactory;
import ru.otus.container.factories.ScopeNewFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class IoCContainerMultithreadTest {
    private Scope root;

    @BeforeEach
    void setUp() {

        root = new Scope(new HashMap<>(), null);

        root.register("IoC.Register", new RegisterFactory());
        root.register("Scopes.New", new ScopeNewFactory());
        root.register("Scopes.Current", new ScopeCurrentFactory());

        IoC.init(root);

        assertNotNull(IoC.getCurrentScope());
        assertNotNull(IoC.getCatalogue());
    }

    private static void exec(ICommand cmd) {
        try {
            cmd.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void runInThread(String name, Runnable r, AtomicReference<Throwable> err, CountDownLatch done) {
        new Thread(() -> {
            try {
                r.run();
            } catch (Throwable t) {
                err.compareAndSet(null, t);
            } finally {
                done.countDown();
            }
        }, name).start();
    }

    @Test
    void catalogue_isThreadLocal_scopesDoNotLeakBetweenThreads() throws Exception {
        CountDownLatch t1Created = new CountDownLatch(1);
        CountDownLatch t2Checked = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(2);
        AtomicReference<Throwable> err = new AtomicReference<>(null);

        runInThread("T1", () -> {
            exec(IoC.resolve("Scopes.New", "A"));

            Map<String, Scope> cat = IoC.getCatalogue();
            assertTrue(cat.containsKey("A"), "T1 should have scope A in its catalogue");
            t1Created.countDown();

            try {
                t2Checked.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, err, done);

        runInThread("T2", () -> {
            try {
                t1Created.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Map<String, Scope> cat = IoC.getCatalogue();
            assertFalse(cat.containsKey("A"), "T2 must NOT see scope A created in T1");
            t2Checked.countDown();
        }, err, done);

        done.await();
        if (err.get() != null) throw new RuntimeException(err.get());
    }

    @Test
    void currentScope_isThreadLocal_registrationInOneThreadDoesNotAffectOther() throws Exception {
        CountDownLatch t1Registered = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(2);
        AtomicReference<Throwable> err = new AtomicReference<>(null);

        runInThread("T1", () -> {
            exec(IoC.resolve("Scopes.New", "A"));
            exec(IoC.resolve("Scopes.Current", "A"));

            IFactory xFactory = a -> "fromT1";
            exec(IoC.resolve("IoC.Register", "X", xFactory));

            String v = IoC.resolve("X");
            assertEquals("fromT1", v);

            t1Registered.countDown();
        }, err, done);

        runInThread("T2", () -> {
            try {
                t1Registered.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertThrows(RuntimeException.class, () -> IoC.resolve("X"),
                    "T2 must NOT resolve X registered in T1 current scope");
        }, err, done);

        done.await();
        if (err.get() != null) throw new RuntimeException(err.get());
    }
}
