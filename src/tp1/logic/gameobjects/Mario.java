/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.ActionParseException;
import tp1.exceptions.ObjectParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends MovingObject implements MarioPlayer{

    // ================================================================
    // ======================= ESTADO INTERNO ==========================
    // ================================================================
    private boolean big;
    private List<Action> pendingActions;

    

    // ================================================================
    // ======================== CONSTRUCTORES ==========================
    // ================================================================
    public Mario(GameWorld game, Position pos) {
        super(game, pos);
        setInitial(true, Action.RIGHT);
    }

    public Mario(Mario mario){
        super(mario.game, mario.pos);
        this.big = mario.big;
        this.dir = mario.dir;
        this.lastDir = mario.lastDir;
        this.pendingActions = new ArrayList<>();
    }

    Mario() {
        super(null, null);
    }



    // ================================================================
    // ======================== INICIALIZACIÓN =========================
    // ================================================================
    private void setInitial(Boolean big, Action dir) {
        this.big = (big == null) ? true : big;
        this.dir = (dir == null) ? Action.RIGHT : dir;
        this.lastDir = this.dir;
        this.pendingActions = new ArrayList<>();
    }

    private static boolean tamValido(String str) {
    	return str.equalsIgnoreCase("big") ||
    			str.equalsIgnoreCase("small") ||
    			str.equalsIgnoreCase("s") ||
    			str.equalsIgnoreCase("b");
    }



    // ================================================================
    // ================== REPRESENTACIÓN VISUAL ========================
    // ================================================================
    public String getIcon() {
        return (dir == Action.DOWN && lastDir == Action.STOP)
                ? Messages.MARIO_STOP
                : switch (dir) {
                      case LEFT -> Messages.MARIO_LEFT;
                      case STOP -> Messages.MARIO_STOP;
                      default -> Messages.MARIO_RIGHT;
                  };
    }

    @Override
    public boolean isBig() {
        return this.big;
    }

    @Override 
    public String toString() {
        return "Mario";
    }



    // ================================================================
    // ================= GESTIÓN DE ACCIONES PENDIENTES ================
    // ================================================================
    public void addAction(Action act) {
        pendingActions.add(act);
    }

    private void clearActions() {
        pendingActions.clear();
    }



    // ================================================================
    // ========================== CICLO DE JUEGO =======================
    // ================================================================
    @Override
    protected void automaticMove() {
        saveLastPosition();

        if (game.fallenOut(pos)) { handleDeath(); }

        if (pendingActions.size() > 0) action();
        else automatic();
    }

    
    private void action() {
        boolean XMoved = false;
        boolean movedUp = false;
        int XCount = 0;
        int YCount = 0;

        for (int i = 0; i < pendingActions.size(); i++) {
            Action act = pendingActions.get(i);

            if (act != null) {
                if(act == Action.STOP) handleStop();
                else if (Action.isXMove(act) && handleHorizontal(act,XMoved, XCount)) { 
                    XMoved = true; XCount++; 
                }
                else if(Action.isUpMove(act) && handleUp(YCount)) { 
                    movedUp = true; YCount++; 
                }
                else if(Action.isDownMove(act)) { 
                    handleDown(movedUp); 
                }
            }
        }
        
        clearActions();
    }



    // ================================================================
    // ========================== MANEJO STOP ==========================
    // ================================================================
    private void handleStop() {
        dir = Action.STOP;
        this.lastDir = Action.STOP;
 
    }



    // ================================================================
    // ===================== MANEJO HORIZONTAL =========================
    // ================================================================
    private boolean handleHorizontal(Action dir, boolean moved, int count) {
        return (!moved || (this.dir == dir && count < 4 )) && moveX(dir) && game.checkInteractions(this);
    }


    // ================================================================
    // ======================= MOVIMIENTO VERTICAL =====================
    // ================================================================
    private boolean handleUp(int verticalCount) {
        return canMoveUp(verticalCount) && moveUp();
    }

    private void handleDown(boolean movedUp) {
        if (!movedUp) fallVertically();
    }

    private boolean canMoveUp(int verticalCount) {
    	return (verticalCount < 4) && canMove(Action.UP, isBig() ? pos.up() : pos);
    }

    private boolean moveUp() {
    	move(Action.UP);
        isFalling = false;
        game.checkInteractions(this);
        return true; 
    }

    private void fallVertically() {

        if(!canMove(Action.DOWN)) { handleStop(); return; }
        
        while (canMove(Action.DOWN) && move(Action.DOWN) && !handleFallenOut()) 
            game.checkInteractions(this);
    }


    private boolean handleFallenOut() {
    	return game.fallenOut(pos) && handleDeath();
    }

    // ================================================================
    // ===================== INTERACCIONES CON OBJETOS =================
    // ================================================================
    @Override
    public boolean interactWith(GameItem item) {
        return super.interactWith(item) && item.receiveInteraction(this);
    }

    public boolean marioExited() {
        game.marioExited();
        return true;
    }

    @Override
    public boolean receiveInteraction(ExitDoor obj) { return true; }

    @Override
    public boolean receiveInteraction(Mushroom obj) { return this.big = true; }

    @Override
    public boolean receiveInteraction(Goomba obj) {
    	if (obj.isInPosition(lastPos.under())) return true;
        if (obj.isInPosition(this.pos.up())) 
            return !(this.big = false) && obj.receiveInteraction(this);

        return !isBig() && handleDeath() || !(this.big = false);
    }

    @Override
    public boolean receiveInteraction(Box obj) {
        return obj.receiveInteraction(this);
    }



    // ================================================================
    // ===================== POSICIÓN / GEOMETRÍA ======================
    // ================================================================
    @Override
    public boolean isInPosition(Position pos) {
        return super.isInPosition(pos)
            || (isAlive() && isBig() && super.isInPosition(pos.under()));
    }



    // ================================================================
    // ====================== CREACIÓN DINÁMICA ========================
    // ================================================================
    @Override
    protected GameObject create(String[] words, GameWorld game, Position pos) 
        throws ObjectParseException {

        Mario mario = new Mario(game, pos);

        if(words.length > 4)
        	throw new ObjectParseException(Messages.OBJECT_PARSE_ERROR.formatted(String.join(" ", words)));

        if(words.length > 3 && !tamValido(words[3]))
        	throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", words)));

        try {
            mario.setInitial(
                ParamParser.parseBoolean(words, 3, "big", "b", "small", "s", true),
                ParamParser.parseDirection(words, 2)
            );
        }
        catch(ActionParseException a) {
        	throw new ObjectParseException(
                Messages.UNKNOWN_MOVING_DIRECTION.formatted(String.join(" ", words)), a
            );
        }
        return mario;
    }



    // ================================================================
    // ======================== MANEJO DE MUERTE =======================
    // ================================================================
    @Override 
    protected boolean handleDeath() {
        game.removeLife();
        this.dead(); 
        if (game.numLives() > 0) 
            game.reset(2025,true);
        
        
        pendingActions.clear();
        return true;
    }



    // ================================================================
    // ========================== PREDICADOS ===========================
    // ================================================================
    @Override
    protected boolean canInteract(GameItem item) {
    	return (!isBig() && item.isInPosition(this.pos)) ||
    		   (isBig() && (item.isInPosition(this.pos.up()) ||
    		                item.isInPosition(this.pos)));
    }



    // ================================================================
    // ======================== CLON Y SAVE ============================
    // ================================================================
    @Override
    public GameObject clone() { 
        return new Mario(this);
    }

    private String sizeStr() {
    	return isBig() ? "Big" : "Small";
    }
        
    @Override
    public String save() {
        return "%s %s %s %s".formatted(pos,this,dirStr(),sizeStr());
    }

}
