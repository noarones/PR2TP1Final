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

    protected abstract GameObject create(String[] words, GameWorld game, Position pos);

    // ===== Movimiento =====
    protected boolean move(Action dir) {
        pos = pos.move(dir);
        return true;
    }

    // ===== Manejo de posiciones =====
    protected Position parsePosition(String[] words) {    
        String[] coords = words[0].replace("(", "").replace(")", "").split(",");
        return new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }

    // ===== Manejo de derrota / puntuación =====
    protected boolean handleDefeat() {
        dead();
        game.addPoints(points);
        return true;
    }

    // ===== Creación / parseo de objetos a partir de descripción =====
    public GameObject parse(String[] words, GameWorld game) {
        Position pos1 = parsePosition(words);
        return (words[1].equalsIgnoreCase(this.toString()) 
                || words[1].equalsIgnoreCase(MyStringUtils.onlyUpper(this.toString()))) 
                && game.isInBoard(pos1) ?
                    this.create(words, game, pos1) : null;
    }

}
