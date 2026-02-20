package ru.otus.container;

import java.util.Map;
import java.util.Objects;

//версия "словаря"
public class Scope {

    private final
    Map<String, IFactory> map;
    private final Scope parent;

    public Scope(Map<String, IFactory> map, Scope parent) {
        this.map = Objects.requireNonNull(map, "Map must not be null");
        this.parent = parent;
    }



    //найти фабрику и вызвать ее
    public <T> T resolve(String key, Object... args) {
        Scope s = this;

        while (s != null) {
            IFactory factory = s.map.get(key);
            if (factory != null) {
                Object result = factory.create(args);
                return (T) result;
            }
            s = s.parent;
        }

        throw new RuntimeException("Dependency not found: " + key);
    }


    public void register(String key, IFactory factory) {
        map.put(key, factory);
    }
}

