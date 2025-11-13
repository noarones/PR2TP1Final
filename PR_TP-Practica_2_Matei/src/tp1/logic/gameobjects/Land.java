package tp1.logic.gameobjects;

import tp1.logic.Game;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(Land obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIcon() {
		// TODO Auto-generated method stub
		return Messages.LAND;
	}


	@Override
	public boolean wasInPosition(Position pos) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override public String toString() {
		return "Land";
	}

	@Override
	protected GameObject create(String[] words, GameWorld game, Position pos) {
		return new Land(game,pos);
	}

	@Override
	public boolean receiveInteraction(Mushroom obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
