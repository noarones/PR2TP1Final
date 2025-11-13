package tp1.logic.gameobjects;



import tp1.logic.*;

public abstract class MovingObject extends GameObject{

	protected boolean isFalling;
	protected Action dir;
	protected Action lastDir;
	protected Position lastPos;

	public MovingObject(GameWorld game, Position pos) {
		super(game, pos);
		isFalling = false;
		this.lastPos = new Position(0,0);
	}



	@Override
	public boolean isSolid() { return false; }

	@Override

	public void update() {
		
		if(!isAlive()) { handleDeath(); return; }

		automaticMove();
	}

	protected void automatic() {

		if (!game.solidUnder(pos)) fall();

		else horizontalMove();
		

	}

	protected void horizontalMove() {}

	protected void fall() {

		pos = pos.move(Action.DOWN);

		dir = Action.DOWN;

		isFalling = true;

	}

	public boolean wasInPosition(Position pos) {
		return lastPos.equals(pos);
	}
 
	protected void saveLastPosition() {
        lastPos = lastPos.copy(pos);
    }
	
	
	
	
	protected void automaticMove() {};

	protected void handleDeath() {};

}