package ru.otus.command;

import ru.otus.container.Scope;
import ru.otus.exception.CommandException;

import java.util.Map;

public class ScopeCurrentCommand implements ICommand {
    private final String scopeId;
    private final ThreadLocal<Scope> currentScopeHolder;
    private final Map<String, Scope> catalogue;

    public ScopeCurrentCommand(String scopeId, ThreadLocal<Scope> currentScope, Map<String, Scope> catalogue) {
        this.scopeId = scopeId;
        this.currentScopeHolder = currentScope;
        this.catalogue = catalogue;
    }

    @Override
    public void execute() throws CommandException {
        Scope scope = catalogue.get(scopeId);
        if (scope == null) {
            throw new CommandException("Scope by id " + scopeId + " is not found");
        }
        currentScopeHolder.set(scope);
    }
}
