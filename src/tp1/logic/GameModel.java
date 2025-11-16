/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;

public interface GameModel {

	public void update();
	public void addAction(Action act);
	public boolean addGameObject(String[] objectDescription, String Mode);
	public void exit();
	
	public void reset(int nLevel);
	
	
}
