package tp1.control.commands;

import java.util.ArrayList;

import java.util.List;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ActionCommand extends AbstractCommand {
	private static final String NAME = Messages.COMMAND_ACTION_NAME;
	private static final String SHORTCUT = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_ACTION_DETAILS;
	private static final String HELP = Messages.COMMAND_ACTION_HELP;
	
	private List<Action> action = new ArrayList<>();
	
	public ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		
	}

	@Override
	public void execute(GameModel game, GameView view) {
		for (Action a: action) {
			game.addAction(a);
		}
		action.clear();
		
		game.update();
		view.showGame();
	}

	@Override
	public Command parse(String[] commandWords) {
	
		boolean ok = true;
		
		if(matchCommandName(commandWords[0]) &&commandWords.length > 1) {
		  for (int j = 1; j < commandWords.length && ok; j++) {
			  Action ret = Action.parseAction(commandWords[j].toLowerCase());
		       ok = ret != null;		  
			if(ok) action.add(ret);	  
		}
		  
		  if(!ok) action.clear();
		  
		 return this;
		  
		}
		
		return null;
	}

}
