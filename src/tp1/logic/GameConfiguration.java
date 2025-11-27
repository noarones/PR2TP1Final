package tp1.logic;

import tp1.logic.gameobjects.Mario;

public interface GameConfiguration {
    // game status
   public int getRemainingTime();
   public int getPoints();
   public int getNumLives();

   // game objects
   public GameObjectContainer getGameObjects();
   public Mario getMario();
}
