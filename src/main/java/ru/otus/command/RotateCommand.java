package ru.otus.command;

import ru.otus.IRotatable;

//для всех rotatable
public class RotateCommand implements ICommand {
    private final IRotatable rotatable;

    public RotateCommand(IRotatable obj) {
        rotatable = obj;
    }

    public void execute() {
        rotatable.setAngle(rotatable.getAngle() + rotatable.getAngleVelocity());
    }
}
