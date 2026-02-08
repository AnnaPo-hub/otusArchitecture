package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogHandlerTest {
    Queue<ICommand> queue;
    LogHandler logHandler;

    @BeforeEach
    void setup() {
        queue = new ArrayDeque<>();
        logHandler = new LogHandler();
    }

    @Test
    void shouldAddLogCommandToQueue() {
        logHandler.handle(new SuccessCommand(), new Exception(), queue);
        assertFalse(queue.isEmpty());
        assertTrue(queue.peek() instanceof LogExceptionCommand);
    }

    static class SuccessCommand implements ICommand {
        @Override
        public void execute() {
            ///
        }
    }
}