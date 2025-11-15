package tp1.logic.gameobjects;

import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject{

	
	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos);
		
	}
	
	ExitDoor() {
		super(null,null);
	}

	@Override
	public boolean interactWith(GameItem item) {
		
		return false;
	}

	@Override
	public boolean receiveInteraction(Land obj) {
		
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		
		return false;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		return obj.isInPosition(this.pos) ? obj.marioExited() : false;
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		
		return false;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void update() {
		
		
	}

	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR;
	}


	@Override 
    public String toString() {
        return "ExitDoor";
    }

	@Override
	protected GameObject create(String[] words, GameWorld game, Position pos) {
		return new ExitDoor(game , pos);
	}

	@Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}
	
}
