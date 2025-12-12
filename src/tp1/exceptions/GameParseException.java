/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class GameParseException extends GameModelException {

    private static final long serialVersionUID = 3188703323840589716L;

    public GameParseException() {
        super();
    }

    public GameParseException(String message) {
        super(message);
    }

    public GameParseException(Throwable cause) {
        super(cause);
    }

    public GameParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
