package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends MovingObject {

    // ==========================================================
    //                      ATRIBUTOS
    // ==========================================================
  


    // ==========================================================
    //                     CONSTRUCTOR
    // ==========================================================
    public Goomba(GameWorld game, Position pos) {
        super(game, pos);
       setInitial(Action.LEFT);
    }

    Goomba() {
		super(null,null);
	}

    private void setInitial(Action dir) {
    	this.dir = dir == null ?  Action.LEFT : dir ;
    	this.lastDir = dir;
    	this.points = 100;
    }
    // ==========================================================
    //                      GETTERS / SETTERS
    // ==========================================================
    public String getIcon() {
        return Messages.GOOMBA;
    }

    public boolean isInPosition(Position pos) {
        return this.pos.equals(pos);
    }

    // ==========================================================
    //                      REPRESENTACIÓN
    // ==========================================================
    @Override
    public String toString() {
        return "Goomba";
    }


    // ==========================================================
    //                   INTERACCIONES
    // ==========================================================
    public boolean receiveInteraction(Mario other) {
        return this.isAlive() && handleDefeat();
    }

    @Override
    public boolean interactWith(GameItem item) {
        boolean canInteract = item.isInPosition(this.pos);
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

    @Override
    public boolean receiveInteraction(Goomba obj) {
        // TODO Auto-generated method stub
        return false;
    }


    // ==========================================================
    //                      UPDATE PRINCIPAL
    // ==========================================================
    @Override 
    public void automaticMove() {
    	saveLastPosition();   
        automatic();
    }


    // ==========================================================
    //                      MOVIMIENTO
    // ==========================================================
    @Override
    protected void horizontalMove() {
        dir = (dir == Action.DOWN) ? lastDir : dir;
        moveHorizontally();
        
    }


    /** Controla el movimiento horizontal y el rebote ante obstáculos. */
    private void moveHorizontally() {
        Position nextPos = pos.move(dir);
        boolean blocked = isBlockedHorizontally();

        if (blocked) {
            reverseDirection();
        } else {
            pos = nextPos;
            lastDir = dir;
        }
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
    private void reverseDirection() {
        dir = (dir == Action.LEFT) ? Action.RIGHT : Action.LEFT;
        lastDir = dir;
    }

    
  
    
    
	@Override
	protected GameObject create(String[] words, GameWorld game, Position pos) {
		Goomba goomba = new Goomba(game,pos);
		Action dir_ = null;
		
		if(words.length >= 3 ) {
			
			 dir_ = Action.parseAction(words[2].toLowerCase());
			if(!(dir_ == Action.LEFT || dir_ == Action.RIGHT)) 
				dir_ = null;
		
			
		}
		
		goomba.setInitial(dir_);
		
		return goomba;
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
