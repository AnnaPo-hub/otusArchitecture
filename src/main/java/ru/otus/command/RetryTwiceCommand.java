package ru.otus.command;

public class RetryTwiceCommand implements ICommand {
    private final ICommand originalCommand;

    public RetryTwiceCommand(ICommand originalCommand) {
        this.originalCommand = originalCommand;
    }

    @Override
    public void execute() throws Exception {
        originalCommand.execute();
    }

    public ICommand getOriginalCommand() {
        return originalCommand;
    }
}
