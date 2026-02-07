package ru.otus;

import java.util.Queue;

//CommandProcessor должен уметь только: достать команду , выполнить, поймать Exception, передать обработчику
public class CommandProcessor {
    Queue<ICommand> queue;
    ExceptionHandlerRegistry registry;

    public CommandProcessor(Queue<ICommand> queue, ExceptionHandlerRegistry registry) {
        this.queue = queue;
        this.registry = registry;
    }

    public void addCommand(ICommand cmd) {
        queue.add(cmd);
    }

    public boolean processCommand() {
        ICommand currCmd = queue.poll();
        if (currCmd == null) {
            return false;
        }
        try {
            currCmd.execute();

        } catch (Exception exc) {
            IExceptionHandler handler = registry.resolve(currCmd, exc);
            handler.handle(currCmd, exc, queue);
        }
        return true;
    }
}

