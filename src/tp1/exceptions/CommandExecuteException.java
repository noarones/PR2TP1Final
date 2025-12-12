/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class CommandExecuteException extends CommandException {

    private static final long serialVersionUID = 5114978533241610720L;

    public CommandExecuteException() {
        super();
    }

    public CommandExecuteException(String message) {
        super(message);
    }

    public CommandExecuteException(Throwable cause) {
        super(cause);
    }

    public CommandExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}

