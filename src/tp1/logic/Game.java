/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.logic;
import java.io.FileOutputStream;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import tp1.logic.gameobjects.*;
import tp1.view.Messages;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;

public class Game implements GameStatus,GameStatusWriter, GameWorld, GameModel{
    // ===== Configuración del juego =====
    private GameConfiguration conf = FileGameConfiguration.NONE;

    // ===== Constantes del juego =====
    public static final int DIM_X = 30;
    public static final int DIM_Y = 15;

    // ===== Estado interno del juego =====
    private int remainingTime;
    private boolean won = false;
    private int nLevel;
    private int points;
    private int lives;
    private InitialValues initValues = new InitialValues(100/*Time*/,0/*Points*/,3/*Lives*/);
    private GameObjectContainer gameObjects;
    private List<GameObject> spawnObjects = new ArrayList<>();
    private boolean playerExits = false;

    // ===== Constructor =====
    public Game(int nLevel) {
        initLevel(nLevel == 6 ? 1 : nLevel);
    }

    // ===== Métodos de inicialización de nivel =====
    private void initLevel(int lvl) {
        setInitialGameValues(lvl);
        
        if(lvl == -1) return;
        
        LevelFactory l = new LevelFactory(this);
        
        this.gameObjects = l.createLevel(lvl);

    }

    private void setInitialGameValues(int lvl) {
        this.nLevel = lvl;
        
        gameObjects = new GameObjectContainer();
        
        initValues.applyTo(this);
        
    }


    // ===== Métodos de reinicio del juego =====
    public void reset(int nLevel, boolean noArguments) {

        if (usingFile(noArguments)) handleConfigFile();
        
        else handleInternalMap(noArguments,nLevel);
            
    }


    
    private void handleInternalMap(boolean noArguments, int nLevel) {
    	
    	this.nLevel = noArguments ? this.nLevel : nLevel;
    	
    	int p = this.points, l = this.lives;
    	
    	initLevel(this.nLevel);
    	
        if (this.nLevel != -1)  
          initValues.applyTo(this,p,l);

    }

    private void handleConfigFile() {

        this.conf.getInitialValues().applyTo(this, points, lives);
      
        this.gameObjects = this.conf.getGameObjects();
    }

    
    
    // ===== Actualización del juego =====
    public void update() {
    	
        this.remainingTime--;
        
        if (updatePred());		
        
    }
   
    
    private boolean addSpawns() {
        
    	for (GameObject o : spawnObjects) 
            if (o != null) gameObjects.add(o);
        
        
        spawnObjects.clear();
        
        return true;
    }

    public boolean checkInteractions(GameObject obj) {
        gameObjects.doInteractions(obj);
    return true;
    }

    // ===== Métodos de Mario =====
    public void marioExited() {
        addPoints(remainingTime * 10);
        won = true;
        remainingTime = 0;
    }

    public void addAction(Action act) {
    	 if(gameObjects.marioExists())
            this.gameObjects.addActionToMario(act);

    }

    // ===== Gestión de vidas =====
    public void removeLife() {
        this.lives--;
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

    public void addPoints(int p) {
        this.points += p;
    }
    
    public int remainingTime() {
        return this.remainingTime;
    }

    public int points() {
        return this.points;
    }

    public int numLives() {
        return this.lives;
    }
    
    
    public void setRemainingTime(int time) {
       this.remainingTime = time;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setNumLives(int lives) {
       this.lives = lives;
    }
    
   
    @Override
    public String toString() {
        return Messages.GAME_NAME;
    }

    // ===== Gestión de objetos del juego =====

    @Override
    public boolean addGameObject(String[] objectDescription, String Mode) throws GameModelException {

        GameObject o = GameObjectFactory.parse(objectDescription, this);
    	
        if (Mode.equalsIgnoreCase("spawn")) 
            spawnObjects.add(o);
        else 
            gameObjects.add(o);
        
        return true;
    }

    
    

    public String positionToString(int col, int row) {
        return gameObjects.positionToString(new Position(row,col));
    }

    
    // ===== Gestión del guardado =====
    @Override
    public void save(String fileName) throws GameModelException {
        try(PrintWriter outChars = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            //Guardar primera línea: tiempo, puntos, vidas
        	  outChars.println(Integer.toString(this.remainingTime) + " " + Integer.toString(this.points) + " " + Integer.toString(this.lives));

        //Guardar 
            gameObjects.save(outChars);
        }
        catch (Exception e) {
            throw new GameModelException(Messages.ERROR_SAVING_GAME.formatted(fileName), e);
        }
    }

    // ===== Gestión del cargado =====
    public void load(String fileName) throws GameLoadException {
    	
        this.conf = new FileGameConfiguration(fileName, this);
        
        this.conf.getInitialValues().applyTo(this);
       
        this.gameObjects = this.conf.getGameObjects();
    

    }

    
    //Pred
    private boolean updatePred() {
    	return !isFinished() && gameObjects.update() && !spawnObjects.isEmpty() && addSpawns();
    }
    
    private boolean usingFile(boolean noArgs) {
    	return noArgs && this.conf != FileGameConfiguration.NONE;
    }

    


    
}
