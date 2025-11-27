package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.view.Messages;
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
}

