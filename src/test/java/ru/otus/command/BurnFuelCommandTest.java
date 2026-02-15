package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.Spaceship;
import ru.otus.Vector;

class BurnFuelCommandTest {

    Spaceship spaceship;
    BurnFuelCommand burnFuelCommand;

    double fuel = 10;
    double fuelBurnRate = 1;

    @Test
    void shouldCheckFuelDecreased() throws Exception {
        spaceship = new Spaceship(new Vector(12, 5), new Vector(-7, 3), fuel, fuelBurnRate);
        burnFuelCommand = new BurnFuelCommand(spaceship);
        burnFuelCommand.execute();
        Assertions.assertEquals((fuel - fuelBurnRate), spaceship.getFuel(), 1e-9);
    }
}