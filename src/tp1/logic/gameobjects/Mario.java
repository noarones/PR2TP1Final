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
        game.checkInteractions(this);
    }



    // ================================================================
    // ===================== MANEJO HORIZONTAL =========================
    // ================================================================
    private boolean handleHorizontal(Action dir, boolean moved, int count) {
        return horizontalCondition(dir,moved,count) && execHorizontal(dir,moved,count);
    }

    private boolean execHorizontal(Action dir, boolean moved, int count) {
        if ( !( isDirAvailable(dir,moved,count) && moveHorizontal(dir) ) )
              changeDirection(dir == Action.LEFT ? Action.RIGHT : Action.LEFT);

        game.checkInteractions(this);
        
        return true;
    }

    private boolean isDirAvailable(Action dir, boolean moved, int count) {
    	switch(dir) {
    	case LEFT:
    		if (!game.solidLeft(pos) && !game.nextToLeftLimit(pos)) {
    			if (isBig() && !game.solidLeft(pos.up())) {
    				return true;
    			}
    			else if (!isBig()) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    	case RIGHT:
    		if (!game.solidRight(pos) && !game.nextToRightLimit(pos)) {
    			if (isBig() && !game.solidRight(pos.up())) {
    				return true;
    			}
    			else if (!isBig()) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    	default:
    		return false;
    	}
    }

    private boolean horizontalCondition(Action dir, boolean moved, int count ) {
    	return !moved || (this.dir == dir && count < 4);
    }

    private boolean moveHorizontal(Action moveDir) {
        move(moveDir);
        lastDir = dir;
        dir = moveDir;
        return true;
    }

    private boolean changeDirection(Action newDir) {
        lastDir = dir;
        dir = newDir;
        return true;
    }



    // ================================================================
    // ======================= MOVIMIENTO VERTICAL =====================
    // ================================================================
    private boolean handleUp(int verticalCount) {
        return moveUpAvailable(verticalCount) && moveUp();
    }

    private void handleDown(boolean movedUp) {
        if (!movedUp) fallVertically();
    }

    private boolean moveUpAvailable(int verticalCount) {
    	return (verticalCount < 4) && !game.solidUp(isBig() ? pos.up() : pos);
    }

    private boolean moveUp() {
    	move(Action.UP);
        isFalling = false;
        game.checkInteractions(this);
        return true;
    }

    private void fallVertically() {

        if(game.solidUnder(pos)) { handleStop(); return; }
        
        while (!game.solidUnder(pos) && moveDown() && !handleFallenOut()) 
            game.checkInteractions(this);
    }

    private boolean moveDown() {
        lastPos = lastPos.copy(pos);
        pos = pos.move(Action.DOWN);
        return true;
    }

    private boolean handleFallenOut() {
    	return game.fallenOut(pos) && handleDeath();
    }



    // ================================================================
    // ====================== MOVIMIENTO AUTOMÁTICO ====================
    // ================================================================
    @Override
    protected void horizontalMove() {
        isFalling = false;

        if (dir == Action.STOP) return;

        Action m = (Action.isXMove(dir)) ? dir : lastDir;

        if (m == Action.RIGHT) moveRight();
        else if (m == Action.LEFT) moveLeft();
    }

    private void moveRight() {
    	if (!game.solidRight(pos) && !game.nextToRightLimit(pos)) {
    		if (isBig() && !game.solidRight(pos.up())) {
    			move(Action.RIGHT);
    		}
    		else if (!isBig()) {
    			move(Action.RIGHT);
    		}
    		else {
    			changeDirection(Action.LEFT);
    		}
    	}
    	else {
    		changeDirection(Action.LEFT);
    	}
    }

    private void moveLeft() {
    	if (!game.solidLeft(pos) && !game.nextToLeftLimit(pos)) {
    		if (isBig() && !game.solidLeft(pos.up())) {
    			move(Action.LEFT);
    		}
    		else if (!isBig()) {
    			move(Action.LEFT);
    		}
    		else {
    			changeDirection(Action.RIGHT);
    		}
    	}
    	else {
    		changeDirection(Action.RIGHT);
    	}
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
