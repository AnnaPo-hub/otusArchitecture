package ru.otus.adapter;

import ru.otus.IMovable;
import ru.otus.Spaceship;
import ru.otus.utils.Vector;

public class MovableAdapter implements IMovable {

    Spaceship spaceship;

    public MovableAdapter(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public Vector getLocation() {
        return spaceship.getLocation();
    }

    @Override
    public void setLocation(Vector newValue) {
        spaceship.setLocation(newValue);
    }

    @Override
    public Vector getVelocity() {
        return spaceship.getVelocity();
    }
}
