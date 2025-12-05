package tp1.logic;

import java.util.ArrayList;
import java.util.List;

public class ActionList {
	
	private Action horizontal; //distinguimos entre acciones horizontales (left y right)
	private Action vertical; //acciones verticales (Up y Down).
	private int contH;//Declaramos contadores para cada tipo de acciones incluido uno únicamente para action Stop.
	private int contV;
	private int contS;
	
	private List<Action> actList; //Y declaramos la lista de acciones.
	
	private static final int MAX_HORIZONTAL = 4;//Establecemos el máximo de acciones de cada tipo que podemos recibir y ejecutar.
	private static final int MAX_VERTICAL = 4;
	private static final int MAX_STOP = 1;
	private static final int MAX_ACTIONS = MAX_HORIZONTAL + MAX_VERTICAL + MAX_STOP;
	//En total serían 9: 4 ups o downs, 4 rights o lefts y un stop.
	
	
	//CONSTRUCTORA
	public ActionList(){
		actList = new ArrayList<>();
		horizontal = null;
		vertical = null;
		contH = 0;
		contV = 0;
	}
	
	
	//Métodos de la lista.
	public boolean isListEmpty() { //Devolvemos true si la lista está vacía.
		return (actList.size() == 0);
	}
	public Action getAction(int idx) { //Devolvemos la acción que se encuentra en la posición de la lista especificada.
	    if (idx >= 0 && idx < actList.size()) { //Comprobamos que la posición dada no accede a valores inválidos o inexistentes.
	        return actList.get(idx);
	    } else {
	        return null;
	    }
	}
	public int getSize() { //Devolvemos el tamaño de la lista de acciones
		return actList.size();
	}
	public void clearList() { 
		actList.clear(); //Limpiamos la lista.
		horizontal = null;
		vertical = null;
		contH = 0; //Reseteamos contadores
		contV = 0;
	}
	
	
	
	//ADD ACTION
	public void add(Action action) {
		//Comprobamos si existen acciones contradictorias y si el tamaño de la lista de acciones es menor a 9
		if (checkAction(action) && actList.size() < (MAX_ACTIONS)){	
			checkMaxMoves(action);
		}
	}
	
	//Antes de añadir una acción a la lista de acciones a ejecutar, vemos si cumple con los requisitos.
	private boolean checkAction(Action a){
		boolean ok = true; 
		if (a == Action.LEFT || a == Action.RIGHT) { 
			if (horizontal == null) { 
				horizontal = a; //Si no existe una acción horizontal todavía, asignamos la 1º acción horizontal
				//introducida a nuestro atributo horizontal (la guardamos).
			} 
			else if (horizontal != a) { //Si añadimos una acción horizontal contraria a la anterior, ejemplo:
				ok = false; //"a right left", como left != right, no la introducimos, devolvemos false.
			} 
		}
		if (a == Action.UP || a == Action.DOWN) { //Análogamente, con up y down se haría de la misma forma. 
			if(vertical == null) { 
				vertical = a; 
			} else if(vertical != a){ 
				ok = false; 
			}
		} 
		return ok;
	}
	
	private void checkMaxMoves(Action action) { //Al añadir acciones, siempre pasamos por esta función para incrementar
		//el contador del tipo de acción añadida y verificar que no nos excedemos en el número de acciones introducidas:
		//contX < MAX_X para acciones horizontales, verticales y stop.
		if ((action == Action.RIGHT || action == Action.LEFT) && contH < MAX_HORIZONTAL) { // contH < MAX_HORIZONTAL permite valores 0, 1, 2, 3 de una array
			actList.add(action); 
			contH++; 
		} 
		else if ((action == Action.UP || action == Action.DOWN) && contV < MAX_VERTICAL) { 
			actList.add(action); 
			contV++; 
		} else if (action == Action.STOP && contS <= MAX_STOP) { 
			actList.add(action); 
			contS++; 
		} //else: no se añade
		// si se añade se incrementa el size() automáticamente;
	}
}