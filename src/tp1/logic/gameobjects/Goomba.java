package tp1.logic.gameobjects;

import tp1.logic.Action;
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
        return this.isAlive() && this.handleDefeat();
    }

    @Override
    public boolean interactWith(GameItem item) {        
        return  item.isInPosition(this.pos) ? item.receiveInteraction(this) : false;
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
    public boolean receiveInteraction(Goomba obj) {
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
    protected GameObject create(String[] words, GameWorld game, Position pos) {
        Goomba goomba = new Goomba(game, pos);

        goomba.setInitial(ParamParser.parseDirection(words, 2));
        
        return goomba;
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
