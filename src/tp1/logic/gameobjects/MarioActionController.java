/*
 * package tp1.logic.gameobjects;
 

import java.util.List;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.Position;

public class MarioActionController {
    Game game;
    public void applyActions(Mario mario, List<Action> action, Game g) {
       game = g;
        boolean xMoved  = false;
        boolean movedUp = false;
        int xCount = 0;
        int yCount = 0;

        List<Action> actions = action;

        for (int i = 0; i < actions.size(); i++) {
            Action act = actions.get(i);
            if (act == null) continue;

            if (act == Action.STOP) {
                handleStop(mario);
            }
            else if (isXMove(act) && handleHorizontal(mario, act, xMoved, xCount)) {
                xMoved = true;
                xCount++;
            }
            else if (isUpMove(act) && handleUp(mario, yCount)) {
                movedUp = true;
                yCount++;
            }
            else if (isDownMove(act)) {
                handleDown(mario, movedUp);
            }
        }

      
    }

    // =====================================================================
    // STOP
    // =====================================================================

    private void handleStop(Mario mario) {
        if (!mario.isFalling()) {
            mario.setDir(Action.STOP);
            mario.setLastDir(Action.STOP);
            mario.checkInteractions();
        }
    }

    // =====================================================================
    // MOVIMIENTO HORIZONTAL
    // =====================================================================

    private boolean handleHorizontal(Mario mario, Action dir,
                                     boolean moved, int count) {
        return horizontalCondition(mario, dir, moved, count)
                && execHorizontal(mario, dir);
    }

    private boolean horizontalCondition(Mario mario, Action dir,
                                        boolean moved, int count) {
        return !moved || (mario.dir == dir && count < 4);
    }

    private boolean execHorizontal(Mario mario, Action dir) {

        if (!(isDirAvailable(mario, dir) && moveHorizontal(mario, dir))) {
            changeDirection(mario, dir == Action.LEFT ? Action.RIGHT : Action.LEFT);
        }

        game.checkInteractions(mario);
       
        return true;
    }

    private boolean isDirAvailable(Mario mario, Action dir) {
        Position pos = mario.getPos();

        return (dir == Action.LEFT  && !mario.solidLeft(pos)  && !mario.nextToLeftLimit(pos))
            || (dir == Action.RIGHT && !mario.solidRight(pos) && !mario.nextToRightLimit(pos));
    }

    private boolean moveHorizontal(Mario mario, Action moveDir) {
        mario.move(moveDir);
        mario.setLastDir(mario.getDir());
        mario.setDir(moveDir);
        return true;
    }

    private boolean changeDirection(Mario mario, Action newDir) {
        mario.setLastDir(mario.getDir());
        mario.setDir(newDir);
        return true;
    }

    // =====================================================================
    // MOVIMIENTO VERTICAL (UP / DOWN)
    // =====================================================================

    private boolean handleUp(Mario mario, int verticalCount) {
        return moveUpAvailable(mario, verticalCount) && moveUp(mario);
    }

    private boolean moveUpAvailable(Mario mario, int verticalCount) {
        Position pos = mario.getPos();

        return (verticalCount < 4)
                && ((!mario.isBig() && !mario.solidUp(pos))
                    || (mario.isBig() && !mario.solidUp(pos.up())));
    }

    private boolean moveUp(Mario mario) {
        mario.move(Action.UP);
        mario.setFalling(false);
        mario.checkInteractions();
        return true;
    }

    private void handleDown(Mario mario, boolean movedUp) {
        if (!movedUp) fallVertically(mario);
    }

    // =====================================================================
    // CAÍDA VERTICAL COMPLETA (fallVertically)
    // =====================================================================

    private void fallVertically(Mario mario) {
        Position startPos = mario.getPos();
        boolean wasOnGround = mario.solidUnder(startPos);

        while (!mario.solidUnder(mario.getPos())) {

            mario.updateLastPosFromCurrent();
            mario.setPos(mario.getPos().move(Action.DOWN));
            mario.setFalling(true);

            // OJO: respeta tu lógica original: fallenOut(pos.up().up())
            if (mario.fallenOut(mario.getPos().up().up())) {
                mario.handleDeath();
                if (mario.numLives() > 0) {
                    mario.setDir(Action.STOP);
                    mario.setFalling(false);
                }
                mario.clearPendingActions();
                return;
            } 
            else {
                mario.checkInteractions();
            }
        }

        mario.setFalling(false);

        if (wasOnGround) {
            mario.setDir(Action.STOP);
            mario.setLastDir(Action.STOP);
        }
    }

    // =====================================================================
    // PREDICADOS DE ACCIÓN
    // =====================================================================

    private boolean isXMove(Action dir) {
        return dir == Action.LEFT || dir == Action.RIGHT;
    }

    private boolean isUpMove(Action dir) {
        return dir == Action.UP;
    }

    private boolean isDownMove(Action dir) {
        return dir == Action.DOWN;
    }
} */
