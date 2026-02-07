package ru.otus;

public class RetryOnceCommand implements ICommand {
    private final ICommand originalCommand;

    public RetryOnceCommand(ICommand originalCommand) {
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
