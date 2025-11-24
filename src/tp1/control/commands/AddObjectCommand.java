/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.control.commands;

import java.util.Arrays;


import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.exceptions.ObjectParseException;

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
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
	    try {
	        game.addGameObject(objectDescription, "command");
	        view.showGame();
	    } catch (OffBoardException | ObjectParseException | PositionParseException e) {
	        // Envolvemos en CommandExecuteException
	        throw new CommandExecuteException(Messages.ERROR_COMMAND_EXECUTE, e);
	    }
	}


	@Override
	public Command parse(String[] commandWords) throws CommandParseException {

	    if (!matchCommandName(commandWords[0])) return null;
	    
	    if (commandWords.length <= 2) 
	        throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	    
	   
	 
	    
	    return new AddObjectCommand(Arrays.copyOfRange(commandWords, 1, commandWords.length));
	}

}
