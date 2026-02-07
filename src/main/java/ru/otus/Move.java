package ru.otus;

//move знает смысл движения
//не знает, что такое SpaceShip
//не знает координаты
//не знает про поворот
public class Move {
    private final IMovable movable;

    public Move(IMovable obj) {
        movable = obj;
    }

    public void execute() {
        movable.setLocation(movable.getLocation().add(movable.getVelocity()));
    }
}
