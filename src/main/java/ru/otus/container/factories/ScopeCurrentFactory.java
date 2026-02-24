package ru.otus.container.factories;

import ru.otus.command.ScopeCurrentCommand;
import ru.otus.container.IoC;

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
