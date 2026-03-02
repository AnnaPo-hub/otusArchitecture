package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.IFuelable;
import ru.otus.IMovable;
import ru.otus.utils.Vector;

class MoveWithFuelCommandTest {

    @Test
    void shouldCheck() throws Exception {
        TestShip ship = new TestShip(new Vector(12, 5), new Vector(-7, 3), 10, 1);
        IMovable movable = ship;
        IFuelable fuelable = ship;
        MoveCommand moveCommand = new MoveCommand(movable);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelable);
        moveCommand.execute();
        burnFuelCommand.execute();

        Assertions.assertEquals(new Vector(5, 8), ship.getLocation());
        Assertions.assertEquals(9.0, ship.getFuel(), 1e-9);
    }

    static class TestShip implements IMovable, IFuelable {
        private Vector location;
        private final Vector velocity;
        private double fuel;
        private final double burnRate;

        TestShip(Vector location, Vector velocity, double fuel, double burnRate) {
            this.location = location;
            this.velocity = velocity;
            this.fuel = fuel;
            this.burnRate = burnRate;
        }

        @Override
        public Vector getLocation() {
            return location;
        }

        @Override
        public void setLocation(Vector newValue) {
            this.location = newValue;
        }

        @Override
        public Vector getVelocity() {
            return velocity;
        }

        @Override
        public double getFuel() {
            return fuel;
        }

        @Override
        public double getFuelBurnRate() {
            return burnRate;
        }

        @Override
        public void setFuel(double newFuelQuantity) {
            this.fuel = newFuelQuantity;
        }
    }
}
