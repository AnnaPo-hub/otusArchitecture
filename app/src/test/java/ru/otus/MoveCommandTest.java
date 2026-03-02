package ru.otus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.command.MoveCommand;
import ru.otus.utils.Vector;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoveCommandTest {

    @Mock
    IMovable movable;

    @Test
    void shouldMoveObject() {
        when(movable.getLocation()).thenReturn(new Vector(12, 5));
        when(movable.getVelocity()).thenReturn(new Vector(-7, 3));

        MoveCommand move = new MoveCommand(movable);
        move.execute();

        verify(movable).setLocation(new Vector(5, 8));
    }

    @Test
    void shouldThrowIfLocationCannotBeSet() {
        when(movable.getLocation()).thenReturn(new Vector(12, 5));
        when(movable.getVelocity()).thenReturn(new Vector(-7, 3));

//when(...).thenThrow(...) - для методов с return
//doThrow(...).when(mock).voidMethod(...)-  для void
        doThrow(new RuntimeException())
                .when(movable)
                .setLocation(any());

        MoveCommand move = new MoveCommand(movable);
        assertThrows(RuntimeException.class, move::execute);
    }


    @Test
    void shouldThrowIfVelocityCannotBeRead() {
        when(movable.getLocation()).thenReturn(new Vector(12, 5));

        when(movable.getVelocity()).thenThrow(new RuntimeException());

        MoveCommand move = new MoveCommand(movable);
        assertThrows(RuntimeException.class, move::execute);
    }

    @Test
    void shouldThrowIfLocationCannotBeRead() {

        when(movable.getLocation()).thenThrow(new RuntimeException());

        MoveCommand move = new MoveCommand(movable);
        assertThrows(RuntimeException.class, move::execute);
    }
}



