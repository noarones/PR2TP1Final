package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;
import tp1.logic.GameWorld;

public class GameObjectFactory {
 
	
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(),
			new Mushroom(),
			new Box()
		);
	
	public static GameObject parse (String objWords[], GameWorld game) {
		
		for (GameObject g : availableObjects) {
			
			GameObject g_ = g.parse(objWords, game);
			
            if (g_ != null)
                return g_;
			
		}
		
		return null;
		
	}
	
}
