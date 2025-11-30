/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.control.commands;

import java.util.ArrayList;

import java.util.List;

import tp1.logic.Action;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.CommandExecuteException;

public class ActionCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_ACTION_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
	private static final String HELP = Messages.COMMAND_ACTION_HELP;
	
	private List<Action> action = new ArrayList<>();
	
	public ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		
	}

	public ActionCommand(List<Action> actionList) {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.action = actionList;
		
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		
		for (Action a: action) game.addAction(a);
		
		action.clear();
		
		game.update();
		view.showGame();
	}

	private boolean commandValido(String[] commandWords)  {
		return isValidParamCommand(commandWords) && prepararLista(commandWords);
	}
	
	
	private boolean prepararLista(String[] commandWords) {
		
		action.clear();
		Action ret = Action.STOP;
		
		for (int j = 1; j < commandWords.length && ret != null; j++) {
			
			ret = Action.parseAction(commandWords[j].toLowerCase());
	     
			if(ret != null) action.add(ret);
		
		}
		  
		return !action.isEmpty();
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {

	    if (!matchCommandName(commandWords[0]))  return null;
	    

	    // si no hay acciones -> error de parámetros
	    if (commandWords.length < 2) 
	        throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	   

	    if (!commandValido(commandWords)) 
	        throw new CommandParseException(Messages.INCORRECT_ACTION_COMMAND);
	    
	    

	    return new ActionCommand(action);
	}


}
