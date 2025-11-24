/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Land extends GameObject{

	public Land(GameWorld game, Position pos) {
		super(game, pos);
		
	}
    
	Land() {
		super(null,null);
	}
	
	@Override
	public boolean interactWith(GameItem item) {
		return false;
	}

	

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void update() {
		
	}

	@Override
	public String getIcon() {
		return Messages.LAND;
	}


	@Override public String toString() {
		return "Land";
	}

	@Override
	protected GameObject create(String[] words, GameWorld game, Position pos) {
		return new Land(game,pos);
	}



}
