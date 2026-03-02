package ru.otus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.command.RotateCommand;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotateTest {

    @Mock
    IRotatable rotatable;

    @Test
    void shouldRotateObject() {
        when(rotatable.getAngle()).thenReturn(90.0);
        when(rotatable.getAngleVelocity()).thenReturn(30.0);

        RotateCommand rotate = new RotateCommand(rotatable);
        rotate.execute();

        verify(rotatable).setAngle(120.0);
    }

    @Test
    void shouldThrowIfAngleCannotBeSet() {
        when(rotatable.getAngle()).thenReturn(90.0);
        when(rotatable.getAngleVelocity()).thenReturn(30.0);

//when(...).thenThrow(...) - для методов с return
//doThrow(...).when(mock).voidMethod(...)-  для void
        doThrow(new RuntimeException())
                .when(rotatable)
                .setAngle(anyDouble());

        RotateCommand rotate = new RotateCommand(rotatable);
        assertThrows(RuntimeException.class, rotate::execute);
    }


    @Test
    void shouldThrowIfAngleVelocityCannotBeRead() {
        when(rotatable.getAngle()).thenReturn(90.0);

        when(rotatable.getAngleVelocity()).thenThrow(new RuntimeException());

        RotateCommand rotate = new RotateCommand(rotatable);
        assertThrows(RuntimeException.class, rotate::execute);
    }

    @Test
    void shouldThrowIfAngleCannotBeRead() {

        when(rotatable.getAngle()).thenThrow(new RuntimeException());

        RotateCommand rotate = new RotateCommand(rotatable);
        assertThrows(RuntimeException.class, rotate::execute);
    }
}



