package ru.otus;

import java.util.Queue;

public class LogHandler implements IExceptionHandler {
    @Override
    public void handle(ICommand cmd, Exception exc, Queue<ICommand> queue) {
        if (cmd instanceof RetryOnceCommand) {
            queue.add(new LogExceptionCommand(((RetryOnceCommand) cmd).getOriginalCommand(), exc));
        } else {
            queue.add(new LogExceptionCommand(cmd, exc));

        }
    }
}
