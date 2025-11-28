/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;


import tp1.exceptions.GameLoadException;

import tp1.exceptions.GameModelException;


public interface GameModel {

	public void update();
	public void addAction(Action act);
	public boolean addGameObject(String[] objectDescription, String Mode)  throws GameModelException;
	public void exit();
	public void reset(int nLevel, boolean noArguments);

    public void save(String fileName) throws GameModelException;
	public void load(String fileName) throws GameLoadException;

	
}
