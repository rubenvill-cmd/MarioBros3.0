package tp1.control.commands;

import tp1.logic.GameInterfaces.GameModel;
import tp1.exceptions.CommandParseException;
import tp1.logic.Action;
import tp1.view.GameView;
import tp1.view.Messages;
import java.util.ArrayList;
import java.util.List;

public class ActionCommand extends AbstractCommand {
	//Atributos para llamar a la constructora de la super clase.
	private static final String name = Messages.COMMAND_ACTION_NAME;
	private static final String shortcut = Messages.COMMAND_ACTION_SHORTCUT;
	private static final String details = Messages.COMMAND_ACTION_DETAILS;
	private static final String help = Messages.COMMAND_ACTION_HELP;
	//añadimos la lista de acciones que tendrá las acciones válidas introducidas por el usuario.
	private List<Action> actions;
	//constructora:
	public ActionCommand () { //constructora sin lista para la lista de CommandGenerator.
		super(name, shortcut, details, help);
		this.actions = new ArrayList<>();
	}
	
	public ActionCommand (List<Action> actions) { //constructora con lista como parámetro
		super(name, shortcut, details, help);
		this.actions = actions;
	}
	
	//métodos implementados de Command:
	@Override
	public void execute(GameModel game, GameView view) {
		//1. Si la lista de acciones existe y no está vacía
		if(actions != null && !(actions.isEmpty())) {
			//2. Añadimos cada acción a la lista de acciones a través del Game.
			for(Action action : actions)
				game.addAction(action);//añadimos la acción a actionList a través de game.
		}
		game.update();
		view.showGame();
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException { 
		//1. El comando sabe verificarse a sí mismo
		if(matchCommandName(commandWords[0])) { //!
			if(commandWords.length > 1){ //!
				//2. Si realmente se ha introducido este comando, creamos una lista de acciones.
				List<Action> parsedActions = new ArrayList<>();
				boolean hasValidActions = false;//!
				//3. Leemos todas las acciones introducidas por el usuario.
		        for (int i = 1; i < commandWords.length; i++) {
		        	//4. Llamamos al método auxiliar "parseAction" para poder añadir acciones de tipo action a la lista.
		        	String upperAction = commandWords[i].toUpperCase();
		            Action action = Action.strToAction(upperAction);
		            if (action != null) {
		            	//5. Si hemos conseguido parsear la acción la añadimos a la lista
		                parsedActions.add(action);
		                hasValidActions = true; //!
		            }
		        }
		        if(!hasValidActions) {
		        	throw new CommandParseException(Messages.COMMAND_ACTION_INCORRECT); //!
		        }
		        //6. Devolvemos el comando a ejecutar con su lista de acciones
		        return new ActionCommand(parsedActions);
			} else {
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);//!
			}
		}
		return null;
	}/*
	//método adicional para devolver Strings como acciones de todas las acciones del juego.
	private Action parseAction (String actionStr) {
		
		String upperAction = actionStr.toUpperCase();
		switch(upperAction) {
		case(Messages.ACTION_LEFT_SHORTCUT):
		case(Messages.ACTION_LEFT):
			return Action.LEFT;
		case(Messages.ACTION_RIGHT_SHORTCUT):
		case(Messages.ACTION_RIGHT):
			return Action.RIGHT;
		case(Messages.ACTION_DOWN_SHORTCUT):
		case(Messages.ACTION_DOWN):
			return Action.DOWN;
		case(Messages.ACTION_STOP_SHORTCUT):
		case(Messages.ACTION_STOP):
			return Action.STOP;
		case(Messages.ACTION_UP_SHORTCUT):
		case(Messages.ACTION_UP):
			return Action.UP;
		default:
			return null;
		}
		
	}*/
}