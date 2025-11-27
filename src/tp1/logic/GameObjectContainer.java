package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {
	private List<GameObject> objects; //Lista que contiene todos los objetos del juego, comienza incluyendo los objetos iniciales.
	private List<GameObject> newObjects;//Contiene los objetos que se añaden durante el update. Por ejemplo, el Mushroom
	//cuando sale del box. Es una lista auxiliar.(No podemos añadir estos objetos durante el update a la lista
	//objects) porque cambiamos el tamaño de la lista inesperadamente.

	//CONSTRUCTORA
	public GameObjectContainer() {
		objects = new ArrayList<GameObject>();
		newObjects = new ArrayList<GameObject>();
	}
	
	//MÉTODOS
	//Añadimos un objeto a la lista de objetos principal.
	public void add(GameObject object) {
		this.objects.add(object);
	}
	
	//Añadimos un objeto a la lista auxiliar de objetos pendientes de añadir
	public void toAdd(GameObject gameobject) {
		this.newObjects.add(gameobject);
	}
	
	
	//UPDATE
	public void update() {
		//1. Actualizar todos los objetos existentes
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
		}
		
		//2. Procesar interacciones entre objetos.
		for(GameObject obj: objects) {
			doInteraction(obj);
		}
		
		//3. Añadir nuevos objetos que se añaden durante el update
		for(GameObject obj : newObjects) {
		    this.objects.add(obj);
		}
		newObjects.clear(); //4. limpiar la lista temporal.
		
		//5. Eliminar a los objetos muertos.
		clean();
	}
	
	//Limpiamos los objetos muertos de la lista
	private void clean() {
		for(int i = 0; i < objects.size(); i++) {
			if (!(objects.get(i).isAlive())) {
				objects.remove(i);
				i--; //Al hacerse un resize automático de la lista, tenemos que actualizar el índice para no saltarnos nada.
			}
		}
	}
	//Quitamos un objeto específico de la lista principal.
	public void remove(GameObject object) {
		for(int i = 0; i < objects.size(); i++) {
			if (objects.get(i).equals(object)) {
				objects.remove(i);
				i--; //como se hace un resize() automaticamente y los elementos se desplazan, hay que volver a chequear la pos i
			}
		}
	}
	
	public void doInteraction(GameItem other) {
		//1. Recorre TODOS los objetos del juego.
		for (GameObject obj: objects) {
			//2. Solo si están vivos
			if (obj.isAlive() && other.isAlive()) {
				//3. Intenta que interactúen entre sí.
				obj.doInteraction(other);//En gameObject.
			}
		}
	}
	
	
	//POSICIONES
	
	//verificamos si el objeto en la posición especificada es sólido o no
	public boolean isPosSolid(Position pos) {
		for(GameObject obj : objects) { //si el obj es mario, se chequea la posición de arriba y la de abajo con su propio marioIsInPos
			if (obj.isInPos(pos) && obj.isSolid()) { //le preguntamos al objeto si es sólido.
				return true;
			}
		}
		return false;
	}
	
	//imprimimos el icono del objeto en su posición.
	public String positionToString(Position pos) {
		StringBuilder posToStr = new StringBuilder();
		for (GameObject obj : objects) {
			if (obj != null && obj.isInPos(pos)) {
				posToStr.append(obj.getIcon());
			}
		}
		
		return posToStr.toString();
	}
}
