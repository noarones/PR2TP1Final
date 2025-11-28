package tp1.logic;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration {
	// Constante NONE que usa el constructor sin parámetros
	public static final GameConfiguration NONE = new FileGameConfiguration();

    private GameObjectContainer gameObjects;

    private InitialValues initialValues;
    
    public FileGameConfiguration() {

        this.gameObjects = new GameObjectContainer();
    }

    public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException {
    	//Contenedor inicial
        this.gameObjects = new GameObjectContainer();
        
	   //Handle Exceptions  
        try (BufferedReader inChars = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
	  

	        // Lee la primera línea
	       String l = inChars.readLine();
	       
	        if (l != null) 
	        	this.initialValues = parseGameState(l);  
	             
	        // Leemos las siguientes líneas
	        while ((l = inChars.readLine()) != null) {
	            try {
	            	
              gameObjects.add(GameObjectFactory.parse(l.trim().split("\\s+"),game));
              			
	            } catch (GameModelException x) {
	                throw new GameLoadException (Messages.INVALID_FILE_CONF.formatted(fileName), x);  // En caso de un error durante el parseo
	            }
	        }
	       
	        //Al terminar de anadir objetos se guarda una copia en la clase FileGameConfiguration (es el estado Inicial de donde se partira)
       
	        
	    } catch (IOException e) {
	    	//Necesario para usuarios de Linux(Mensaje distinto de IOException utilizo Messages.static unico. 
	        throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName)
	        		, new IOException(Messages.FILE_NO_ENCONTRADO.formatted(fileName)));
	    }
    }

    // Método para analizar el estado del juego desde la primera línea
	private InitialValues parseGameState(String line) throws GameLoadException {
		String[] state = line.trim().split("\\s+");
		
		if (state.length != 3) 
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(line.toString()));
		
		try {
			
			return new InitialValues(Integer.parseInt(state[0]),
					                 Integer.parseInt(state[1]), 
					                 Integer.parseInt(state[2]));

		} catch (NumberFormatException e) {
			throw new GameLoadException(Messages.INCORRECT_GAME_STATUS.formatted(line.toString()), e);
		}
	}
	
	public InitialValues getInitialValues() {
	   return this.initialValues; 
	}

    @Override
    public GameObjectContainer getGameObjects() {
        return new GameObjectContainer(this.gameObjects);
    }
    

}
