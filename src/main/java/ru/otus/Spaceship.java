package ru.otus;

public class Spaceship {
    Vector location;
    Vector velocity;
    double fuel;
    double fuelBurnRate;

    public Spaceship(Vector location, Vector velocity, double fuel, double fuelBurnRate) {
        this.location = location;
        this.velocity = velocity;
        this.fuel = fuel;
        this.fuelBurnRate = fuelBurnRate;
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
}
