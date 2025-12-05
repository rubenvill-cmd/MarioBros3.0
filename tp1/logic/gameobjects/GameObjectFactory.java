package tp1.logic.gameobjects;

import java.util.Arrays;
import tp1.view.Messages;
import java.util.List;

import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
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
	
	public static GameObject parse(String[] objWords, GameWorld game) throws GameParseException, OffBoardException{ //lanza las excepciones (no las modifica)
			for(GameObject go : availableObjects) { //recorre la lista de objetos
				GameObject parsedObject = go.parse(objWords, game); //parse del objeto de la lista de aviableObjects
				if(parsedObject != null) { //si coincide Y está bien escrito, se devuelve el objeto (el AddObjectCommand añadirá al game el objeto)
					return parsedObject;
				}
			}
		//si no ha returneado nada es porque el nombre del objeto no existe
		throw new ObjectParseException(Messages.UNKNOWN_OBJECT.formatted(String.join(" ", objWords)));
	}
}
