package ru.otus.container.factories;

import ru.otus.command.RegisterCommand;
import ru.otus.container.IoC;

public class RegisterFactory implements IFactory {
    @Override
    public Object create(Object... args) {
        if (args.length != 2) {
            throw new RuntimeException("IoC.Register expects 2 arguments (String key, IFactory factoryToRegister)");
        }
        String key = (String) args[0];
        IFactory factoryToRegister = (IFactory) args[1];
        return new RegisterCommand(key, factoryToRegister, IoC.getCurrentScope());
    }
}
