/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.CommandExecuteException;

public class ResetCommand extends AbstractCommand{
	private static final String NAME = Messages.COMMAND_RESET_NAME;
	private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	private static final String HELP = Messages.COMMAND_RESET_HELP;
	private int a;
	private boolean noArguments;
	
	public ResetCommand() {
		super(NAME, SHORTCUT , DETAILS, HELP);
		this.noArguments = true;
	}

	public ResetCommand(int nLevel, boolean noArguments) {
		super(NAME, SHORTCUT , DETAILS, HELP);
		this.noArguments = noArguments;
		this.a = nLevel;
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException{
		
     if(!((a >= -2 && a <=2 || noArguments) && reset(game,view)))
     
	 view.showError(Messages.INVALID_LEVEL_NUMBER + Messages.LINE_SEPARATOR); 
	
	}
	
   private boolean reset(GameModel game, GameView view) {
	   
	   game.reset(noArguments ? -2 : a);
	   view.showGame(); 
	 	
	   return true;
   }

   @Override
   public Command parse(String[] commandWords) throws CommandParseException {

       if (!matchCommandName(commandWords[0])) return null; 
       
       if (commandWords.length > 2) 
           throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
       

       int level = -2; // tu valor especial por defecto

       if (commandWords.length == 2) {
           try {
               level = Integer.parseInt(commandWords[1]);
           } catch (NumberFormatException nfe) {
               throw new CommandParseException(
                       Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]),
                       nfe
               );
           }
       }

       return new ResetCommand(level, false);
   }


}
