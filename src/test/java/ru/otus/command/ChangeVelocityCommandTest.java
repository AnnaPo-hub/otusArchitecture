package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.IMovable;
import ru.otus.dto.Spaceship;
import ru.otus.adapter.MovableAdapter;
import ru.otus.utils.Vector;

class ChangeVelocityCommandTest {

    Spaceship spaceship;
    IMovable movable;
    MoveCommand moveCommand;
    RotateWithVelocityChangeMacroCommand changeVelocityCommand;
    double fuel = 10;
    double fuelBurnRate = 1;


    @Test
    void shouldCheck() {

        spaceship = new Spaceship(new Vector(12, 5), new Vector(-7, 3), fuel, fuelBurnRate);

        movable = new MovableAdapter(spaceship);


        moveCommand = new MoveCommand(movable);
      //  changeVelocityCommand = new ChangeVelocityOnRotateCommand(movable);

        moveCommand.execute();

        Assertions.assertEquals(new Vector(5, 8), spaceship.getLocation());
    }
}