package ru.otus;

public class LogExceptionCommand implements ICommand {
    private final ICommand failedCommand;
    private final Exception exception;

    public LogExceptionCommand(ICommand failedCommand, Exception exception) {
        this.failedCommand = failedCommand;
        this.exception = exception;
    }

    @Override
    public void execute() {
        System.out.println("Ошибка при выполнении " + failedCommand.getClass().getSimpleName() + " : " + exception.getMessage());
    }
}
