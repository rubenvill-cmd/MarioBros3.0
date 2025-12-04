package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.exceptions.ActionParseException;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;

public abstract class MovingObject extends GameObject {
	protected Action act;
	protected boolean wasFalling;
	
	//CONSTRUCTORA
	public MovingObject(GameWorld game, Position pos) {
		super(game, pos);
		this.wasFalling = false;
	}
	
	protected MovingObject(MovingObject other) {
		super(other);
		this.act = other.act;
		this.wasFalling = other.wasFalling;
	}
	
	protected abstract Action defaultAct();
	
	
	//UPDATE (MOVIMIENTO AUTOMÁTICO COMÚN)
	@Override
	public void update() {
		if (isSidePosSolid(Action.DOWN)) {//si debajo hay suelo
			if (act == Action.LEFT) { //se está moviendo a la izquierda
				doLeft();
			}
			else if (act == Action.RIGHT) { //se está moviendo a la derecha
				doRight();
			}
		}
		else { //no hay solido debajo
			doFall();
		}
	}
	
	private void doLeft() {
		if (isSidePosSolid(Action.LEFT) || posExitsBoard(Action.LEFT)){
			act = Action.RIGHT; // entonces vamos al lado contrario
		}
		else { //no se choca con nada
			move(Action.LEFT);
		}
	}
	private void doRight() {
		if (isSidePosSolid(Action.RIGHT)||posExitsBoard(Action.RIGHT)){
			act = Action.LEFT; // entonces vamos al lado contrario
		}
		else { //no se choca con nada
			move(Action.RIGHT);
		}
	}
	private void doFall() { //cae una posición
		move(Action.DOWN);
		wasFalling = true;
		if(posExitsBoard(Action.STOP))	{ //si el personaje se sale del tablero muere
			dead();
		}
	}
	
	
	
	//PARSE DEL OBJETO
	
	@Override
	public GameObject parse(String[] objectDescription, GameWorld game) throws GameParseException, OffBoardException{
		GameObject go = super.parse(objectDescription, game); //parsea la posición y el nombre //PUEDE DAR OBJECTPARSE EXC Y POSITIONPARSEEXCEPTION
		if (go!= null) {
			MovingObject obj = (MovingObject) go; //si el objeto no es nulo, es un MovingObject
			//parse de la acción
			try {
				//POS, NOMBRE ACCION
				if(objectDescription.length > 2){ //la descripción contiene acción
					Action a = Action.parseAction(objectDescription); //SI EL OBJETO DA NULL, VA A SALTAR EXCEPCION, Y SI NO HAY OTRA LETRA, SE PONE LA ACT AUTOMATICA
					//if (a != null) {
						obj.act = a;
						//}
						return obj; //devuelve el objeto parseado (posición, nombre, acción)
				}
				else {obj.act = defaultAct();}
				return obj; //devuelve el objeto parseado (posición, nombre, accion por defecto)
			}
			catch (ActionParseException aExc) {
				throw new GameParseException(Messages.UNKNOWN_DIR.formatted(String.join(" ", objectDescription)), aExc);
			}
		}
		//NO CATCHEAMOS EL OFFBOARDEXCEPTION NI EL POSITIONEXCEPTION PQ CREO QUE NO HACEMOS NADA CON ELLA, SOLO LA LANZAMOS
		//TENEMOS QUE HACER TAMBIEN LA DE OBJECTPARSEEXCEPTION????
		
		return null; //NO SE SI HAY QUE PONER EL RETURN NULL (creo que si pq si es null es pq no se ha matcheado el Name)
	}
	
	@Override
	public String toString() {
		String posName = super.toString() + " ";
		String ac = act.toString();
		return posName + ac;
	}
}