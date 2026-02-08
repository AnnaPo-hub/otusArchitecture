package ru.otus;

import java.util.Queue;

public class RetryTwiceHandler implements IExceptionHandler {
    @Override
    public void handle(ICommand cmd, Exception exc, Queue<ICommand> queue) {
        if (cmd instanceof RetryOnceCommand) {
            queue.add(new RetryTwiceCommand(((RetryOnceCommand) cmd).getOriginalCommand()));
        } else {
            queue.add(new RetryTwiceCommand(cmd));
        }
    }
}
