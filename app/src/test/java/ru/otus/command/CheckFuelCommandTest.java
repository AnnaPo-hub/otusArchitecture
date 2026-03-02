package ru.otus.command;

import org.junit.jupiter.api.Test;
import ru.otus.IFuelable;
import ru.otus.exception.CommandException;

import static org.junit.jupiter.api.Assertions.*;

class CheckFuelCommandTest {

    @Test
    void shouldThrowWhenFuelIsNotEnough() {
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(new TestFuelable(0, 1));
        CommandException ex =
                assertThrows(CommandException.class, checkFuelCommand::execute);
        assertEquals("Movement is impossible: not enough fuel", ex.getMessage());

    }
  ///И ещё: если burnRate = 0, то топлива “всегда хватает”  - отдельный крайний кейс (можно потом тестом).

    @Test
    void shouldNotThrowWhenFuelIsEnough() {
        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(new TestFuelable(10, 1));
        assertDoesNotThrow( checkFuelCommand::execute);
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
