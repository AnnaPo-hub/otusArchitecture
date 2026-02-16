package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.IFuelable;
import ru.otus.adapter.FuelableAdapter;
import ru.otus.dto.Spaceship;
import ru.otus.utils.Vector;

class BurnFuelCommandTest {

    Spaceship spaceship;
    IFuelable fuelable;
    BurnFuelCommand burnFuelCommand;

    double fuel = 10;
    double fuelBurnRate = 1;

    @Test
    void shouldCheckFuelDecreased() throws Exception {

        spaceship = new Spaceship(new Vector(12, 5), new Vector(-7, 3), fuel, fuelBurnRate);

        fuelable = new FuelableAdapter(spaceship);

        burnFuelCommand = new BurnFuelCommand(fuelable);
        burnFuelCommand.execute();
        Assertions.assertEquals((fuel - fuelBurnRate), spaceship.getFuel(), 1e-9);
    }
}