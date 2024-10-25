package ru.otus;

public class Spaceship {
    Vector location;
    Vector velocity;
    int fuel;
    int fuelVelocity;
    boolean isFuelOn;

    void move() {
        if (isFuelOn && fuel - fuelVelocity >= 0) {
            location = Vector.plus(location, velocity);
            System.out.println("Spaceship is moving");
            fuel -= fuelVelocity;
        }
    }

    public void rotate() {
        System.out.println("Spaceship is rotating");

    }
}
