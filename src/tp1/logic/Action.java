package tp1.logic;

import tp1.view.Messages;
import tp1.exceptions.ActionParseException;

public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0); //Enumerado de todas las acciones del juego
	//que utilizamos en los objetos del game para moverlos con movePos.
	
	private final int x;
	private final int y;
	
	//Constructora de la clase
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	
	public int getX() {//cogemos coordenada x de la acción en específico.
		return x;
	}
    public int getY() {//cogemos coordenada y de la acción en específico.
		return y;
	}
	
    /*
	public boolean equals(Action a) {//NUNCA utilizamos este método que compara acciones.
		return ((x == a.getX())&&(y == a.getY()));
	}
	*/
	@Override
	public String toString() {//método Override de toString para imprimir mensaje de debug sobre qué acción tiene
		//cada objeto en cada update.
        switch(this) {
            case LEFT: return "LEFT";
            case RIGHT: return "RIGHT";
            case UP: return "UP";
            case DOWN: return "DOWN";
            case STOP: return "STOP";
            default: return "";
        }
    }
	
	public static Action parseAction(String[] objDescription) throws ActionParseException {//Se llama a este método desde el parse de MovingObject.
		String strAction = objDescription[2].toUpperCase();//Coge la acción introducida por el usuario y la convierte a mayúsuclas
		Action action = strToAction(strAction);//Hacemos uso de una función auxiliar para obtener la acción introducida de string a Action.
		if(action != null) {//Si se ha podido devolver una acción de tipo Action.
			return action; //devuelve la acción parseada
		}
		else throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(strAction)); //AHORA SI ES NULL LO GESTIONA EL MOVINGOBJECT
		//else return null; //si la acción no ha podido ser parseada, se devuelve null, indicador de hacer la act automática
	}
	
	private static Action strToAction(String action) {//Se compara la string toUpper de objDescription[2] con los
		//name y shortcuts de la clase Messages de las acciones pertinentes.
		if(action.equals(Messages.ACTION_LEFT) || action.equals(Messages.ACTION_LEFT_SHORTCUT)) {
			return Action.LEFT;
		}
		else if (action.equals(Messages.ACTION_RIGHT) || action.equals(Messages.ACTION_RIGHT_SHORTCUT)) {
			return Action.RIGHT;
		} 
		else if (action.equals(Messages.ACTION_STOP) || action.equals(Messages.ACTION_STOP_SHORTCUT)) {
			return Action.STOP;
		}
		return null; //returneamos null si no se ha podido convertir la "acción" introducida.
	}
	//Para crear un objeto tan solo se necesita la acción horizontal. Tan sólo manejamos esas acciones. (Up y down se queda fuera).
}