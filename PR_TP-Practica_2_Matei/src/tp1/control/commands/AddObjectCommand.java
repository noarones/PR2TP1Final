package tp1.control.commands;

import java.util.Arrays;


import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class AddObjectCommand extends AbstractCommand {

	private static final String NAME = Messages.COMMAND_ADDOBJECT_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ADDOBJECT_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ADDOBJECT_DETAILS;
	private static final String HELP = Messages.COMMAND_ADDOBJECT_HELP;
	
	 private String[] objectDescription;
	
	public AddObjectCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		
	}

	private AddObjectCommand(String[] objectDescription) {
	        this();
	        this.objectDescription = objectDescription;
	    }
	 
	 @Override
	    public void execute(GameModel game, GameView view) {
	     
	        if (game.addGameObject(objectDescription, "command")) 
	        	view.showGame();
	        else 
	            view.showError(Messages.INVALID_GAME_OBJECT.formatted(String.join(" ", objectDescription)));
	        
	    }

	 @Override
	    public Command parse(String[] commandWords) {
	        if (matchCommandName(commandWords[0])) {
	            if (commandWords.length > 1) {
	                // Copiamos el resto de las palabras como descripción del objeto
	                String[] objDesc = Arrays.copyOfRange(commandWords, 1, commandWords.length);
	                return new AddObjectCommand(objDesc);
	            } else {
	                // Faltan argumentos
	                return null;
	            }
	        }
	        return null;
	    }
}
