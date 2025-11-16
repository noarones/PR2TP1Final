/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

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
        return item.isInPosition(this.pos.under()) ? item.receiveInteraction(this) : false;
    }

    @Override
    public boolean receiveInteraction(Mario obj) {
        abierto = true;
        game.addPoints(points);
        game.addGameObject(new String[] { pos.up().toString(), "Mushroom" }, "spawn");
        
        return isAlive();
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

    @Override
    public boolean receiveInteraction(Mushroom obj) {
        return false;
    }

    @Override
    public boolean receiveInteraction(Box obj) {
        return false;
    }

    // ===== Colisión =====
    @Override
    public boolean isSolid() {
        return true;
    }

    // ===== Creación dinámica =====
    @Override
    protected GameObject create(String[] words, GameWorld game, Position pos) {
        Box box = new Box(game, pos);
        box.setInitial(ParamParser.parseBoolean(words, 2, "empty", "e", "full", "f", false));
        return box;
    }

    // ===== Actualización del objeto =====
    @Override
    public void update() {
        // No requiere actualización
    }

}
