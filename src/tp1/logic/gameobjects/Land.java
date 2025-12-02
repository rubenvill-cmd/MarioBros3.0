package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;

public class Land extends GameObject{

	//CONSTRUCTORAS
	public Land(GameWorld game, Position pos) {
		super(game, pos);
	}
	public Land() { //Constructora sin parámetros para la lista availableObjects.
		super(null, new Position(0,0));
	}
	
	@Override
	public String getIcon() {
		return Messages.LAND;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void update() {}
	
	//INTERACCIONES
	
	@Override
	public boolean interactWith(GameItem other) {  //PODEMOS PONER QUE LAND SIEMPRE DEVUELVA FALSE EN RECIEVEINTERACTION DEL GAMEITEM
		if (otherInPos(other)) {
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
		return Messages.OBJECT_LAND_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_LAND_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Land(game, pos);
	}
	
	@Override
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		if (objDescription.length > 2) {
			throw new GameParseException(Messages.COMMAND_ADDOBJECT_ERROR.formatted(String.join(" ", objDescription)));
		}
		return super.parse(objDescription, game);
	}
}
