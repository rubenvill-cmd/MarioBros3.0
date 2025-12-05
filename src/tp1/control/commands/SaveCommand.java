package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends AbstractCommand {
	
	private static final String NAME = Messages.COMMAND_SAVE_NAME;
	private static final String SHORTCUT = Messages.COMMAND_SAVE_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_SAVE_DETAILS;
	private static final String HELP = Messages.COMMAND_SAVE_HELP;
	
	private String fileName;
	   
	public SaveCommand() { //sin parámetros para la lista de CommandGenerator y su ejecución en game.
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	public SaveCommand(String fileName) { //con parámetro para su ejecución en game, para resetear un nivel en específico.
		   super(NAME, SHORTCUT, DETAILS, HELP);
		   this.fileName = fileName;
	}
	
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		try {
			game.save(fileName);
			view.showMessage(Messages.COMMAND_SAVE_CORRECT.formatted(fileName));
		}
		catch (GameModelException e) {
			throw new CommandExecuteException(Messages.COMMAND_SAVE_ERROR, e); //ESTE MENSAJE ME LO HE INVENTADO
		}
		//view.showGame();
	}
	
	@Override
	public Command parse(String[] objWords) throws CommandParseException {
		if(matchCommandName(objWords[0])) {
			if(objWords.length == 2) {
				fileName = objWords[1];
				return this;
			}
			else throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER); //NO SE SI PUEDE LANZAR MÁS EXCEPCIONES
		}
				
		return null;
	}
}
