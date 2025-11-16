/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ , MATEI-CRISTIAN FLOREA */
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

    protected void horizontalMove() {
        dir = (dir == Action.DOWN) ? lastDir : dir;
        moveHorizontally();
        saveLastDir(dir);
    }


    /** Controla el movimiento horizontal y el rebote ante obstáculos. */
    private boolean moveHorizontally() {
    	
        return isBlockedHorizontally() ? reverseDirection() : (move(dir));
    }
    
    private boolean saveLastDir(Action dir) {
    	lastDir = dir;
    	return true;
    }

    /** Devuelve true si el movimiento horizontal está bloqueado. */
    private boolean isBlockedHorizontally() {
        return switch (dir) {
            case LEFT -> game.solidLeft(pos) || game.nextToLeftLimit(pos);
            case RIGHT -> game.solidRight(pos) || game.nextToRightLimit(pos);
            default -> false;
        };
    }

    /** Invierte la dirección actual y actualiza la última dirección válida. */
    private boolean reverseDirection() {
        dir = (dir == Action.LEFT) ? Action.RIGHT : Action.LEFT;
        return true;
    }

    
  
	protected void fall() {

		move(Action.DOWN);
		dir = Action.DOWN;
		isFalling = true;

	}

 
	protected void saveLastPosition() { lastPos = lastPos.copy(pos); }
	
	protected void automaticMove() {};

	protected void handleDeath() {};

}