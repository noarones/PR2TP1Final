/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class ActionParseException extends GameParseException {

    private static final long serialVersionUID = 9123113903640416197L;


    public ActionParseException() {
        super();
    }


    public ActionParseException(String message) {
        super(message);
    }


    public ActionParseException(Throwable cause) {
        super(cause);
    }


    public ActionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

