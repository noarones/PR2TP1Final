/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends MovingObject {

	
    // ===== Constructores =====
    public Goomba(GameWorld game, Position pos) {
        super(game, pos);
        setInitial(Action.LEFT);
    }

    Goomba() {
        super(null,null);
    }

    // ===== Inicialización =====
    private void setInitial(Action dir) {
    	
        if(dirValido(dir)) this.dir = dir;
        this.lastDir = dir;
        this.points = 100;
    }

    private static boolean  dirValido(Action dir) {
    	return dir == Action.LEFT || dir == Action.RIGHT; 
    }
    // ===== Representación y estado =====
    @Override
    public String getIcon() {
        return Messages.GOOMBA;
    }

    public boolean isInPosition(Position pos) {
        return this.pos.equals(pos);
    }

    @Override
    public String toString() {
        return "Goomba";
    }

    // ===== Interacciones con otros objetos =====
    public boolean receiveInteraction(Mario other) {
        return this.isAlive() && this.handleDefeat();
    }

    @Override
    public boolean interactWith(GameItem item) {        
        return item.isInPosition(this.pos) ? item.receiveInteraction(this) : false;
    }



    // ===== Movimiento automático =====
    @Override 
    public void automaticMove() {
        saveLastPosition();   
        automatic();
    }

    // ===== Creación dinámica =====
    @Override
    protected GameObject create(String[] words, GameWorld game, Position pos) throws ObjectParseException{
    	if(words.length > 3)
    		throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR.formatted(String.join(" ", words)));
    	try {
    		
    		
        Goomba goomba = new Goomba(game, pos);
        Action dir = ParamParser.parseDirection(words, 2);
        
        if(!dirValido(dir)) 
        	throw new ObjectParseException(Messages.INVALID_MOVING_DIRECTION.formatted(String.join(" ", words)));
       
        goomba.setInitial(dir);
        
        return goomba;
        
    } catch(ActionParseException a) {
    	throw new ObjectParseException(Messages.UNKNOWN_MOVING_DIRECTION.formatted(String.join(" ", words)), a);
    } 
   
    }

}
