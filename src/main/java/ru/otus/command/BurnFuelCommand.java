package ru.otus.command;

import ru.otus.Spaceship;

public class BurnFuelCommand implements ICommand {

    private final Spaceship spaceship;

    public BurnFuelCommand(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public void execute() throws Exception {
        spaceship.setFuel(spaceship.getFuel() - spaceship.getFuelBurnRate());
    }
}
