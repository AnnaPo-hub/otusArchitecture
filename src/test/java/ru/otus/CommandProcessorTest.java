package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandProcessorTest {
    Queue<ICommand> queue;
    ExceptionHandlerRegistry registry;
    CommandProcessor processor;

    @BeforeEach
    void setup() {
        queue = new ArrayDeque<>();
        registry = new ExceptionHandlerRegistry();

        registry.register(FailingCommand.class, Exception.class, new RetryOnceHandler());
        registry.register(RetryOnceCommand.class, Exception.class, new LogHandler());

        processor = new CommandProcessor(queue, registry);
    }

    @Test
    void shouldRetryOnceThenLogOnSecondFailure() {
        queue.add(new FailingCommand());
        assertTrue(processor.processCommand());
        assertFalse(queue.isEmpty());
        assertTrue(queue.peek() instanceof RetryOnceCommand);
        assertTrue(processor.processCommand());
        assertFalse(queue.isEmpty());
        assertTrue(queue.peek() instanceof LogExceptionCommand);
    }

    static class FailingCommand implements ICommand {
        @Override
        public void execute() throws Exception {
            throw new Exception("boom");
        }
    }
}


