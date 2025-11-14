package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand{
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	private int a;
	private boolean noArguments;
	public ResetCommand() {
		super(NAME, SHORTCUT , DETAILS, HELP);
	  noArguments = true;
	}

	@Override
	public void execute(GameModel game, GameView view) {
	if(a == 2 || a == 0 || a == 1 || a == -1 || a == -2 || noArguments)
		{
		game.reset(noArguments ? -2 : a);
	
		view.showGame(); }
	else { view.showError(Messages.INVALID_LEVEL_NUMBER + Messages.LINE_SEPARATOR); 
	}
	  
	
	}


	@Override
	public Command parse(String[] commandWords) {
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length > 1) {
			 a = Integer.parseInt(commandWords[1]);
		     noArguments = false;
			} 
			else noArguments = true;
			return this;
		}
		
		
		
		return null;
	}

}
