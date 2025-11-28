package tp1.logic.gameobjects;

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
}
