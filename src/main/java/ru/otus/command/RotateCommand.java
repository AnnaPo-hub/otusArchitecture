package ru.otus.command;

import ru.otus.IRotatable;

public class RotateCommand {
    private final IRotatable rotatable;

    public RotateCommand(IRotatable obj) {
        rotatable = obj;
    }

    public void execute() {
        rotatable.setAngle(rotatable.getAngle() + rotatable.getAngleVelocity());
    }
}
