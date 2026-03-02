package ru.otus.command;

import ru.otus.IMovable;

public class MoveCommand implements ICommand {
    private final IMovable movable;

    public MoveCommand(IMovable obj) {
        movable = obj;
    }

    public void execute() {
        movable.setLocation(movable.getLocation().add(movable.getVelocity()));
    }
}
