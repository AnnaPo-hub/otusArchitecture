package ru.otus.adapter;

import ru.otus.IFuelable;
import ru.otus.Spaceship;

public class FuelableAdapter implements IFuelable {
    Spaceship spaceship;

    public FuelableAdapter(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public double getFuel() {
        return spaceship.getFuel();
    }

    @Override
    public double getFuelBurnRate() {
        return spaceship.getFuelBurnRate();
    }

    @Override
    public void setFuel(double newFuelQuantity) {
        spaceship.setFuel(newFuelQuantity);
    }
}
