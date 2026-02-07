package ru.otus;

import java.util.Queue;

public class RetryOnceHandler implements IExceptionHandler {
    @Override
    public void handle(ICommand cmd, Exception exc, Queue<ICommand> queue) {
        queue.add(new RetryOnceCommand(cmd));
    }
}
