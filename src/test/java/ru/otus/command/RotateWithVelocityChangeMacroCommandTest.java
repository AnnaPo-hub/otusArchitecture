package ru.otus.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.IRotatable;
import ru.otus.IVelocityChangeable;
import ru.otus.utils.Vector;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotateWithVelocityChangeMacroCommandTest {

    @Mock
    IRotatable rotatable;

    @Mock
    IVelocityChangeable velocityChangeable;

    @Test
    void shouldRotateAndRotateVelocityVector() throws Exception {
        // angle: 0 + 90
        when(rotatable.getAngle()).thenReturn(0.0);
        when(rotatable.getAngleVelocity()).thenReturn(90.0);

        // velocity: (1,0) -> after 90deg -> (0,1)
        when(velocityChangeable.getVelocity()).thenReturn(new Vector(1.0, 0.0));

        ICommand cmd = new RotateWithVelocityChangeMacroCommand(velocityChangeable, rotatable);
        cmd.execute();

        verify(rotatable).setAngle(90.0);

        verify(velocityChangeable).setVelocity(argThat(v ->
                Math.abs(v.getX() - 0.0) < 1e-9 &&
                        Math.abs(v.getY() - 1.0) < 1e-9
        ));
    }

    @Test
    void shouldWrapExceptionIntoCommandExceptionIfRotateFails() throws Exception {
        when(rotatable.getAngle()).thenReturn(0.0);
        when(rotatable.getAngleVelocity()).thenReturn(90.0);

        doThrow(new RuntimeException("boom"))
                .when(rotatable)
                .setAngle(anyDouble());

        ICommand cmd = new RotateWithVelocityChangeMacroCommand(velocityChangeable, rotatable);

        assertThrows(ru.otus.exception.CommandException.class, cmd::execute);

        verify(velocityChangeable, never()).setVelocity(any());
    }
}