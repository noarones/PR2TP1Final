package tp1.exceptions;

public class PositionParseException extends GameParseException {

    private static final long serialVersionUID = 7389886342668897738L;

	public PositionParseException(String message) {
        super(message);
    }

    public PositionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
