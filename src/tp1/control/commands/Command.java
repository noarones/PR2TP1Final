/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;

public interface Command {

	public void execute(GameModel game, GameView view);
	
	public Command parse(String[] commandWords);
    
	public String helpText();
}
