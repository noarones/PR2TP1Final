package tp1.exceptions;

public class ActionParseException extends GameParseException {

    private static final long serialVersionUID = 9123113903640416197L;

	public ActionParseException(String message) {
        super(message);
    }

    public ActionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
