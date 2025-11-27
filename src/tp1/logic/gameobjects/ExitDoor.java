package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;


public class ExitDoor extends GameObject{
	
	//CONSTRUCTORAS
	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos);
	}
	public ExitDoor() { //Constructora sin parámetros para la lista availableObjects.
		super(null, new Position(0,0));
	}
	
	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public void update() {}
	
	@Override
	public boolean interactWith(GameItem other) {
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
	}
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
		return Messages.OBJECT_EXITDOOR_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_EXITDOOR_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new ExitDoor(game, pos);
	}
}