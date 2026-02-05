package ru.otus;

//rotate знает смысл движения
//не знает, что такое SpaceShip
//не знает что есть прямолинейноее движение
public class Rotate {
    private final IRotatable rotatable;

    public Rotate(IRotatable obj) {
        rotatable = obj;
    }

    public void execute() {
        rotatable.setAngle(rotatable.getAngle() + rotatable.getAngleVelocity());
    }
}
