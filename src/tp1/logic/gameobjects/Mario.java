package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.view.Messages;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;

public class Mario extends MovingObject{
	private boolean big;
	private Position bigPos;
	private ActionList actList;
	private boolean isRising;
	
	
	//CONSTRUCTORA
	public Mario(GameWorld game, Position pos) {
		super(game, pos);
		act = defaultAct();
		actList = new ActionList(); 
		big = true;
		isRising = false;
		posIfBig(); //aqui te crea la posición grande de mario
	}
	
	public Mario() {
		super(null, new Position (0,0)); //variable anónima para la constructora super
	}
	
	
	@Override
	protected Action defaultAct() {
		return Action.RIGHT;
	}
	
	@Override	
	public String getIcon(){
		if (!super.isAlive()) return Messages.EMPTY;
		else {
			if (act == Action.RIGHT) {
				return Messages.MARIO_RIGHT;
			}
			else if (act == Action.LEFT){
				return Messages.MARIO_LEFT;
			}
			else { //act == Action.STOP
				return Messages.MARIO_STOP;
			}
		}
	}
	
	@Override 
	public boolean isSolid() { 
		return false;
	}
	
	public void addAction(Action act) {
		actList.add(act);
	}
	
	private void posIfBig() { //marca la bigPos como una por encima de la pos normal
		if (big) {
			bigPos = relativePos(Action.UP);
		}
		else bigPos = null;
	}
	
	private void becomeBig() {this.big = true; this.posIfBig();}

		//POSICIONES
	@Override
	public boolean isInPos(Position p) { //se sobrescribe porque mario tiene dos posiciones
		posIfBig(); //si mario es grande marca la bigPos para chequearla
		return super.isInPos(p) || (this.big && this.bigPos.equals(p)); //true si mario está en la posición (la big o la normal)
	}
	@Override 
	protected boolean isSidePosSolid(Action dir) {
		if (big) {
			return ((game.isPosSolid(bigPos.movePos(dir)) || super.isSidePosSolid(dir)));
		}
		else{
			return super.isSidePosSolid(dir);
		}
	}
	@Override
	protected boolean posExitsBoard(Action dir) {
		if (big) { //lo mismo que con el isSidePosSolid
			return (bigPos.movePos(dir).exitsBoard() || super.posExitsBoard(dir));
		}
		else {
			return super.posExitsBoard(dir);
		}
	}
	
	
		//MOVIMIENTO
	@Override
	protected void move(Action dir) {
		if (big) {
			bigPos = relativePos(Action.UP);		
		}
		super.move(dir);
	}
	
	
	@Override
	public void update() {
		if(actList.isListEmpty()) { //si la lista esta vacía aplica las acciones automáticas
			super.update();
			posIfBig(); //se actualiza la posición grande de mario (pq el update() de MovingObject no la tiene en cuenta)
			if (!isAlive()) game.marioDies();
			game.interact(this);
		}
		else { //si no está vacía, lee la lista de acciones y las ejecuta
			for(int i = 0; i < actList.getSize(); i++) {
				executeAction(actList.getAction(i));
				posIfBig();
				game.interact(this);
			}
			actList.clearList(); //se deja la lista vacía para el siguiente ciclo de juego
		}
		wasFalling = false; //ponemos que el wasFalling en falso cuando ejecute una nueva accion, solo está en true cuando está callendo
		isRising = false; //lo mismo con rising
	}
	
	//ACCIONES MANUALES
	private void executeAction (Action a) {
		switch(a) {
		case Action.RIGHT:
		case Action.LEFT:{
			horizontalAction(a);
			break;
		}
		case Action.UP:{
			upAction();
			break;
		}
		case Action.DOWN:{
			downAction();
			break;
		}
		default: { //Action.STOP
			stopAction();
			break;
		}
		}
	}
	
	private void horizontalAction(Action a) {
		if (isSidePosSolid(a) || posExitsBoard(a)) { //si choca con un solido o el tablero, no se mueve, solo cambia el act
			if (a == Action.RIGHT) {
				act = Action.LEFT;
			}
			else if (a == Action.LEFT) {
				act = Action.RIGHT;
			}
		} 
		else { //si no se choca ejecuta la accion
			act = a;
			move(a);
		}
	}
	private void upAction() {
		isRising = true;
		if (!isSidePosSolid(Action.UP) && !posExitsBoard(Action.UP)) {
			move(Action.UP);
		}
	}
	private void downAction() {
		while (!isSidePosSolid(Action.DOWN) && !posExitsBoard(Action.DOWN)) { //mientras que no haya solido sigue cayendo
			move(Action.DOWN);
			wasFalling = true; //true pq ha caido
			game.interact(this);
		}
		if (posExitsBoard(Action.DOWN)) { //si se sale del tablero mientras que cae
			dead();
			game.marioDies();
		}
		if (isSidePosSolid(Action.DOWN) && (!posExitsBoard(Action.DOWN))){ //si hay un solido debajo(y no ha muerto)
			if(!wasFalling) { //si NO estaba cayendo (había solido debajo)
				act = Action.STOP;
			} 
		}
	}
	private void stopAction() {
		act = Action.STOP;
	}

	
	
		//INTERACCIONES
	@Override
	public boolean interactWith(GameItem other) {
		if (otherInPos(other) || (this.big && other.isInPos(bigPos))) {
	        return other.receiveInteraction(this);
	    }
	    return false;
	}

	@Override
	public boolean receiveInteraction(Land obj) {
		return false;
	}

	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		game.marioExited();
		return true;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Goomba obj) {
		if (isAlive()) {
			if(wasFalling) {
				return true;
			}
			else { //mario no estaba cayendo
				if (big) { //si es grande, deja de serlo
					big = false;
					return true;
				}
				else { //si no, mario muere
					dead();
					game.marioDies();
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean receiveInteraction(Mushroom obj) {
		if(!big) {
			becomeBig(); //mario se hace grande
			return true;
		}
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Box obj) {
		if (isRising) { //si mario está ejecutando la acción up, se abre la box (llamamos a que box reciba la interacción de mario)
			return obj.receiveInteraction(this);
		}
		return false;
	}
	
	
	
	
	//PARSE
	@Override
	protected String getName() {
		return Messages.OBJECT_MARIO_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_MARIO_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Mario(game, pos);
	}
	
	
	@Override // ao (1,2) M R B
	public Mario parse(String[] objectDescription, GameWorld game) {
		GameObject go = super.parse(objectDescription, game); //parsea la posicion, el nombre y la acción
		if (go!= null) {
			Mario mario = (Mario)go; //si el objeto no es nulo, es un Mario
			
			//parse del estado
			if (objectDescription.length == 4) {
				String strState = objectDescription[3].toUpperCase();
				if(strState.equals(Messages.STATE_SMALL) || strState.equals(Messages.STATE_SMALL_SHORTCUT)) {
					mario.big = false;
				}
				//si no se especifica, se pone el valor por defecto
			}
			return mario;
		}
		return null;
	}
}
