package ru.otus.handlers;

import ru.otus.command.ICommand;

import java.util.Queue;

public interface IExceptionHandler {
    void handle(ICommand cmd, Exception exc, Queue<ICommand> queue);
}
