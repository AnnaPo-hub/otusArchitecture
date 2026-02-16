package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.IFuelable;
import ru.otus.IMovable;
import ru.otus.dto.Spaceship;
import ru.otus.adapter.FuelableAdapter;
import ru.otus.adapter.MovableAdapter;
import ru.otus.utils.Vector;

class MoveWithFuelCommandTest {
    Spaceship spaceship;
    IFuelable fuelable;
    IMovable movable;
    BurnFuelCommand burnFuelCommand;
    MoveCommand moveCommand;

    double fuel = 10;
    double fuelBurnRate = 1;

    @Test
    void shouldCheck() throws Exception {

        spaceship = new Spaceship(new Vector(12, 5), new Vector(-7, 3), fuel, fuelBurnRate);

        fuelable = new FuelableAdapter(spaceship);
        movable = new MovableAdapter(spaceship);

        moveCommand = new MoveCommand(movable);
        burnFuelCommand = new BurnFuelCommand(fuelable);

        moveCommand.execute();
        burnFuelCommand.execute();

        Assertions.assertEquals( new Vector(5, 8), spaceship.getLocation());
        Assertions.assertEquals((fuel - fuelBurnRate), spaceship.getFuel(), 1e-9);
    }
}