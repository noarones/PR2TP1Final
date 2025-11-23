/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameWorld{
	
	public boolean solidUnder(Position pos);
	public boolean solidRight(Position pos);
	public boolean solidLeft(Position pos);
	public boolean solidUp(Position pos);
	public boolean nextToRightLimit(Position pos);
	public boolean nextToLeftLimit(Position pos);
	public boolean fallenOut(Position pos);
	public void addPoints(int p);
	public void marioExited();
	public void removeLife();
	public void setAsMainCharacter(Mario mario);
	public boolean isInBoard(Position pos); 
	// Se utilizan en Mario
	public void reset(int nLevel);
	public int numLives();
	public boolean addGameObject(String[] objectDescription, String Mode) throws OffBoardException, ObjectParseException, PositionParseException ;
    public void checkInteractions(GameObject obj);
}
