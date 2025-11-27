package tp1.logic.gameobjects;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.logic.Position;
import tp1.logic.Action;
import tp1.view.Messages;

public class Mushroom extends MovingObject {
	
	//CONSTRUCTORAS
	public Mushroom (GameWorld game, Position pos) {
		super(game, pos);
		act = defaultAct();
	}
	public Mushroom () {
		super(null, new Position(0,0));
		act = Action.RIGHT;
	}
	
	
	@Override
	protected Action defaultAct() {
		return Action.RIGHT;
	}
	
	@Override
	public String getIcon() {
		if (!super.isAlive()) {
			return Messages.EMPTY;
		}
		return Messages.MUSHROOM;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	
	
	// INTERACCIONES
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
		this.dead();
		return true;
	}
	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false;
	}
	
	public boolean receiveInteraction(Mushroom obj) {
		return false;
	}
	
	public boolean receiveInteraction(Box obj) {
		return false;
	}

	
	
	//PARSE DEL OBJETO
	@Override
	protected String getName() {
		return Messages.OBJECT_MUSHROOM_NAME;
	}
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_MUSHROOM_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Mushroom(game, pos);
	}
}
