package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;
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
	
	public static GameObject parse(String[] objWords, GameWorld game) {
		for(GameObject go : availableObjects) { //recorre la lista de objetos
			GameObject parsedObject = go.parse(objWords, game); //parse del objeto
			if(parsedObject != null) { //si coincide Y está bien escrito, se devuelve el objeto (el AddObjectCommand añadirá al game el objeto)
				return parsedObject;
			}
		}
		return null;
	}
}
