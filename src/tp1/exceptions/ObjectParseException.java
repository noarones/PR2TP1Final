/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class ObjectParseException extends GameParseException {

    private static final long serialVersionUID = 261840818349536677L;

    public ObjectParseException() {
        super();
    }

    public ObjectParseException(String message) {
        super(message);
    }

    public ObjectParseException(Throwable cause) {
        super(cause);
    }

    public ObjectParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

