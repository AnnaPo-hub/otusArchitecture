package ru.otus.handlers;

import ru.otus.command.ICommand;
import ru.otus.command.RetryOnceCommand;

import java.util.Queue;

public class RetryOnceHandler implements IExceptionHandler {
    @Override
    public void handle(ICommand cmd, Exception exc, Queue<ICommand> queue) {
        queue.add(new RetryOnceCommand(cmd));
    }
}
