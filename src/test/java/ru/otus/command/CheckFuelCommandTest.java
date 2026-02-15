package ru.otus.command;

import org.junit.jupiter.api.Test;
import ru.otus.Spaceship;
import ru.otus.adapter.FuelableAdapter;
import ru.otus.utils.Vector;
import ru.otus.exception.CommandException;

import static org.junit.jupiter.api.Assertions.*;

class CheckFuelCommandTest {

    Spaceship spaceship;
    CheckFuelCommand checkFuelCommand;

    @Test
    void shouldThrowWhenFuelIsNotEnough() {
        spaceship = new Spaceship(new Vector(12, 5), new Vector(-7, 3), 0, 1);
        checkFuelCommand = new CheckFuelCommand(new FuelableAdapter(spaceship));
        CommandException ex =
                assertThrows(CommandException.class, checkFuelCommand::execute);
        assertEquals("Movement is impossible: not enough fuel", ex.getMessage());

    }
  ///И ещё: если burnRate = 0, то топлива “всегда хватает”  - отдельный крайний кейс (можно потом тестом).

    @Test
    void shouldNotThrowWhenFuelIsEnough() {
        spaceship = new Spaceship(new Vector(12, 5), new Vector(-7, 3), 10, 1);
        checkFuelCommand = new CheckFuelCommand(new FuelableAdapter(spaceship));
        assertDoesNotThrow( checkFuelCommand::execute);
    }
}
