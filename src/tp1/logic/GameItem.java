/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;


import tp1.logic.gameobjects.*;

public interface GameItem {

	public  boolean isSolid();
	public  boolean isAlive();
	public  boolean isInPosition(Position pos);
 
	
	public  boolean interactWith(GameItem item);

	public  boolean receiveInteraction(Land obj);
	public  boolean receiveInteraction(ExitDoor obj);
	public  boolean receiveInteraction(Mario obj);
	public  boolean receiveInteraction(Goomba obj);
	public  boolean receiveInteraction(Mushroom obj);
	public  boolean receiveInteraction(Box obj);
}
