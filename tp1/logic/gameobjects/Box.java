package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;
import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;

public class Box extends GameObject{
	private boolean full; //true si esta llena
	private final static int POINTS_BOX = 50;
	
	//CONSTRUCTORAS
	public Box(GameWorld game, Position pos) {
		super(game, pos);
		full = defaultState();
	}
	public Box() { //Constructora sin parámetros para la lista availableObjects.
		super(null, new Position(0,0));
	}
	public Box(Box other) {
		super(other);
		this.full = other.full;
	}
	
	private boolean defaultState() {
		return true;
	}
	
	@Override
	public GameObject copy() {
		return new Box(this);
	}
	
	@Override
	public String getIcon() {
		if (full) return Messages.BOX;
		else return Messages.EMPTY_BOX;
	}
	
	protected boolean isFull() {
		return full;
	}
	protected void empty() {
		full = false;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void update() {}
	
	
	//INTERACIONES
	@Override
	public boolean interactWith(GameItem other) {
		if (otherInRelativePos(other, Action.DOWN) && full) {
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
		return false;
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		if (full) {
			game.incrPoints(POINTS_BOX);
			game.addNewObject(new Mushroom(game, relativePos(Action.UP)));
			full = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	};
	
	@Override
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}
	
	@Override
	public boolean receiveInteraction(Box obj) {
		return false;
	}
	
	
	//PARSE
	//métodos para matchObjectGame de cada objeto del juego y parse
	@Override
	protected String getName() {
		return Messages.OBJECT_BOX_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_BOX_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Box(game, pos);
	}
	
	
	//AYUDA CON EL PARSE DEL BOX PORFA :(
	@Override
	public GameObject parse(String[] objectDescription, GameWorld game) throws GameParseException, OffBoardException{
		GameObject go = super.parse(objectDescription, game); //parsea la posición y el nombre //PUEDE DAR OBJECTPARSE EXC Y POSITIONPARSEEXCEPTION
		if (go!= null) {
			Box obj = (Box) go;
			
			if(objectDescription.length == 3){ //la descripción contiene acción
				boolean f = parseBoxState(objectDescription); //si da null es porque no existe ese estado, y lanza la excepción
				obj.full = f;
				return obj; //devuelve el objeto parseado (posición, nombre, acción)
			}
			else if (objectDescription.length == 2){ //se pone el estado por defecto
				obj.full = defaultState();
				return obj;
			}
			else throw new GameParseException(Messages.COMMAND_ADDOBJECT_ERROR.formatted(String.join(" ", objectDescription))); //tiene muchos argumentos
			}
		
		return null;
	}
	
	private boolean parseBoxState(String[] objectDescription) throws GameParseException{
		String strState = objectDescription[2].toUpperCase();
		if (strState.equals(Messages.STATE_BOX_EMPTY) || strState.equals(Messages.STATE_BOX_EMPTY_SHORTCUT)) {
			return false;
		}
		else if (strState.equals(Messages.STATE_BOX_FULL) || strState.equals(Messages.STATE_BOX_FULL_SHORTCUT)) {
			return true;
		}
		else throw new GameParseException(Messages.INVALID_BOX_STATUS.formatted(String.join(" ", objectDescription)));
	}
	
	
	
	
	@Override
	public String toString() {
		String posName = super.toString() + " ";
		String st;
		if (full) st = Messages.STATE_BOX_FULL;
		else st = Messages.STATE_BOX_EMPTY;
		return posName + st;
	}
}
