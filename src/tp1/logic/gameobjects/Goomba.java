package tp1.logic.gameobjects;
import tp1.logic.Position;
import tp1.logic.GameInterfaces.GameWorld;
import tp1.logic.Action;
import tp1.view.Messages;

public class Goomba extends MovingObject {
	
private final static int POINTS_DIE = 100;
	
	//CONSTRUCTORAS
	public Goomba(GameWorld game, Position pos) {
		super(game, pos);
		act = defaultAct();
	}
	public Goomba() { //Constructora sin parámetros para la lista de objetos en GameObjectFactory
		super(null, new Position(0,0));
	}
	
	
	@Override
	protected Action defaultAct() {
		return Action.LEFT;
	}

	@Override
	public String getIcon() {
		if (!super.isAlive()) {
			return Messages.EMPTY;
		}
		return Messages.GOOMBA;
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
	public boolean receiveInteraction(Mario obj) { //recibe la interacción de MARIO
		if (isAlive()) {
			goombaDies();
			return true;
		}
		return false;
	}
	private void goombaDies() {
		this.dead();
        game.incrPoints(POINTS_DIE);
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
		return Messages.OBJECT_GOOMBA_NAME;
	}
	
	@Override
	protected String getShortcut() {
		return Messages.OBJECT_GOOMBA_SHORTCUT;
	}
	@Override
	protected GameObject createObject(GameWorld game, Position pos) {
		return new Goomba(game, pos);
	}
	
}
