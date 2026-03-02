package ru.otus.command;

import ru.otus.IRotatable;
import ru.otus.IVelocityChangeable;
import ru.otus.utils.Vector;

//команда для модификации вектора мгновенной скорости при повороте для тех, кто и вращается и двигается
public class ChangeVelocityCommand implements ICommand {
    private final IVelocityChangeable velocityChangeable;
    private final IRotatable rotatable;

    public ChangeVelocityCommand(IVelocityChangeable velocityChangeable, IRotatable rotatable) {
        this.velocityChangeable = velocityChangeable;
        this.rotatable = rotatable;
    }

    public void execute() {
        Vector v = velocityChangeable.getVelocity();

        double angleDeg = rotatable.getAngleVelocity();
        double angleRad = Math.toRadians(angleDeg);

        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);

        double newX = v.getX() * cos - v.getY() * sin;
        double newY = v.getX() * sin + v.getY() * cos;

        velocityChangeable.setVelocity(new Vector(newX, newY));
    }
}
