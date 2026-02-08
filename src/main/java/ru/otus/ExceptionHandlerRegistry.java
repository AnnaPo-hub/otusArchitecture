package ru.otus;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import ru.otus.command.ICommand;
import ru.otus.handlers.IExceptionHandler;

public class ExceptionHandlerRegistry {

    private final Table<
            Class<? extends ICommand>,
            Class<? extends Exception>,
            IExceptionHandler> table = HashBasedTable.create();

    public void register(
            Class<? extends ICommand> cmdClass,
            Class<? extends Exception> excClass,
            IExceptionHandler handler) {

        table.put(cmdClass, excClass, handler);
    }

    public IExceptionHandler resolve(ICommand cmd, Exception exc) {

        Class<? extends ICommand> cmdClass = cmd.getClass();
        Class<? extends Exception> excClass = exc.getClass();

        IExceptionHandler handler = table.get(cmdClass, excClass);
        if (handler == null) {
            handler = table.get(cmdClass, Exception.class);
        }
        if (handler == null) {
            throw new RuntimeException(
                    "Handler not found for " +
                            cmdClass.getSimpleName() +
                            " and " +
                            excClass.getSimpleName());
        }

        return handler;
    }
}
