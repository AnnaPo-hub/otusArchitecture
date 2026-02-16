package ru.otus;

import ru.otus.command.ICommand;
import ru.otus.command.RotateCommand;
import ru.otus.command.RotateWithVelocityChangeMacroCommand;

public class RotateCommandFactory {
    public static ICommand create (IRotatable rotatable){
        if (rotatable instanceof IVelocityChangeable velocityChangeable) {

            return new RotateWithVelocityChangeMacroCommand(
                    velocityChangeable, rotatable

            );
        }
        return new RotateCommand(rotatable);
    }
}
