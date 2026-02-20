package ru.otus;

import ru.otus.container.IoC;
import ru.otus.container.Scope;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        Scope root = new Scope(new HashMap<>(), null);
        IoC.init(root);

//        root.register("IoC.Register", );
//        root.register("Scopes.New", );
//        root.register("Scopes.Current", );


    }
}