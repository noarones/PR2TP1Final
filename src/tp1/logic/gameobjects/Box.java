package tp1.logic.gameobjects;

import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Box extends GameObject{

	private boolean abierto = false;
	
	public Box(GameWorld game, Position pos) {
		super(game, pos);
		setInitial(abierto);
	}

	Box() {
		super(null,null);
	}
	
	private void setInitial(boolean abierto)
	{
		this.abierto = abierto;
		this.points = 50;
	}	
	@Override
	public String toString() {
		return "Box";
	}



	 @Override
	    public boolean interactWith(GameItem item) {
	        boolean canInteract = item.isInPosition(this.pos.under());
	        if (canInteract) {
	            return item.receiveInteraction(this);
	        }
	        return canInteract;
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

	private String[] buildString(String type, Position pos) {
	    return new String[] {
	        pos.toString(), 
	        type
	    };
	}
	
	@Override
	public boolean receiveInteraction(Mario obj) {
		 abierto = true;
		 game.addPoints(points);
	     game.addGameObject(buildString("Mushroom", this.pos.up()), "spawn");
		 return isAlive();
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
	public String getIcon() {
		return abierto ? Messages.EMPTY_BOX : Messages.BOX;
	}

	@Override
	protected GameObject create(String[] words, GameWorld game, Position pos) {
		Box box = new Box(game,pos);
		boolean abierto_ = false;
		if(words.length > 2 && (words[2].toLowerCase().equals("empty") || words[2].toLowerCase().equals("e")))
			abierto = true;
		else if(words.length > 2 && (words[2].toLowerCase().equals("full") || words[2].toLowerCase().equals("f")))
		box.setInitial(abierto_);
		return box;
	}

	@Override
	public boolean receiveInteraction(Mushroom obj) {
		
		return false;
	}

	@Override
	public boolean receiveInteraction(Box obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wasInPosition(Position pos) {
		return false;
	}

	@Override
	public void update() {
	
		
	}
}
