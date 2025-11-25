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
import tp1.exceptions.ActionParseException;
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

    protected abstract GameObject create(String[] words, GameWorld game, Position pos) throws ObjectParseException, OffBoardException;

    // ===== Movimiento =====
    protected boolean move(Action dir) {
        pos = pos.move(dir);
        return true;
    }

    // ===== Manejo de posiciones =====
    protected Position parsePosition(String[] words) throws PositionParseException {

        try {
            // Extraer coordenadas (ej: "(a,4)" → "a", "4")
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
    public GameObject parse(String[] words, GameWorld game)
            throws ObjectParseException, OffBoardException {

        Position pos1;

        // 1. Parsear la posición
        try {
            pos1 = parsePosition(words);
        } catch (PositionParseException e) {
            throw new ObjectParseException(
                Messages.INVALID_OBJECT_POSITION.formatted(String.join(" ", words)),
                e
            );
        }

        // 2. ¿Es el tipo correcto?
        boolean matchesType =
                words[1].equalsIgnoreCase(this.toString()) ||
                words[1].equalsIgnoreCase(MyStringUtils.onlyUpper(this.toString()));

        if (!matchesType) return null;
        

        // 3. Tipo coincide → ahora cualquier fallo es ERROR REAL
        if (!game.isInBoard(pos1)) {
            throw new OffBoardException(
                Messages.OFF_BOARD_OBJECT.formatted(String.join(" ", words))
            );
        }

        // 4. Crear el objeto válido
        return this.create(words, game, pos1);
    }
    
    
    
    
	public  boolean receiveInteraction(Land obj) { return false; }
	
	public  boolean receiveInteraction(ExitDoor obj) { return false; }
	
	public  boolean receiveInteraction(Mario obj) { return false; }
	
	public  boolean receiveInteraction(Goomba obj) { return false; }
	
	public  boolean receiveInteraction(Mushroom obj) { return false; }
	
	public  boolean receiveInteraction(Box obj) { return false; }

}
