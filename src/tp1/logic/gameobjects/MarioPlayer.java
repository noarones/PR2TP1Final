package tp1.logic.gameobjects;

import tp1.logic.Action;

public interface MarioPlayer {
	//Unico movimiento permitido para Game
	public void addAction(Action dir);
	public GameObject clone();
}
