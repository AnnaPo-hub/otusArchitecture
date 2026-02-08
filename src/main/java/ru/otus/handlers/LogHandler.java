package ru.otus.handlers;

import ru.otus.command.ICommand;
import ru.otus.command.LogExceptionCommand;
import ru.otus.command.RetryOnceCommand;
import ru.otus.command.RetryTwiceCommand;

import java.util.Queue;

public class LogHandler implements IExceptionHandler {
    @Override
    public void handle(ICommand cmd, Exception exc, Queue<ICommand> queue) {
        if (cmd instanceof RetryOnceCommand) {
            queue.add(new LogExceptionCommand(((RetryOnceCommand) cmd).getOriginalCommand(), exc));
        } else if (cmd instanceof RetryTwiceCommand) {
            queue.add(new LogExceptionCommand(((RetryTwiceCommand) cmd).getOriginalCommand(), exc));
        } else {
            queue.add(new LogExceptionCommand(cmd, exc));
        }
    }
}
