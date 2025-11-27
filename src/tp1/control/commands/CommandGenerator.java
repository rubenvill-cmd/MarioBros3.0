package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.control.commands.exceptions.CommandParseException;
import tp1.view.Messages;

public class CommandGenerator {
	//lista de comandos declarados como instancias reconocidas por el programa.
	private static final List<Command> availableCommands = Arrays.asList(
			new AddObjectCommand(),
			new ActionCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()
	);
	
	//métodos
	public static Command parse(String[] commandWords) throws CommandParseException {//Controller llama a este método.
			
		for (Command c: availableCommands) {//por cada comando, llama a su método parse.
				Command parsedCommand = c.parse(commandWords);
				if(parsedCommand != null) {
					return parsedCommand;
				}
			}
			throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", commandWords))); //!
		}
	
	/*
	 * Si el nombre del comando iterado coincide con el introducido por el usuario, 
	 * el comando se devuelve a sí mismo. Si no se encuentra ninguno, se devuelve null.
	 */
	
	//método que escribe la ayuda de cada comando.
	public static String commandHelp() { 
		StringBuilder commands = new StringBuilder();
		
		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
		
		for (Command c: availableCommands) { 
			commands.append(c.helpText());//aquí se llama al helpText de cada comando.
		}
		
		return commands.toString();
	}

}
