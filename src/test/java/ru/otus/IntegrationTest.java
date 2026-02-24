package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.command.ICommand;
import ru.otus.command.LogExceptionCommand;
import ru.otus.command.RetryOnceCommand;
import ru.otus.command.RetryTwiceCommand;
import ru.otus.handlers.LogHandler;
import ru.otus.handlers.RetryOnceHandler;
import ru.otus.handlers.RetryTwiceHandler;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTest {
    Queue<ICommand> queue;
    ExceptionHandlerRegistry registry;
    CommandProcessor processor;

    @BeforeEach
    void setup() {
        queue = new ArrayDeque<>();
        registry = new ExceptionHandlerRegistry();

        registry.register(FailingCommand.class, Exception.class, new RetryOnceHandler());
        registry.register(RetryOnceCommand.class, Exception.class, new RetryTwiceHandler());
        registry.register(RetryTwiceCommand.class, Exception.class, new LogHandler());

        processor = new CommandProcessor(queue, registry);
    }


    @Test
    void shouldRetryTwiceThenLogOnThirdFailure() {
        queue.add(new FailingCommand());
        assertTrue(processor.processCommand());
        assertFalse(queue.isEmpty());
        assertTrue(queue.peek() instanceof RetryOnceCommand);
        assertTrue(processor.processCommand());
        assertFalse(queue.isEmpty());
        assertTrue(queue.peek() instanceof RetryTwiceCommand);
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


