/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ , MATEI-CRISTIAN FLOREA */
package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mushroom extends MovingObject{

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
    public boolean receiveInteraction(Mario obj) {
        return isAlive() && dead();
    }




    @Override
    protected GameObject create(String[] words, GameWorld game, Position pos) throws ObjectParseException{
        Mushroom mushroom = new Mushroom(game, pos);
try {
        mushroom.setInitial(ParamParser.parseDirection(words, 2));
}
        catch(ActionParseException a) {
        	throw new ObjectParseException( Messages.UNKNOWN_MOVING_DIRECTION.formatted(String.join("", words)), a);
        }
        return mushroom;
    }

    @Override
    public GameObject clone() {
        return new Mushroom(this.game, this.pos);
    }

    @Override
    public String save() {
        return this.pos.toString() + " " + "Mushroom" + " " + this.dir.toString();
    }


}