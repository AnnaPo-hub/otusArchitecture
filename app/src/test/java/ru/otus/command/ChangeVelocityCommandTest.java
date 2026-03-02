package ru.otus.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.IMovable;
import ru.otus.utils.Vector;

class ChangeVelocityCommandTest {

    @Test
    void shouldCheck() {
        IMovable movable = new TestMovable(new Vector(12, 5), new Vector(-7, 3));
        MoveCommand moveCommand = new MoveCommand(movable);
        moveCommand.execute();
        Assertions.assertEquals(new Vector(5, 8), movable.getLocation());
    }

    static class TestMovable implements IMovable {
        private Vector location;
        private final Vector velocity;

        TestMovable(Vector location, Vector velocity) {
            this.location = location;
            this.velocity = velocity;
        }

        @Override
        public Vector getLocation() {
            return location;
        }

        @Override
        public void setLocation(Vector newValue) {
            this.location = newValue;
        }

        @Override
        public Vector getVelocity() {
            return velocity;
        }
    }
}
