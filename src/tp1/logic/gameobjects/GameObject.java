/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.util.*;

import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.view.Messages;

public abstract class GameObject implements GameItem {  

    // ===== Estado interno del objeto =====
    protected Position pos; 
    private boolean isAlive;
    protected GameWorld game; 
    protected int points;

    // ===== Constructor =====
    public GameObject(GameWorld game, Position pos) {
        this.isAlive = true;
        this.pos = pos;
        this.game =  game;
    }

    // ===== Estado y vida del objeto =====
    public boolean isInPosition(Position p) {
        return pos.equals(p);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean dead() {
        this.isAlive = false;
        return true;
    }

    // ===== Métodos abstractos que definen comportamiento de subclases =====
    public abstract boolean isSolid();

    public abstract void update();

    public abstract String getIcon();

    protected abstract GameObject create(String[] words, GameWorld game, Position pos) throws GameModelException;


    // ===== Movimiento =====
    protected boolean move(Action dir) {
        pos = pos.move(dir);
        return true;
    }

    // ===== Manejo de posiciones =====
    protected Position parsePosition(String[] words) throws PositionParseException {

        try {
            // Extraer coordenadas
            String cleaned = words[0].replace("(", "").replace(")", "");
            String[] coords = cleaned.split(",");

            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);

            return new Position(row, col);

        } catch (NumberFormatException nfe) {
            // Error en parseInt → posición inválida
            throw new PositionParseException(
                Messages.INVALID_POSITION.formatted(words[0]),
                nfe
            );
        } catch (Exception e) {
            // Cualquier otro fallo: formato inválido
            throw new PositionParseException(
                Messages.INVALID_POSITION.formatted(words[0]),
                e
            );
        }
    }

    // ===== Manejo de derrota / puntuación =====
    protected boolean handleDefeat() {
        dead();
        game.addPoints(points);
        return true;
    }

    // ===== Creación / parseo de objetos a partir de descripción =====
    public GameObject parse(String[] words, GameWorld game) throws GameModelException {

        try {
       
        	Position pos1 = parsePosition(words);
     
        	assertInBoard(pos1, words, game);
        
        	return matchesType(words) ? this.create(words, game, pos1) : null;
       
        
        }
        
        catch (PositionParseException e) {
        	
            throw new ObjectParseException(
                Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", words)),
                e
            );   
        }
        
    }
    
    
    private boolean matchesType(String[] words) {
    	return words[1].equalsIgnoreCase(this.toString()) ||
                words[1].equalsIgnoreCase(MyStringUtils.onlyUpper(this.toString()));
    }
    
    private void assertInBoard(Position pos, String[] words, GameWorld game) throws OffBoardException{
        if (!game.isInBoard(pos)) 
            throw new OffBoardException(
                Messages.OFF_BOARD_OBJECT.formatted(String.join(" ", words))
            );
        
    }

    protected boolean canInteract(GameItem item) {
    	return item.isInPosition(pos);
    };   
    

    public abstract String save();

    public abstract GameObject clone();

    public boolean isBig() { return false;}
    
	public  boolean receiveInteraction(Land obj) { return false; }
	
	public  boolean receiveInteraction(ExitDoor obj) { return false; }
	
	public  boolean receiveInteraction(Mario obj) { return false; }
	
	public  boolean receiveInteraction(Goomba obj) { return false; }
	
	public  boolean receiveInteraction(Mushroom obj) { return false; }
	
	public  boolean receiveInteraction(Box obj) { return false; }
	
	public boolean interactWith(GameItem item) { return canInteract(item); }
 
}
