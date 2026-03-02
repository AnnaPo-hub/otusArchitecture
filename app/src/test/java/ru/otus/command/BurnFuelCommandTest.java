package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.IFuelable;

class BurnFuelCommandTest {

    @Test
    void shouldCheckFuelDecreased() throws Exception {
        TestFuelable fuelable = new TestFuelable(10, 1);
        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(fuelable);
        burnFuelCommand.execute();
        Assertions.assertEquals(9.0, fuelable.getFuel(), 1e-9);
    }

    static class TestFuelable implements IFuelable {
        private double fuel;
        private final double burnRate;

        TestFuelable(double fuel, double burnRate) {
            this.fuel = fuel;
            this.burnRate = burnRate;
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
