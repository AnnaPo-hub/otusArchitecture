package ru.otus;


import ru.otus.processor.GenerateAdapter;
import ru.otus.utils.Vector;


@GenerateAdapter
public interface IMovable {
    Vector getLocation();

    void setLocation(Vector newValue);

    Vector getVelocity();

}
