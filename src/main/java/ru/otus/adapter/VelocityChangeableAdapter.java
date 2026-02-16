package ru.otus.adapter;

import ru.otus.IVelocityChangeable;
import ru.otus.dto.Spaceship;
import ru.otus.utils.Vector;

public class VelocityChangeableAdapter implements IVelocityChangeable {

    private final Spaceship spaceship;

    public VelocityChangeableAdapter(Spaceship spaceship) {
        this.spaceship = spaceship;
    }


    @Override
    public Vector getVelocity() {
        return spaceship.getVelocity();
    }

    @Override
    public void setVelocity(Vector newVelocity) {
        spaceship.setVelocity(newVelocity);
    }
}
