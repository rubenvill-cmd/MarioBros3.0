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
		GameObject go = super.parse(objectDescription, game); //parsea la posición y el nombre -> puede dar GameParseException y OffBoardException
		if (go!= null) {
			MovingObject obj = (MovingObject) go; //si el objeto no es nulo, es un MovingObject
			
			//parse de la acción
			try {
				if(objectDescription.length > 2){ //la descripción contiene acción
					Action a = Action.parseAction(objectDescription); //si a da null, salta excepción (dentro de Action)
						obj.act = a;
						if (!isValidAction(a)) {
							throw new GameParseException(Messages.INVALID_ACT.formatted(String.join(" ", objectDescription)));
						}
						return obj; //devuelve el objeto parseado (posición, nombre, acción)
				}
				//si solo hay 2 elementos se pone la acción por defecto
				else {obj.act = defaultAct();}
				return obj; //devuelve el objeto parseado (posición, nombre, accion por defecto)
			}
			catch (ActionParseException aExc) {
				throw new GameParseException(Messages.UNKNOWN_OBJ_DIR.formatted(String.join(" ", objectDescription)), aExc);
			}
		}
		
		return null; //si devuelve null es porque no ha matchName (por tanto no es este objeto el que hay que parsear)
	}
	private boolean isValidAction(Action a) {
		return a == Action.RIGHT || a == Action.LEFT || a == Action.STOP ;
	}
	
	
	@Override
	public String toString() {
		String posName = super.toString() + " ";
		String ac = act.toString();
		return posName + ac;
	}
}