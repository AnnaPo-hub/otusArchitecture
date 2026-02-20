package ru.otus.command;

import ru.otus.container.Scope;
import ru.otus.exception.CommandException;

import java.util.HashMap;
import java.util.Map;

public class RegisterCommand implements ICommand {
    private final String scopeId;
    private final Map<String, Scope> catalogue;
    private final Scope parent;


    public RegisterCommand(String scopeId, Scope parent, Map<String, Scope> catalogue ) {
        this.scopeId = scopeId;
        this.parent = parent;
        this.catalogue = catalogue;
    }

    @Override
    public void execute() throws CommandException {
        Scope scope = new Scope(new HashMap<>(), parent);
        catalogue.put(scopeId, scope);
    }
}
