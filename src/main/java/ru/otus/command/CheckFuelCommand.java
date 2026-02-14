package ru.otus.command;

import ru.otus.Spaceship;
import ru.otus.exception.CommandException;

public class CheckFuelCommand implements ICommand {
    private final Spaceship spaceship;

    public CheckFuelCommand(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public void execute() throws CommandException {
        double fuel = spaceship.getFuel();
        if (fuel < spaceship.getFuelBurnRate()) {
            throw new CommandException("Movement is impossible: not enough fuel");
        }
    }
}
