/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic.gameobjects;

import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.ObjectParseException;
import tp1.logic.Action;
import tp1.logic.GameItem;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends MovingObject {

    // ===== Estado interno =====
    private boolean big;
    private List<Action> pendingActions;

    // ===== Constructores =====
    public Mario(GameWorld game, Position pos) {
        super(game, pos);
        setInitial(true, Action.RIGHT);
    }

    Mario() {
        super(null, null);
    }

    // ===== Inicialización =====
    private void setInitial(Boolean big, Action dir) {
        this.big = (big == null) ? true : big;
        this.dir = (dir == null) ? Action.RIGHT : dir;
        this.lastDir = this.dir;
        this.pendingActions = new ArrayList<>();
    }

    // ===== Representación visual =====
    public String getIcon() {
        return (dir == Action.DOWN && lastDir == Action.STOP)
                ? Messages.MARIO_STOP
                : switch (dir) {
                      case LEFT -> Messages.MARIO_LEFT;
                      case STOP -> Messages.MARIO_STOP;
                      default -> Messages.MARIO_RIGHT;
                  };
    }

    public boolean isBig() {
        return this.big;
    }

    @Override 
    public String toString() {
        return "Mario";
    }

    // ===== Gestión de acciones pendientes =====
    public void addAction(Action act) {
        pendingActions.add(act);
    }

    private void clearActions() {
        pendingActions.clear();
    }

    // ===== Movimiento automático =====
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
        boolean movedDown = false;
        int XCount = 0;
        int YCount = 0;

        for (int i = 0; i < pendingActions.size(); i++) {
            Action act = pendingActions.get(i);
            if (act != null) {
                switch (act) {
                    case LEFT -> { if (handleLeft(XMoved, XCount)) { XMoved = true; XCount++; } }
                    case RIGHT -> { if (handleRight(XMoved, XCount)) { XMoved = true; XCount++; } }
                    case UP -> { if (handleUp(movedDown, YCount)) { movedUp = true; YCount++; } }
                    case DOWN -> handleDown(movedUp);
                    case STOP -> handleStop();
                }
            }
        }
        clearActions();
    }

    // ===== Movimiento horizontal =====
    private boolean handleLeft(boolean horizontalMoved, int horizontalCount) {
        if (!horizontalMoved || (dir == Action.LEFT && horizontalCount < 4)) {
            if (!game.solidLeft(pos) && !game.nextToLeftLimit(pos)) 
                moveHorizontal(Action.LEFT);
            else 
                changeDirection(Action.RIGHT);
            game.checkInteractions(this);
            return true;
        }
        return false;
    }

    private boolean handleRight(boolean horizontalMoved, int horizontalCount) {
        if (!horizontalMoved || (dir == Action.RIGHT && horizontalCount < 4)) {
            if (!game.solidRight(pos) && !game.nextToRightLimit(pos)) 
                moveHorizontal(Action.RIGHT);
            else 
                changeDirection(Action.LEFT);
            game.checkInteractions(this);
            return true;
        }
        return false;
    }

    private void moveHorizontal(Action moveDir) {
        pos = pos.move(moveDir);
        lastDir = dir;
        dir = moveDir;
    }

    private void changeDirection(Action newDir) {
        lastDir = dir;
        dir = newDir;
    }

    // ===== Movimiento vertical =====
    private boolean handleUp(boolean movedDown, int verticalCount) {
        if (!movedDown && verticalCount < 4 && !game.solidUp(pos)) {
            pos = pos.move(Action.UP);
            isFalling = false;
            game.checkInteractions(this);
            return true;
        }
        return false;
    }

    private void handleDown(boolean movedUp) {
        if (!movedUp) fallVertically();
    }

    private void handleStop() {
        if (!isFalling) {
            dir = Action.STOP;
            this.lastDir = Action.STOP;
            game.checkInteractions(this);
        }
    }

    private void fallVertically() {
        boolean wasOnGround = game.solidUnder(pos);

        while (!game.solidUnder(pos)) {
            lastPos = lastPos.copy(pos);
            pos = pos.move(Action.DOWN);
            isFalling = true;

            if (game.fallenOut(pos.up().up())) {
                handleDeath();
                if (game.numLives() > 0) {
                    dir = Action.STOP;
                    isFalling = false;
                }
                pendingActions.clear();
                return;
            } else {
                game.checkInteractions(this);
            }
        }

        isFalling = false;
        if (wasOnGround) {
            dir = Action.STOP;
            this.lastDir = Action.STOP;
        }
    }

    // ===== Movimiento horizontal auxiliar =====
    @Override
    protected void horizontalMove() {
        isFalling = false;

        if (dir == Action.STOP) return;

        Action moveDir = (dir == Action.LEFT || dir == Action.RIGHT) ? dir : lastDir;

        if (moveDir == Action.RIGHT) moveRight();
        else if (moveDir == Action.LEFT) moveLeft();
    }

    private void moveRight() {
        if (!game.solidRight(pos) && !game.nextToRightLimit(pos)) pos = pos.move(Action.RIGHT);
        else dir = lastDir = Action.LEFT;
    }

    private void moveLeft() {
        if (!game.solidLeft(pos) && !game.nextToLeftLimit(pos)) pos = pos.move(Action.LEFT);
        else dir = lastDir = Action.RIGHT;
    }

    // ===== Interacciones con objetos =====
    @Override
    public boolean interactWith(GameItem item) {
        return item.isInPosition(this.pos) ? item.receiveInteraction(this) : false;
    }

    public boolean marioExited() {
        game.marioExited();
        return true;
    }

    @Override
    public boolean receiveInteraction(Land obj) { return false; }

    @Override
    public boolean receiveInteraction(ExitDoor obj) { return true; }

    @Override
    public boolean receiveInteraction(Mario obj) { return false; }

    @Override
    public boolean receiveInteraction(Mushroom obj) {
        if (this.isBig() && obj.isInPosition(this.pos.up())) {
            return obj.receiveInteraction(this);
        }
        if (!this.isBig()) {
            this.big = true;
        }
        return true;
    }

    @Override
    public boolean receiveInteraction(Goomba obj) {
        if (lastPos != null && obj.isInPosition(lastPos.under())) {
            // Mata al Goomba sin perder tamaño
            return true;
        }

        if (isBig() && obj.isInPosition(this.pos.up())) {
            this.big = false;
            return obj.receiveInteraction(this);
        }

        // Colisión lateral o frontal → Mario pierde tamaño o vida
        if (obj.isInPosition(this.pos)) {
            if (isBig()) this.big = false;
            else {
                game.removeLife();
                this.dead();
                if (game.numLives() > 0) {
                    game.reset(-2);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveInteraction(Box obj) {
        return obj.receiveInteraction(this);
    }

    // ===== Posición considerando tamaño =====
    @Override
    public boolean isInPosition(Position pos) {
        return super.isInPosition(pos) || (isAlive() && isBig() && super.isInPosition(pos.under()));
    }

    // ===== Manejo de muerte =====
    @Override 
    protected void handleDeath() {
        game.removeLife();
        if (game.numLives() > 0) {
            game.reset(-2);
        }
    }

    // ===== Creación dinámica =====
    @Override
    protected GameObject create(String[] words, GameWorld game, Position pos) throws ObjectParseException {
    	Mario mario = new Mario(game, pos);
	    Action dir;
	    try {
	        dir = ParamParser.parseDirection(words, 2);
	    } 
	    catch (ObjectParseException obj) {
	        throw new ObjectParseException(Messages.UNKNOWN_MOVING_DIRECTION.formatted(String.join(" ", words)), obj);
	    }
	    if (words.length > 3) {
	        String status = words[3].toLowerCase();
	        if (!status.equals("big") && !status.equals("b")   && !status.equals("small") && !status.equals("s")) {
	            throw new ObjectParseException(Messages.INVALID_MARIO_SIZE.formatted(String.join(" ", words)));
	        }
	    }

	    boolean isBig = ParamParser.parseBoolean(words, 3, "big", "b", "small", "s", false);
	    mario.setInitial(isBig, dir);
	    game.setAsMainCharacter(mario);
	    return mario;
    }

}
