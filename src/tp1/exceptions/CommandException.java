package tp1.exceptions;

public class CommandException extends Exception {

	private static final long serialVersionUID = -3960399942983039533L;

	public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
