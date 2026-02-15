package ru.otus.command;

import ru.otus.IFuelable;

public class BurnFuelCommand implements ICommand {

    private final IFuelable fuelable;

    public BurnFuelCommand(IFuelable fuelable ) {
        this.fuelable = fuelable;
    }

    @Override
    public void execute() throws Exception {
        fuelable.setFuel(fuelable.getFuel() - fuelable.getFuelBurnRate());
    }
}
