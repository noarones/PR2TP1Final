/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class GameModelException extends Exception {

    private static final long serialVersionUID = -4729459550871557074L;

    public GameModelException() {
        super();
    }

    public GameModelException(String message) {
        super(message);
    }

    public GameModelException(Throwable cause) {
        super(cause);
    }

    public GameModelException(String message, Throwable cause) {
        super(message, cause);
    }
}

