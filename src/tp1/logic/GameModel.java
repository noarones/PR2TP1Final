/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;

public interface GameModel {

	public void update();
	public void addAction(Action act);
	public boolean addGameObject(String[] objectDescription, String Mode)  throws OffBoardException, ObjectParseException, PositionParseException;
	public void exit();
	public void reset(int nLevel, boolean noArguments);
	
	
}
