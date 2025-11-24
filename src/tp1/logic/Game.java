/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 *  MATEI-CRISTIAN FLOREA
 */
package tp1.logic;
import java.util.ArrayList;
import java.util.List;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.exceptions.ObjectParseException;
public class Game implements GameStatus, GameWorld, GameModel {

    // ===== Constantes del juego =====
    public static final int DIM_X = 30;
    public static final int DIM_Y = 15;

    // ===== Estado interno del juego =====
    private int remainingTime;
    private boolean won = false;
    private int nLevel;
    private int points;
    private int lives;
    private Mario mario;
    private GameObjectContainer gameObjects;
    private List<GameObject> spawnObjects = new ArrayList<>();
    private boolean playerExits = false;

    // ===== Constructor =====
    public Game(int nLevel) {
        initLevel(nLevel);
    }

    // ===== Métodos de inicialización de nivel =====
    private void initLevel(int lvl) {
        setInitialGameValues(lvl);
        if(lvl == -1) return;

        // Construcción del mapa
        buildBaseGround();
        buildPlatforms();
        buildFinalJump();
        addCharacters(lvl);
    }

    private void setInitialGameValues(int lvl) {
        this.nLevel = lvl;
        this.remainingTime = 100;
        this.lives = 3;
        this.points = 0;
        gameObjects = new GameObjectContainer();
    }

    /* Construcción del mapa (refactorizado para evitar duplicación) */
    private void buildBaseGround() {
        for(int c = 0; c < 15; c++) {
            gameObjects.add(new Land(this,new Position(13, c)));
            gameObjects.add(new Land(this,new Position(14, c)));		
        }

        gameObjects.add(new Land(this,new Position(Game.DIM_Y - 3, 9)));
        gameObjects.add(new Land(this,new Position(Game.DIM_Y - 3, 12)));

        for (int c = 17; c < Game.DIM_X; c++) {
            gameObjects.add(new Land(this,new Position(Game.DIM_Y - 2, c)));
            gameObjects.add(new Land(this,new Position(Game.DIM_Y - 1, c)));		
        }
    }

    private void buildPlatforms() {
        gameObjects.add(new Land(this,new Position(9,2)));
        gameObjects.add(new Land(this,new Position(9,5)));
        gameObjects.add(new Land(this,new Position(9,6)));
        gameObjects.add(new Land(this,new Position(9,7)));
        gameObjects.add(new Land(this,new Position(5,6)));
    }

    private void buildFinalJump() {
        int tamX = 8, tamY = 8;
        int posIniX = Game.DIM_X - 3 - tamX;
        int posIniY = Game.DIM_Y - 3;

        for(int col = 0; col < tamX; col++) {
            for (int fila = 0; fila < col + 1; fila++) {
                gameObjects.add(new Land(this,new Position(posIniY - fila, posIniX + col)));
            }
        }
    }

    private void addCharacters(int lvl) {
        this.mario = new Mario(this, new Position(Game.DIM_Y - 3, 0));
        gameObjects.add(this.mario);

        gameObjects.add(new Goomba(this, new Position(0, 19)));
        if(lvl == 1 || lvl == 2) {
            gameObjects.add(new Goomba(this, new Position(4, 6)));
            gameObjects.add(new Goomba(this, new Position(12, 6)));
            gameObjects.add(new Goomba(this, new Position(12, 8)));
            gameObjects.add(new Goomba(this, new Position(10, 10)));
            gameObjects.add(new Goomba(this, new Position(12, 11)));
            gameObjects.add(new Goomba(this, new Position(12, 14)));
        }
        if(lvl == 2) {
            gameObjects.add(new Box(this, new Position(9,4)));
            gameObjects.add(new Mushroom(this, new Position(12,8)));
            gameObjects.add(new Mushroom(this, new Position(2,20)));
        }

        gameObjects.add(new ExitDoor(this,new Position(Game.DIM_Y - 3, Game.DIM_X - 1)));
    }

    // ===== Métodos de reinicio del juego =====
    public void reset(int nLevel) {
        if (nLevel != -1) {
            int pointsAux = this.points, livesAux = this.lives;
            initLevel(nLevel = nLevel == -2 ? this.nLevel : nLevel);
            points = pointsAux; 
            lives = livesAux;
        } else initLevel(nLevel);
    }

    // ===== Actualización del juego =====
    public void update() {
        this.remainingTime--;
        if (!isFinished()) {
            gameObjects.update();
            if(!spawnObjects.isEmpty()) addSpawns();		
        }
    }

    private void addSpawns() {
        for (GameObject o : spawnObjects) {
            if (o != null) gameObjects.add(o);
        }
        spawnObjects.clear();
    }

    public void checkInteractions(GameObject obj) {
        gameObjects.doInteractions(obj);
    }

    // ===== Métodos de Mario =====
    public void marioExited() {
        this.points += this.remainingTime * 10;
        this.won = true;
        this.remainingTime = 0;
    }

    public void addAction(Action act) {
        if (mario != null) {
            mario.addAction(act);
        }
    }

    // ===== Gestión de vidas =====
    public void removeLife() {
        this.lives = this.lives - 1;
    }

    // ===== Métodos de colisiones y límites =====
    public boolean solidUnder(Position pos) {
        return gameObjects.isSolid(pos.under());
    }

    public boolean solidRight(Position pos) {
        return gameObjects.isSolid(pos.right());
    }

    public boolean solidLeft(Position pos) {
        return gameObjects.isSolid(pos.left());
    }

    public boolean solidUp(Position pos) {
        return gameObjects.isSolid(pos.up());
    }

    public boolean nextToRightLimit(Position pos) {
        return pos.nextToLimit(pos, DIM_X - 1);
    }

    public boolean nextToLeftLimit(Position pos) {
        return pos.nextToLimit(pos, 0);
    }

    public boolean fallenOut(Position pos) {
        return pos.fallenOut(pos, DIM_Y - 1);
    }

    public boolean isInBoard(Position pos) {
        return pos.isInBoard(DIM_X, DIM_Y);
    }

    // ===== Estado del juego =====
    public boolean isFinished() {
        return (playerWins() || playerLoses() || this.playerExits || this.remainingTime == 0);
    }

    public boolean playerWins() {
        return this.won;
    }

    public boolean playerLoses() {
        return lives == 0 || this.remainingTime == 0;
    }

    public void exit() {
        this.playerExits = true;
    }

    public int remainingTime() {
        return this.remainingTime;
    }

    public int points() {
        return this.points;
    }

    public void addPoints(int p) {
        this.points += p;
    }

    public int numLives() {
        return this.lives;
    }

    @Override
    public String toString() {
        return "Mario Bros 2.0";
    }

    // ===== Gestión de objetos del juego =====

    @Override
    public boolean addGameObject(String[] objectDescription, String Mode)
            throws OffBoardException, ObjectParseException , PositionParseException{

    	if(objectDescription.length > 4)
    		throw new ObjectParseException(String.format(Messages.OBJECT_PARSE_ERROR, String.join(" ", objectDescription)));
    	
        GameObject o = GameObjectFactory.parse(objectDescription, this);

        // Si GameObjectFactory.parse devuelve null, sería un fallo de diseño,
        // pero en nuestro siguiente paso haremos que lance excepciones.

        if (Mode.equalsIgnoreCase("spawn")) {
            spawnObjects.add(o);
        } else {
            gameObjects.add(o);
        }
        return true;
    }


    public String positionToString(int col, int row) {
        return gameObjects.positionToString(new Position(row,col));
    }

    // ===== Setters =====
    @Override
    public void setAsMainCharacter(Mario mario) {
        this.mario = mario;
    }

}
