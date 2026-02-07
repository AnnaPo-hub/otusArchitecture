package ru.otus;

public class RetryTwiceCommand implements ICommand {
    private final ICommand originalCommand;

    public RetryTwiceCommand(ICommand iCommand) {
        this.originalCommand = iCommand;
    }

    @Override
    public void execute() throws Exception {
        originalCommand.execute();
    }
}
