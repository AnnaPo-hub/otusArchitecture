package ru.otus.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.exception.CommandException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class MacroCommandTest {

    @Mock
    ICommand moveCommand;
    @Mock
    ICommand checkFuelCommand;
    @Mock
    ICommand burnFuelCommand;

    ICommand[] iCommands;

    MacroCommand macroCommand;

    @BeforeEach
    void setup() {

    }

    @Test
    void shouldExecuteAllCommandsInOrder() throws Exception {
        iCommands = new ICommand[]{checkFuelCommand, moveCommand, burnFuelCommand};
        macroCommand = new MacroCommand(iCommands);

        macroCommand.execute();
        InOrder inOrder = inOrder(checkFuelCommand, moveCommand, burnFuelCommand);
        inOrder.verify(checkFuelCommand).execute();
        inOrder.verify(moveCommand).execute();
        inOrder.verify(burnFuelCommand).execute();
    }

    @Test
    void shouldStopExecutionWhenCommandThrows() throws Exception {
        doThrow(new RuntimeException())
                .when(moveCommand)
                .execute();

        ICommand[] iCommands = new ICommand[]{checkFuelCommand, moveCommand, burnFuelCommand};
        MacroCommand macro = new MacroCommand(iCommands);

        assertThrows(CommandException.class, macro::execute);
    }
}