package ru.otus.container;

import ru.otus.command.ScopeCurrentCommand;

public class ScopeCurrentFactory implements IFactory {
    @Override
    public Object create(Object... args) {
        if (args.length != 1) {
            throw new RuntimeException("IoC.ScopeCurrent expects 1 argument: String scopeId");
        }
        String scopeId = (String) args[0];
        return new ScopeCurrentCommand(scopeId, IoC.getCurrentScopeHolder(), IoC.getCatalogue());
    }
}
