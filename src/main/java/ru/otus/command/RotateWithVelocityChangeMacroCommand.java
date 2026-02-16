package ru.otus.command;

import ru.otus.IRotatable;
import ru.otus.IVelocityChangeable;


//изменение  вектора скорости при повороте
//только для тех, кто имеет velocity
public class RotateWithVelocityChangeMacroCommand implements ICommand {

    private final ICommand[] commands;
    RotateCommand rotateCommand;
    ChangeVelocityCommand changeVelocityCommand;
    private final MacroCommand macroCommand;

    public RotateWithVelocityChangeMacroCommand(IVelocityChangeable velocityChangeable, IRotatable rotatable) {
        rotateCommand = new RotateCommand(rotatable);
        changeVelocityCommand = new ChangeVelocityCommand(velocityChangeable, rotatable);
        commands = new ICommand[]{rotateCommand, changeVelocityCommand};
        macroCommand = new MacroCommand(commands);
    }

    @Override
    public void execute() throws Exception {
        macroCommand.execute();
    }
}
