package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject{

    // ==========================================================
    //                      ATRIBUTOS
    // ==========================================================
  


    // ==========================================================
    //                     CONSTRUCTOR
    // ==========================================================
    public Mushroom(GameWorld game, Position pos) {
        super(game, pos);
        setInitial(Action.RIGHT);
    }

    Mushroom() {
        super(null, null);
    }

     // ==========================================================
    //                      GETTERS / SETTERS
    // ==========================================================
    public String getIcon() {
        return Messages.MUSHROOM;
    }


    private void setInitial(Action dir) {
    	this.dir = dir == null ?  Action.RIGHT : dir ;
    	this.lastDir = dir;
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

     // ==========================================================
    //                      REPRESENTACIÓN
    // ==========================================================
    @Override
    public String toString() {
        return "MUshroom";
    }


    // ==========================================================
    //                   INTERACCIONES
    // ==========================================================
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
        return false;
    }

    @Override
    public boolean receiveInteraction(ExitDoor obj) {
        return false;
    }

    @Override
    public boolean receiveInteraction(Mario obj) {
        if (this.isAlive()) {
            this.dead();
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveInteraction(Goomba obj) {
        return false;
    }

    @Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}

    @Override
    protected GameObject create(String[] words, GameWorld game, Position pos) {
        Mushroom mushroom = new Mushroom(game,pos);
		Action dir_ = null;
		
		if(words.length >= 3 ) {
			
			 dir_ = Action.parseAction(words[2].toLowerCase());
			if(!(dir_ == Action.LEFT || dir_ == Action.RIGHT)) 
				dir_ = null;
		
			
		}
		
		mushroom.setInitial(dir_);
		
		return mushroom;
    }

	@Override
	public boolean receiveInteraction(Box obj) {
		
		return false;
	}

}