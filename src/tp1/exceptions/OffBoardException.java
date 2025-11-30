/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.exceptions;

public class OffBoardException extends GameModelException {

    private static final long serialVersionUID = -6315992177867572676L;

	public OffBoardException(String message) {
        super(message);
    }

    public OffBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
