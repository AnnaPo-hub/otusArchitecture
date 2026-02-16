package ru.otus;


import ru.otus.utils.Vector;

public interface IMovable {
    Vector getLocation();

    void setLocation(Vector newValue);

    Vector getVelocity();

}
