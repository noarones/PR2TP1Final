/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class PositionParseException extends GameParseException {

    private static final long serialVersionUID = 7389886342668897738L;

    public PositionParseException() {
        super();
    }

    public PositionParseException(String message) {
        super(message);
    }

    public PositionParseException(Throwable cause) {
        super(cause);
    }

    public PositionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

