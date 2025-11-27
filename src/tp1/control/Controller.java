package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.control.commands.exceptions.CommandException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class Controller {

	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}

	public void run() { //bucle principal del juego

		view.showWelcome(); //MarioBross 2.X

		view.showGame();
		
		while (!game.isFinished()) {
			
			String[] words = view.getPrompt();//guardamos el input del usuario.
			
			try {
				Command command = CommandGenerator.parse(words); //llamamos al método parse de cada comando y recibimos
				//if (command != null)
					command.execute(game, view); //En caso de que el comando se encuentre, llamamos a su método execute.
					//else 
						//view.showError(Messages.UNKNOWN_COMMAND.formatted(String.join(" ", words))); //Si no se encuentra, printeamos error.
				
			} catch (CommandException someException) { //Si se lanza una excepción de tipo Parse o Execute, la cogemos.
				view.showError(someException.getMessage()); //Imprimimos el error de la excepción.
				Throwable cause = someException.getCause(); //Intentamos ir más abajo para identificar la causa.
				//Aquí Throwable está por encima de Exception.
				while(cause != null) { //Si realmente existe una causa de la excepción, que sería otra excepción de nivel inferior, más abajo
					
					view.showError(cause.getMessage()); //Enseñamos el mensaje de error de esa otra excepción
					cause = cause.getCause(); //intentamos encontrarle a esta excepción una excepción aún más baja.
				}
			}
			//el comando que el usuario quiere o null en caso de no haberse encontrado

		}
		view.showEndMessage();
	}
	
}
