package ru.otus.container;

import ru.otus.command.ScopeNewCommand;

public class ScopeNewFactory implements IFactory {
    @Override
    public Object create(Object... args) {
        if (args.length != 1) {
            throw new RuntimeException("IoC.ScopeNew expects 1 argument: String scopeId");
        }
        String scopeId = (String) args[0];
        return new ScopeNewCommand(scopeId, IoC.getCurrentScope(), IoC.getCatalogue());
    }
}
