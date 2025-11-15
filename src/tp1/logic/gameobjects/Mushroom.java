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
        return isAlive() && dead();
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
        Mushroom mushroom = new Mushroom(game, pos);

        mushroom.setInitial(ParamParser.parseDirection(words, 2));

        return mushroom;
    }

	@Override
	public boolean receiveInteraction(Box obj) {
		
		return false;
	}

}