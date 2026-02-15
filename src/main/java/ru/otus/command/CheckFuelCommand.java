package ru.otus.command;

import ru.otus.IFuelable;
import ru.otus.exception.CommandException;

public class CheckFuelCommand implements ICommand {
    private final IFuelable fuelable;

    public CheckFuelCommand(IFuelable fuelable) {
        this.fuelable = fuelable;
    }

    @Override
    public void execute() throws CommandException {
        double fuel = fuelable.getFuel();
        if (fuel < fuelable.getFuelBurnRate()) {
            throw new CommandException("Movement is impossible: not enough fuel");
        }
    }
}
