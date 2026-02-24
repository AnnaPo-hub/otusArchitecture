package ru.otus.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.command.BurnFuelCommand;
import ru.otus.command.ICommand;
import ru.otus.container.factories.IFactory;
import ru.otus.container.factories.RegisterFactory;
import ru.otus.container.factories.ScopeCurrentFactory;
import ru.otus.container.factories.ScopeNewFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IoCTest {
    private Scope root;

    @BeforeEach
    void setUp() {
        root = new Scope(new HashMap<>(), null);

        root.register("IoC.Register", new RegisterFactory());
        root.register("Scopes.New", new ScopeNewFactory());
        root.register("Scopes.Current", new ScopeCurrentFactory());

        IoC.init(root);

        assertNotNull(IoC.getCurrentScope());
    }

    @Test
    void bootstrap_allowsResolvingBaseCommands() {
        ICommand newScopeCmd = IoC.resolve("Scopes.New", "A");
        assertNotNull(newScopeCmd);

        ICommand currentScopeCmd = IoC.resolve("Scopes.Current", "A");
        assertNotNull(currentScopeCmd);

        // Для register нужно 2 аргумента: key и factory
        IFactory dummyFactory = args -> "ok";
        ICommand registerCmd = IoC.resolve("IoC.Register", "X", dummyFactory);
        assertNotNull(registerCmd);
    }

    @Test
    void scopesNew_putsScopeIntoCatalogue() throws Exception {
        Map<String, Scope> catalogueBefore = IoC.getCatalogue();
        assertFalse(catalogueBefore.containsKey("A"));

        IoC.<ICommand>resolve("Scopes.New", "A").execute();

        Map<String, Scope> catalogueAfter = IoC.getCatalogue();
        assertTrue(catalogueAfter.containsKey("A"));
        assertNotNull(catalogueAfter.get("A"));
    }

    @Test
    void scopesCurrent_switchesCurrentScope() throws Exception {
        Scope before = IoC.getCurrentScope();

        IoC.<ICommand>resolve("Scopes.New", "A").execute();
        Scope scopeA = IoC.getCatalogue().get("A");
        assertNotNull(scopeA);

        IoC.<ICommand>resolve("Scopes.Current", "A").execute();
        Scope after = IoC.getCurrentScope();

        assertSame(scopeA, after);
        assertNotSame(before, after);
    }

    @Test
    void iocRegister_registersDependencyInCurrentScope() throws Exception {
        IoC.<ICommand>resolve("Scopes.New", "A").execute();
        IoC.<ICommand>resolve("Scopes.Current", "A").execute();

        IFactory xFactory = args -> "hello";
        IoC.<ICommand>resolve("IoC.Register", "X", xFactory).execute();

        // Проверяем, что  "X" резолвится
        String resolved = IoC.resolve("X");
        assertEquals("hello", resolved);
    }

    @Test
    void canRegisterAndExecuteGameCommand_viaLambdaFactory() throws Exception {
        IoC.<ICommand>resolve("Scopes.New", "A").execute();
        IoC.<ICommand>resolve("Scopes.Current", "A").execute();

        // Регистрируем "BurnFuel" как фабрику-команду через лямбду
        IFactory burnFuelFactory = args -> {
            // ожидаем один аргумент: IFuelable
            ru.otus.IFuelable fuelable = (ru.otus.IFuelable) args[0];
            return new BurnFuelCommand(fuelable);
        };
        IoC.<ICommand>resolve("IoC.Register", "BurnFuel", burnFuelFactory).execute();

        TestFuelable ship = new TestFuelable(10.0, 2.5);

        ICommand burn = IoC.resolve("BurnFuel", ship);
        burn.execute();

        assertEquals(7.5, ship.getFuel(), 1e-9);
    }

    @Test
    void scopesCurrent_throwsIfScopeIdNotFound() {
        ICommand cmd = IoC.resolve("Scopes.Current", "NO_SUCH_SCOPE");

        Exception ex = assertThrows(Exception.class, cmd::execute);
        assertTrue(ex.getMessage().toLowerCase().contains("not found"));
    }
    static class TestFuelable implements ru.otus.IFuelable {
        private double fuel;
        private final double burnRate;

        TestFuelable(double fuel, double burnRate) {
            this.fuel = fuel;
            this.burnRate = burnRate;
        }

        @Override
        public double getFuel() {
            return fuel;
        }

        @Override
        public double getFuelBurnRate() {
            return burnRate;
        }

        @Override
        public void setFuel(double newFuelQuantity) {
            this.fuel = newFuelQuantity;
        }
    }
}