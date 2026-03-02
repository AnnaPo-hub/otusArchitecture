package ru.otus.command;

import ru.otus.container.Scope;
import ru.otus.container.factories.IFactory;
import ru.otus.exception.CommandException;

import java.lang.reflect.Constructor;

public class AdapterCommand implements ICommand {
    private final String key;
    private final Class<?> adapterClass;
    private final Scope scope;

    public AdapterCommand(String key, Class<?> adapterClass, Scope scope) {
        this.key = key;
        this.adapterClass = adapterClass;
        this.scope = scope;
    }

    @Override
    public void execute() throws CommandException {
        IFactory adapterFactory = args -> {
            if (args.length != 1) {
                throw new RuntimeException("Adapter factory expects 1 argument: target object");
            }
            return createAdapter(args[0]);
        };
        scope.register(key, adapterFactory);
    }

    private Object createAdapter(Object target) {
        try {
            Constructor<?> ctor = adapterClass.getConstructor(Object.class);
            return ctor.newInstance(target);
        } catch (NoSuchMethodException ignored) {
            for (Constructor<?> ctor : adapterClass.getConstructors()) {
                Class<?>[] params = ctor.getParameterTypes();
                if (params.length == 1 && params[0].isAssignableFrom(target.getClass())) {
                    try {
                        return ctor.newInstance(target);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot create adapter " + adapterClass.getName(), e);
                    }
                }
            }
            throw new RuntimeException("No suitable constructor for adapter " + adapterClass.getName());
        } catch (Exception e) {
            throw new RuntimeException("Cannot create adapter " + adapterClass.getName(), e);
        }
    }
}
