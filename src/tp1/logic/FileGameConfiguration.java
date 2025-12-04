package tp1.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameInterfaces.GameConfiguration;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;

public class FileGameConfiguration implements GameConfiguration{
	private int remainingTime;
	private int points;
	private int lives;
	private Mario mario;
	private List<GameObject> npcObjects;
	
	public FileGameConfiguration(String fileName, GameWorld game) throws GameLoadException{
		npcObjects = new ArrayList<>();
		
		try (BufferedReader in = new BufferedReader(new FileReader(fileName))){
			readGameState(in);
			readGameObjects(in, game);
		}
		catch (IOException e) {
			throw new GameLoadException(Messages.FILEGAMECONFIGURATION_NOT_FOUND.formatted(fileName), e);
		} 
		catch (NumberFormatException e) {
			throw new GameLoadException(Messages.INVALID_POS, e);
		}
		catch (ObjectParseException e) {
			throw new GameLoadException(Messages.FILEGAMECONFIGURATION_INVALID_FILE_CONFIG.formatted(fileName)/*+ e.getMessage()*/, e);
		}
		catch (OffBoardException e) {
			throw new GameLoadException(Messages.FILEGAMECONFIGURATION_INVALID_FILE_CONFIG.formatted(fileName)/*+ e.getMessage()*/, e);
		}
		/*
		catch (Exception e) {
			throw new GameLoadException("Error inesperado al cargar el juego", e);
		} // SI NO ES NINGUNA ES PQ ES UNA GAMELOADEXCEPTION Y LA LANZA DIRECTAMENTE
		*/
	}
	
	private void readGameState(BufferedReader in) throws GameLoadException, IOException, NumberFormatException{
		String firstLine = in.readLine();
		if (firstLine == null /*|| gameState.trim().isEmpty()*/) {
			throw new GameLoadException("Empty file"); //EL ARCHIVO ESTÁ VACÍO
		}
		
		//pasar a string el formato
		//LO DEL trim().split("\\s+"); LO HE SACADO DED CONSOLE VIEW EN EL MÉTODO DE GETPROMPT();
		String[] gameState = firstLine.trim().split("\\s+"); //crea un array de string, donded cada elemento es un dato (quitando los espacios)
		if (gameState.length != 3) {
			throw new GameLoadException(Messages.FILEGAMECONFIGURATION_INCORRECT_GAME_STATUS.formatted(String.join(" ", gameState))); //FORMATO DE ARCHIVO INCORRECTO
		}
		
		//inicializar atributos
		//try {
			this.remainingTime = Integer.parseInt(gameState[0]);
			this.points = Integer.parseInt(gameState[1]); //todas estaban a 0
			this.lives = Integer.parseInt(gameState[2]);
		//}
		//catch (NumberFormatException e) {throw new GameLoadException ("", e);} //ERROR PQ NO SON NUMEROS
	}
	
	
	private void readGameObjects(BufferedReader in, GameWorld game) throws GameLoadException, IOException, ObjectParseException, OffBoardException{
		String[] words = null;
		//try {
			String line = in.readLine();
			while (line != null) {
				words = line.trim().split("\\s+");
				//parsear a mario primero por si acaso
				Mario tempMario = new Mario();
				Mario parsedMario = tempMario.parse(words, game);
				
				if (parsedMario != null) { //entonces es un Mario
					//HACEMOS QUE SI SE PARSEAN A MUCHOS MARIOS DA IGUAL PQ NOS QUEDAMOS CON EL ULTIMO
					this.mario = parsedMario;
				}
				else {
					//parsear los otros objetos
					GameObject obj = GameObjectFactory.parse(words, game);
					if (obj != null) {
						this.npcObjects.add(obj);
					}
					//SI DA NULL EN EL FACTORY, ENTONCES LANZA UN ERROR DE OBJECTPARSE O OFFBOARD
					/*else { //CREO Q NO TENGO Q PONER ESTE ELSE
						throw new GameLoadException(); //OBJETO NO RECONODIDO (O ESO SE HACE EN EL OBJECT PARSE EXCEPTION)???
					}*/
				}
				line = in.readLine();
			}
		//}
		//catch (GameParseException e) { throw new ObjectParseException(e.getMessage());}	
	}
	
	@Override
	public int getRemainingTime() {
		return remainingTime;
	}

	@Override
	public int points() {
		return points;
	}

	@Override
	public int numLives() {
		return lives;
	}

	@Override
	public Mario getMario() {
		return mario;
	}

	@Override
	public List<GameObject> getNPCObjects() {
		return npcObjects;
	}
	
}