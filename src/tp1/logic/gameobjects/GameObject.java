package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.util.*;
public abstract class GameObject implements GameItem {  

	protected Position pos; // If you can, make it private.
	private boolean isAlive;
	protected GameWorld game; 
	protected int points;
	public GameObject(GameWorld game, Position pos) {
		this.isAlive = true;
		this.pos = pos;
		this.game =  game;
	}
	
	
	
	public boolean isInPosition(Position p) {
		return pos.equals(p);
	}
 	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void dead(){
		this.isAlive = false;
	}
	
	// TODO implement and decide, Which one is abstract?
    public abstract boolean isSolid();

    public abstract void update();
	
	public abstract String getIcon();

	// Not mandatory but recommended
	protected void move(Action dir) {
		
	}
	
	protected Position parsePosition(String[] words) {
		String[] coords = words[0].replace("(", "").replace(")", "").split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        
        return new Position(x, y);
	}
	
	protected boolean handleDefeat() {
		dead();
		game.addPoints(points);
		return true;
	}

	
	public GameObject parse(String[] words, GameWorld game) {
		Position pos1 = parsePosition(words);
		return (words[1].equalsIgnoreCase(this.toString()) 
				 || words[1].equalsIgnoreCase(MyStringUtils.onlyUpper(this.toString()))) && game.isInBoard(pos1) ?
				      this.create(words, game, pos1) : null;
		
	}
	 protected abstract GameObject create(String[] words, GameWorld game, Position pos);
	
}
