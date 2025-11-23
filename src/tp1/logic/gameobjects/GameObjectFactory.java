/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;
import tp1.logic.GameWorld;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.view.Messages;
public class GameObjectFactory {
 
	
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Land(),
			new ExitDoor(),
			new Goomba(),
			new Mario(),
			new Mushroom(),
			new Box()
		);
	
	public static GameObject parse(String[] objWords, GameWorld game)
	        throws OffBoardException, ObjectParseException, PositionParseException{

	    for (GameObject g : availableObjects) {
	        GameObject obj = g.parse(objWords, game);

	        if (obj != null) {
	            return obj;             // Objeto válido
	        }
	        // obj == null → tipo no coincide → seguir buscando
	    }

	    // Ningún objeto ha hecho match de tipo
	    throw new ObjectParseException(
	            Messages.UNKNOWN_GAME_OBJECT.formatted(String.join(" ", objWords))
	    );
	}

	}
	

