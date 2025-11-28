package tp1.logic;

import tp1.logic.gameobjects.Mario;

public interface GameConfiguration {
   public InitialValues getInitialValues();
   // game objects
   public GameObjectContainer getGameObjects();

}
