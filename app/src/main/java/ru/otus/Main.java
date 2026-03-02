package ru.otus;

import ru.otus.command.ICommand;
import ru.otus.container.IoC;
import ru.otus.container.Scope;
import ru.otus.container.factories.AdapterFactory;
import ru.otus.container.factories.RegisterFactory;
import ru.otus.container.factories.ScopeCurrentFactory;
import ru.otus.container.factories.ScopeNewFactory;
import ru.otus.dto.Spaceship;
import ru.otus.utils.Vector;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scope root = new Scope(new HashMap<>(), null);

        root.register("IoC.Register", new RegisterFactory());
        root.register("IoC.Adapter", new AdapterFactory());
        root.register("Scopes.New", new ScopeNewFactory());
        root.register("Scopes.Current", new ScopeCurrentFactory());

//регистрируем стратегии
        root.register("ru.otus.IMovable:location.get", (arg) -> ((Spaceship) arg[0]).getLocation());
        root.register("ru.otus.IMovable:velocity.get", (arg) -> ((Spaceship) arg[0]).getVelocity());
        root.register(
                "ru.otus.IMovable:location.set",
                (arg) -> (ICommand) () -> {
                    Spaceship s = (Spaceship) arg[0];
                    Vector v = (Vector) arg[1];
                    s.setLocation(v);
                }
        );

        IoC.init(root);
    }
}
