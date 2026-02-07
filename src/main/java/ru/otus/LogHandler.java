package ru.otus;

import java.util.Queue;

public class LogHandler implements IExceptionHandler {
    @Override
    public void handle(ICommand cmd, Exception exc, Queue<ICommand> queue) {
        queue.add(new LogExceptionCommand(cmd, exc));
    }
}
