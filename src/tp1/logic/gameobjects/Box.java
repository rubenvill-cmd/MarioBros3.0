package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;
import tp1.exceptions.GameParseException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;

public class Box extends GameObject{
	private boolean full; //true si esta llena
	private final static int POINTS_BOX = 50;
	
	//CONSTRUCTORAS
	public Box(GameWorld game, Position pos) {
		super(game, pos);
		full = true;
	}
	public Box() { //Constructora sin parámetros para la lista availableObjects.
		super(null, new Position(0,0));
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
			//parse deL estado
			//try {
				if(objectDescription.length > 2){ //la descripción contiene acción
					boolean f = parseBoxState(objectDescription); //SI EL OBJETO DA NULL, VA A SALTAR EXCEPCION, Y SI NO HAY OTRA LETRA, SE PONE LA ACT AUTOMATICA
					obj.full = f;
					return obj; //devuelve el objeto parseado (posición, nombre, acción)
				}
				else {obj.full = true;}
				return obj; //devuelve el objeto parseado (posición, nombre, accion por defecto)
			/*}
			catch (ObjectParseException e) { throw new GameParseException(Messages.INVALID_BOX_STATUS.formatted(objectDescription));
			}*/
			
			}
		
		return null; //NO SE SI HAY QUE PONER EL RETURN NULL (creo que si pq si es null es pq no se ha matcheado el Name)
	}
	
	private boolean parseBoxState(String[] objectDescription) throws GameParseException{
		String strState = objectDescription[2].toUpperCase();
		//ME HE INVENTADO LO DE LA CLASE BOOLEAN AYUDA
		Boolean f = strToState(strState);
		if (f != null) {return f;}
		else throw new GameParseException(Messages.INVALID_BOX_STATUS.formatted(objectDescription));
	}
	private Boolean strToState(String state) {
		if (state.equals(Messages.STATE_BOX_EMPTY) || state.equals(Messages.STATE_BOX_EMPTY_SHORTCUT)) {
			return false;
		}
		else if (state.equals(Messages.STATE_BOX_FULL) || state.equals(Messages.STATE_BOX_FULL_SHORTCUT)) {
			return true;
		}
		return null;
	}
	
}

