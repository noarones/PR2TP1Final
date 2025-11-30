/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.logic.Game;
import tp1.view.GameView;

import tp1.exceptions.CommandException;


/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	
	private GameView view;

	public Controller(Game game, GameView view) {
		this.game = game;
		this.view = view;
	}


	/**
	 * Runs the game logic, coordinate Model(game) and View(view)
	 */
	public void run() {
	    view.showWelcome();
	    view.showGame();
	    
	    while (!game.isFinished()) {
	        String[] words = view.getPrompt();
	        try {
	            Command command = CommandGenerator.parse(words);
	            command.execute(game, view);
	        } catch (CommandException e) {
	            view.showError(e.getMessage());
	            Throwable cause = e.getCause();
	            while (cause != null) {
	                view.showError(cause.getMessage());
	                cause = cause.getCause();
	            }
	        }

	    }

	    view.showEndMessage();
	}

}
