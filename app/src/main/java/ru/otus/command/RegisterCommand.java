package ru.otus.command;

import ru.otus.container.factories.IFactory;
import ru.otus.container.Scope;
import ru.otus.exception.CommandException;

public class RegisterCommand implements ICommand {
    private final String key;
    private final IFactory factory;
    private final Scope scope;


    public RegisterCommand(String key, IFactory factory, Scope scope) {
        this.key = key;
        this.factory = factory;
        this.scope = scope;
    }

    @Override
    public void execute() throws CommandException {
        scope.register(key, factory);
    }
}
