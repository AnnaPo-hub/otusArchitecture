package ru.otus;

import java.util.Queue;

public interface IExceptionHandler {
    void handle(ICommand cmd, Exception exc, Queue<ICommand> queue);
}
