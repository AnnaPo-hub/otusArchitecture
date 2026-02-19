package ru.otus.container;

@FunctionalInterface
public interface IFactory {
    Object create(Object... args);
}

