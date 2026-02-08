package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class RetryOnceHandlerTest {
    Queue<ICommand> queue;
    RetryOnceHandler retryOnceHandler;
    ICommand cmd;

    @BeforeEach
    void setup() {
        queue = new ArrayDeque<>();
        retryOnceHandler = new RetryOnceHandler();
        cmd = new SuccessCommand();
    }

    @Test
    void shouldEnqueueRetryOnceCommand() {
        retryOnceHandler.handle(cmd, new Exception(), queue);
        assertFalse(queue.isEmpty());
        assertTrue(queue.peek() instanceof RetryOnceCommand);
        assertSame(((RetryOnceCommand) queue.peek()).getOriginalCommand(), cmd);
    }

    static class SuccessCommand implements ICommand {
        @Override
        public void execute() {
            ///
        }
    }
}