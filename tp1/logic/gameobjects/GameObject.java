package tp1.logic.gameobjects;

import tp1.exceptions.GameParseException;
import tp1.view.Messages;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;
import tp1.logic.Action;
import tp1.logic.GameInterfaces.GameWorld;
//import tp1.logic.Game;
import tp1.logic.Position;

public abstract class GameObject implements GameItem{ 
	
	private Position pos;
	private boolean isAlive;
	protected GameWorld game;
	
	//CONSTRUCTORAS
	public GameObject(GameWorld game, Position pos) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
	}
	
	protected GameObject(GameObject other) { //Constructora por copia
		this.pos = other.pos;
		this.game = other.game;
		this.isAlive = other.isAlive;
		}


	//ICONO DEL OBJETO
	public abstract String getIcon();
	
	
	//MÉTODOS DE GAMEITEM
	@Override
	public abstract boolean isSolid();
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	@Override
	public boolean isInPos(Position p) {
		return this.pos.equals(p);
	}
	
	//COPIA DE GAMEOBJECTS
	public abstract GameObject copy ();
	
			
	
	public void dead(){
		this.isAlive = false;
	}
	
	public abstract void update(); //cada update() de cada objeto hace una cosa
	
	protected void move(Action dir) { //mueve a la posicion en la dirección indicada
		this.pos = relativePos(dir);
	}
	
	
	public boolean doInteraction(GameItem other) { //las interacciones son bidireccionales, así que da igual quién interactúe con quien
		boolean interaction1 = other.interactWith(this);
		boolean interaction2 = this.interactWith(other);
		return (interaction1 || interaction2);
	}
	
	
	
	//COMPARACIÓN POSICIONES
	protected boolean isSidePosSolid (Action dir) { //mira si la posición al lado de pos (en la dirección dada) es solida
		return game.isPosSolid(relativePos(dir));
	}
	protected boolean posExitsBoard(Action dir) { //mira si la dirección posición al lado de pos se sale del tablero
		return relativePos(dir).exitsBoard();
	}
	protected Position relativePos(Action dir) {
		return pos.movePos(dir);
	}
	protected boolean otherInPos(GameItem other) { //compara la posición de dos personajes, para los interactWith()
		return other.isInPos(this.pos);
	}
	protected boolean otherInRelativePos(GameItem other, Action dir) { //compara la posición de uno con la posición relativa de otro (se usa para el BOX)
		return other.isInPos(relativePos(dir));
	}
	
	
	
	//MÉTODOS PARA  matchObjectGame y PARSE DE CADA OBJETO DEL JUEGO
	protected abstract String getName();
		
	protected abstract String getShortcut();

	protected abstract GameObject createObject(GameWorld game, Position pos); //cada objeto del juego se crea a sí mismo
	
	protected boolean matchName(String name) { //devuelve si el string dado se corresponde con el nombre o el shortcut
		return getShortcut().equalsIgnoreCase(name) || 
				getName().equalsIgnoreCase(name);
	}

	
	//parse de la posición y el nombre
	//objDescription: pos, nombre (no tiene el ao)
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		if (matchName(objDescription[1])) {	
			try {
			Position userPos = Position.parsePos(objDescription); //parse de la posición (convierte un String en Position)
			if(userPos != null) { //es null cuando la posición no es válida
				return createObject(game, userPos);
			}
			}
			catch (PositionParseException pExc) {
				throw new GameParseException(Messages.INVALID_OBJ_POS.formatted(String.join(" ", objDescription)), pExc); //envuelve el error de Posicion en su superclase GameParseException
			}
		}
		return null; //si devuelve null es porque el nombre no coincide con el de GameObjectFactory (no es el objeto que queremos parsear)
	}
	
	
	
	@Override
	public String toString() {
		String p = pos.toString() + " ";
		return p + getName();
	}
}