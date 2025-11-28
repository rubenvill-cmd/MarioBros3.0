package tp1.logic.gameobjects;

import java.util.Arrays;
import tp1.view.Messages;
import java.util.List;

import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameInterfaces.GameWorld;

public class GameObjectFactory {
	
	//lista de personajes disponibles
	private static final List<GameObject> availableObjects = Arrays.asList(
			new Mario(),
			new Goomba(),
			new Land(),
			new ExitDoor(),
			new Mushroom(),
			new Box()
			);
	
	public static GameObject parse(String[] objWords, GameWorld game) throws GameParseException, OffBoardException{
		for(GameObject go : availableObjects) { //recorre la lista de objetos
			GameObject parsedObject = go.parse(objWords, game); //parse del objeto de la lista de aviableObjects
			if(parsedObject != null) { //si coincide Y está bien escrito, se devuelve el objeto (el AddObjectCommand añadirá al game el objeto)
				return parsedObject;
			}
		}
		//SI LLEGA AQUI ES PQ EL NOMBRE DEL OBJETO NO EXISTE
		throw new GameParseException(Messages.UNKNOWN_OBJECT.formatted(objWords[1])); //UNKNOWN GAME OBJECT + OBJWORDS[1];
		//return null; //SI "DEVUELVE NULO" ES PQ ANTES HABÍA UN ERROR
	}
}
