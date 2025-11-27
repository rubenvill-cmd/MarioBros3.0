package tp1.control.commands;

import tp1.control.commands.exceptions.CommandExecuteException;
import tp1.control.commands.exceptions.CommandParseException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;

public interface Command { //métodos que todos los comandos deben de implementar

	public void execute(GameModel game, GameView view) throws CommandExecuteException;//cada comando debe de ejecutarse. 
	public Command parse(String[] commandWords) throws CommandParseException;//cada comando tiene que saber parsearse

	public String helpText();//a la hora de mostrar la ayuda de cada comando, cada comando
	//debe de devolver la suya.
}
