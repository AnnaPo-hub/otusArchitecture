package ru.otus;

import ru.otus.utils.Vector;

public class Spaceship  {
    private Vector location;
    private Vector velocity;
    private double fuel;
    private final double fuelBurnRate;

    public Spaceship(Vector location, Vector velocity, double fuel, double fuelBurnRate) {
        this.location = location;
        this.velocity = velocity;
        this.fuel = fuel;
        this.fuelBurnRate = fuelBurnRate;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public Vector getLocation() {
        return location;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getFuel() {
        return fuel;
    }

    public double getFuelBurnRate() {
        return fuelBurnRate;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }
}
