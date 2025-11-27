package tp1.logic.gameobjects;
import tp1.logic.Action;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;

public abstract class MovingObject extends GameObject {
	protected Action act;
	protected boolean wasFalling;
	
	//CONSTRUCTORA
	public MovingObject(GameWorld game, Position pos) {
		super(game, pos);
		this.wasFalling = false;
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
	public GameObject parse(String[] objectDescription, GameWorld game) {
		GameObject go = super.parse(objectDescription, game); //parsea la posición y el nombre
		if (go!= null) {
			MovingObject obj = (MovingObject) go; //si el objeto no es nulo, es un MovingObject
			
			//parse de la acción
			if(objectDescription.length > 2){ //la descripción contiene acción
				Action a = Action.parseAction(objectDescription);
				if (a != null) {
					obj.act = a;
				}
				return obj; //devuelve el objeto parseado (posición, nombre, acción)
			}
			obj.act = defaultAct();
			return obj; //devuelve el objeto parseado (posición, nombre, accion por defecto)
		}
		return null;
	}
}
