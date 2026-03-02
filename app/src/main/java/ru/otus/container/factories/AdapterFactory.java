package ru.otus.container.factories;

import ru.otus.command.AdapterCommand;
import ru.otus.container.IoC;

public class AdapterFactory implements IFactory {
    @Override
    public Object create(Object... args) {
        if (args.length != 2) {
            throw new RuntimeException("IoC.Adapter expects 2 arguments (String key, Class<?> adapterClass)");
        }
        String key = (String) args[0];
        Class<?> adapterClass = (Class<?>) args[1];
        return new AdapterCommand(key, adapterClass, IoC.getCurrentScope());
    }
}
