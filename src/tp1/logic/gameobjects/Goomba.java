/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

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
        this.dir = dir == null ?  Action.LEFT : dir ;
        this.lastDir = dir;
        this.points = 100;
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
    protected GameObject create(String[] words, GameWorld game, Position pos) {
        Goomba goomba = new Goomba(game, pos);
        goomba.setInitial(ParamParser.parseDirection(words, 2));
        return goomba;
    }

}
