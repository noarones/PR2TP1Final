package tp1.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {
	// Constante NONE que usa el constructor sin parámetros
	public static final GameConfiguration NONE = new FileGameConfiguration();

    private GameObjectContainer gameObjects;
    private int remainingTime;
    private int points;
    private int numLives;
    String line = "";

    public FileGameConfiguration() {
        this.remainingTime = 0;
        this.points = 0;
        this.numLives = 0;
        this.gameObjects = new GameObjectContainer();
    }

    public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException {
        this.gameObjects = new GameObjectContainer();
	    try (BufferedReader inChars = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
	        String l; 

	        // Lee la primera línea
	        l = inChars.readLine();
	        if (l != null) 
	            parseGameState(l);  
	             
	        

	        // Leemos las siguientes líneas
	        while ((l = inChars.readLine()) != null) {
	            try {
	                // Puedes asignar la línea leída a `line` si lo necesitas
	                this.line = l;
	                GameObject o = GameObjectFactory.parse(l,game);  
	                this.gameObjects.add(o);
	            } catch (GameModelException x) {
	                throw new GameLoadException (Messages.INVALID_FILE_CONF.formatted(fileName), x);  // En caso de un error durante el parseo
	            }
	        }

	    } catch (IOException e) {
	    	//Necesario para usuarios de Linux(Mensaje distinto de IOException utilizo Messages.static unico. 
	        throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName)
	        		, new IOException(Messages.FILE_NO_ENCONTRADO.formatted(fileName)));
	    }
    }

    // Método para analizar el estado del juego desde la primera línea
	private void parseGameState(String line) throws GameLoadException {
		String[] state = line.trim().split("\\s+");
		if (state.length != 3) {
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(line.toString()) , new ObjectParseException("null"));
		}
		try {
			this.remainingTime = Integer.parseInt(state[0]);
			this.points = Integer.parseInt(state[1]);
			this.numLives = Integer.parseInt(state[2]);
		} catch (NumberFormatException e) {
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(line.toString()), e);
		}
	}

    @Override
    public int getRemainingTime() {
        return this.remainingTime;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public int getNumLives() {
        return this.numLives;
    }

    @Override
    public GameObjectContainer getGameObjects() {
        return new GameObjectContainer(this.gameObjects);
    }
    
}
