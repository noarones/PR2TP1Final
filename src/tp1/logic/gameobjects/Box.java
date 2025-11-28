/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;


import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
public class Box extends GameObject {

    // ===== Estado interno =====
    private boolean abierto = false;

    // ===== Constructores =====
    public Box(GameWorld game, Position pos) {
        super(game, pos);
        setInitial(abierto);
    }

    Box() {
        super(null,null);
    }

    // ===== Inicialización =====
    private void setInitial(boolean abierto) {
        this.abierto = abierto;
        this.points = 50;
    }

    public static boolean statusValido(String str) {
    	 
    	return str.equalsIgnoreCase("empty") ||
    			str.equalsIgnoreCase("e") ||
    			str.equalsIgnoreCase("full") ||
    			str.equalsIgnoreCase("f") ;
    }
    
    // ===== Representación textual =====
    @Override
    public String toString() {
        return "Box";
    }

    @Override
    public String getIcon() {
        return abierto ? Messages.EMPTY_BOX : Messages.BOX;
    }

    // ===== Interacciones con otros objetos =====
    @Override
    public boolean interactWith(GameItem item) {
        boolean canInteract = (!item.isBig() && item.isInPosition(this.pos.under())) ||
                              (item.isBig() && (item.isInPosition(this.pos.under()) || item.isInPosition(this.pos)));
        if (canInteract) {
            return item.receiveInteraction(this);
        }
        return false;
    }

    @Override
    public boolean receiveInteraction(Mario obj) {
        if (!abierto) {
            abierto = true;
            game.addPoints(points);
            try {
                game.addGameObject(new String[] { pos.up().toString(), "Mushroom" }, "spawn");
            } catch (GameModelException e) {
            
            }
            return true;
        }
        return false;
    }
    


    // ===== Colisión =====
    @Override
    public boolean isSolid() {
        return true;
    }

    // ===== Creación dinámica =====
    @Override

    protected GameObject create(String[] words, GameWorld game, Position pos) throws GameModelException{


    	
    	if(words.length > 3)
    		throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR.formatted(String.join(" ", words)));
        Box box = new Box(game, pos);
        
        if(words.length > 2 &&!statusValido(words[2]))
        	throw new ObjectParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", words)));
        box.setInitial(ParamParser.parseBoolean(words, 2, "empty", "e", "full", "f", false));
        return box;
    }

    // ===== Actualización del objeto =====
    @Override
    public void update() {
        // No requiere actualización
    }

    @Override
    public GameObject clone() {
        Box clone = new Box(this.game, this.pos);
        clone.abierto = this.abierto;
        return clone;
    }

    @Override
    public String save() {
        String statusStr = abierto ? "Empty" : "Full";
        return this.pos.toString() + " " + this.toString() + " " + statusStr;
    }
}
