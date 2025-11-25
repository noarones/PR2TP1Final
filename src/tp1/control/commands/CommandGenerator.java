/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.control.commands;

import java.util.Arrays;
import java.util.List;


import tp1.view.Messages;
import tp1.exceptions.CommandParseException;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
			new LoadCommand(),
			new SaveCommand(),
			new AddObjectCommand(),
			new ActionCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()			
	);

	public static Command parse(String[] commandWords) throws CommandParseException{		
		for (Command c: availableCommands) {
			Command parsed = c.parse(commandWords);
			if(parsed != null)	
			return parsed;

		}
		  throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(commandWords[0]));
	}
		
	public static String commandHelp() {
	    StringBuilder commands = new StringBuilder();

	    commands.append(Messages.HELP_AVAILABLE_COMMANDS)
	            .append(Messages.LINE_SEPARATOR);

	    for (Command c : availableCommands) {
	        String help = c.helpText().trim(); // elimina saltos extra
	        commands.append(Messages.TAB)
	                .append(help)
	                .append(Messages.LINE_SEPARATOR);
	    }

	    return commands.toString();
	}


}
