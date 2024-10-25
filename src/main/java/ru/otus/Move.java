package ru.otus;

public class Move {
    Movable _movable;

    public Move(Movable movable) {
        _movable = movable;
    }

  public  void execute() {
_movable.setLocation(
        Vector.plus(
                _movable.getLocation(),
                _movable.getVelocity()));

    }
}
