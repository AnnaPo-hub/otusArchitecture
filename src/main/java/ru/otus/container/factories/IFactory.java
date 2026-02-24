package ru.otus.container.factories;

@FunctionalInterface
public interface IFactory {
    Object create(Object... args);
}

