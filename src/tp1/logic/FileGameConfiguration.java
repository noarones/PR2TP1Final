/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
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
        try (BufferedReader inChars = 
        		new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
	  
	        //LEE INITIALVALUES Y GUARDA VALORES
	       readInitialValues(inChars);
	       
	       //LEE OBJECT VALUES RESTANTES
	       readObjectValues(inChars, game,fileName);
	        
	    } catch (IOException e) {
	    	//Mensaje distinto en Linux, por lo que se usa un mensaje unico de Messages 
	        throw new GameLoadException(Messages.FILE_NOT_FOUND.formatted(fileName)
	        		, new IOException(Messages.FILE_NO_ENCONTRADO.formatted(fileName)));
	    }
    }

    //Parsea primera linea
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
	
	
	private void readInitialValues(BufferedReader inChars) throws IOException, GameLoadException {
		 String l = inChars.readLine();
	       
	        if (l != null) 
	        	this.initialValues = parseGameState(l);  
	}
	
	private void readObjectValues(BufferedReader inChars, GameWorld game,String fileName) throws GameLoadException, IOException {
		
		   String l;
	        //LEE OBJETOS
	        while ((l = inChars.readLine()) != null) {  
	        		try {
	        			gameObjects.add(GameObjectFactory.parse(l.trim().split("\\s+"),game));
             			
	        			} catch (GameModelException x) {
	        				throw new GameLoadException (Messages.INVALID_FILE_CONF.formatted(fileName), x);  
	        				// En caso de un error durante el parseo
	        			}
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
