package ru.otus.command;

import ru.otus.exception.CommandException;

public class MacroCommand implements ICommand {
    private final ICommand[] commands;

    public MacroCommand(ICommand[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() throws Exception {
        try {
            for (ICommand command : commands) {
                command.execute();
            }
        } catch (Exception e) {
            throw new CommandException("MacroCommand execution failed", e);
        }
    }
}
