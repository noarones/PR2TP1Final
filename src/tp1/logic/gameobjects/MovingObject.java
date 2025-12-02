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
    	
    	if (dir == Action.STOP) return;
        dir = (Action.isXMove(dir)) ? dir : lastDir;
        moveX(this.dir, canMove(this.dir));
       saveLastDir(dir);
    }


    /** Controla el movimiento horizontal y el rebote ante obstáculos. */
    protected boolean moveX(Action d , boolean canMove) {
    	if(canMove) { move(d); this.dir = d;} 
    	else reverseDirection(d);
    return true;
    }
    
 
    
    private boolean saveLastDir(Action dir) {
    	lastDir = dir;
    	return true;
    }


    /** Invierte la dirección actual y actualiza la última dirección válida. */
    protected boolean reverseDirection(Action dir) {
    	lastDir = dir;
    	this.dir = Action.oppositeAction(dir);
        
        return true;
    }

  
	protected void fall() {

		move(Action.DOWN);
		dir = Action.DOWN;
		isFalling = true;

	}

	//Puede moverse un objeto desde una posicion concreta(Generalizar movimiento)
	   protected boolean canMove(Action a, Position pos) {
	    	switch(a) {
	    	case LEFT: return  (!game.solidLeft(pos)  && !game.nextToLeftLimit(pos));
	    	case RIGHT: return !game.solidRight(pos) && !game.nextToRightLimit(pos);
	    	case UP: return !game.solidUp(pos) && game.isInBoard(pos.up());
	    	case DOWN: return !game.solidUnder(pos);
	    	
	    		default:
	    			return true;
	    	}
	    }
	   
	   //Puede moverse un objeto desde la posicion actual
	   protected boolean canMove(Action a) {
	    	switch(a) {
	    	case LEFT: return  (!game.solidLeft(pos)  && !game.nextToLeftLimit(pos));
	    	case RIGHT: return !game.solidRight(pos) && !game.nextToRightLimit(pos);
	    	case UP: return !game.solidUp(pos) && game.isInBoard(pos.up());
	    	case DOWN: return !game.solidUnder(pos);
	    	
	    		default:
	    			return true;
	    	}
	    }
	
	protected String dirStr() {
	    
	    return (Action.isYMove(dir)) ? this.lastDir.toString() : this.dir.toString(); 
	}
	
    protected boolean move(Action dir) {
    	
        saveLastPosition();
        pos = pos.move(dir);
       
        return true;
    }
	
	protected void saveLastPosition() { lastPos = lastPos.copy(pos); }
	
	protected void automaticMove() {};

	protected boolean handleDeath() {return true;};

}