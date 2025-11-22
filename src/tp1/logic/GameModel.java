/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.ObjectParseException;

public interface GameModel {

	public void update();
	public void addAction(Action act);
	public boolean addGameObject(String[] objectDescription, String Mode)  throws OffBoardException, ObjectParseException;
	public void exit();
	public void reset(int nLevel);
	
	
}
