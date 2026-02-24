package ru.otus;

import ru.otus.container.*;
import ru.otus.container.factories.RegisterFactory;
import ru.otus.container.factories.ScopeCurrentFactory;
import ru.otus.container.factories.ScopeNewFactory;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scope root = new Scope(new HashMap<>(), null);

        root.register("IoC.Register", new RegisterFactory());
        root.register("Scopes.New", new ScopeNewFactory());
        root.register("Scopes.Current", new ScopeCurrentFactory());

        IoC.init(root);
    }
}