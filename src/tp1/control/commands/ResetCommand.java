package tp1.control.commands;
import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

//reset es un comando que puede aceptar parámetros, entonces hereda de AbstractCommand
//en vez de heredar de NoParamsCommand.
public class ResetCommand extends AbstractCommand{ 
	   //declaramos los atributos necesarios para llamar a la super constructora:
	
	   private static final String NAME = Messages.COMMAND_RESET_NAME;
	   private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
	   private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
	   private static final String HELP = Messages.COMMAND_RESET_HELP;
	   
	   private Integer nlevel; //Integer representa al tipo primitivo int como objeto y puede ser nulo.
	   
	   //constructoras
	   
	   public ResetCommand() { //sin parámetros para la lista de CommandGenerator y su ejecución en game.
		super(NAME, SHORTCUT, DETAILS, HELP);
	   }
	   public ResetCommand(int nlevel) { //con parámetro para su ejecución en game, para resetear un nivel en específico.
		   super(NAME, SHORTCUT, DETAILS, HELP);
		   this.nlevel = nlevel;
	   }
	   
	 //métodos reset  
		
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
		if(nlevel != null) {
			if(nlevel == 0 || nlevel == 1 || nlevel == -1 || nlevel == 2) {
				game.resetGame(nlevel);
				view.showGame();
			}
			else
				throw new CommandExecuteException(Messages.INVALID_LEVEL_NUMBER);
		}
		else {
			game.resetGame();
			view.showGame();
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0])) { //!
			if(commandWords.length == 1)
				return new ResetCommand();
			else if (commandWords.length == 2) {
				try {
					nlevel = Integer.parseInt(commandWords[1]);
					return new ResetCommand(nlevel);
					} catch(NumberFormatException e) {
						throw new CommandParseException(Messages.LEVEL_NOT_A_NUMBER_ERROR.formatted(commandWords[1]), e); //!
				}
			} else {
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER); //!
			}
		}
				
		return null;
	}

}

/*
 * EXPLICACIÓN DEL RESET:
 * Este comando puede recibir (o no) el número de nivel que se quiere resetear como
 * parámetro.
 * Por lo tanto tiene dos constructoras, una sin parámetros y otra con nLevel como
 * parámetro.
 * En consecuencia, GameModel tiene dos cabeceras de reset y Game implementa dos
 * métodos diferentes de reset: uno que actualiza el juego actual (sin parámetros),
 * y otro que actualiza el nivel que el usuario pide.
 * En el caso en el que reciba un número de nivel erróneo, (porque el nivel no existe),
 * entonces muestra el error de invalid level number.
 * 
 * IGUALMENTE RESET SE TERMINARÁ CAMBIANDO PORQUE EN LA SIGUIENTE PARTE SE VAN A PODER
 * CREAR NIVELES, ENTONCES HABRÁ QUE BUSCAR UNA FORMA PARA QUE RESET PUEDA RESETEAR
 * TODOS LOS NIVELES EXISTENTES SIN TENER QUE AÑADIR LA LÓGICA MANUALMENTE.
 * SEGURAMENTE IMPLEMENTAR UNA LISTA DE NIVELES Y CADA VEZ QUE SE CREE UNO, METERLO EN
 * LA LISTA (¿CON EL NÚMERO EN SÍ SERÍA SUFICIENTE?). LISTA DE NÚMEROS DE NIVEL DISPONIBLES.
 * */