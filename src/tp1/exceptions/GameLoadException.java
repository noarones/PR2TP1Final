/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class GameLoadException extends CommandException {

    private static final long serialVersionUID = -8334214123004881517L;

	public GameLoadException(String message) {
        super(message);
    }

    public GameLoadException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
