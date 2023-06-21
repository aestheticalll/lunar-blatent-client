package tactical.client.feature.command.exception;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class CommandInvalidArgumentException extends Exception {
    private final String argumentName;
    private final String message;

    public CommandInvalidArgumentException(String argumentName, String message) {
        this.argumentName = argumentName;
        this.message = message;
    }

    public String argumentName() {
        return argumentName;
    }

    public String message() {
        return message;
    }
}
