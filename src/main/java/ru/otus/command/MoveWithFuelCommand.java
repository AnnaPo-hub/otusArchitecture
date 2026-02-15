package ru.otus.command;

import ru.otus.IFuelable;
import ru.otus.IMovable;

public class MoveWithFuelCommand implements ICommand {
    IMovable movable;
    IFuelable fuelable;
    final MoveCommand moveCommand;
    final CheckFuelCommand checkFuelCommand;
    final BurnFuelCommand burnFuelCommand;
    final ICommand[] commands;
    final MacroCommand macroCommand;

    public MoveWithFuelCommand(IMovable movable, IFuelable fuelable) {
        this.movable = movable;
        this.fuelable = fuelable;
        this.moveCommand = new MoveCommand(movable);
        this.checkFuelCommand = new CheckFuelCommand(fuelable);
        this.burnFuelCommand = new BurnFuelCommand(fuelable);
        commands = new ICommand[]{checkFuelCommand, moveCommand, burnFuelCommand};
        macroCommand = new MacroCommand(commands);
    }

    public void execute() throws Exception {
        macroCommand.execute();
    }
}
