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
	
	protected GameObject(GameObject other) {
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
	
	public abstract GameObject copy ();
	
	
	
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
	
	
	//Métodos para matchObjectGame y parse de cada objeto del juego
	protected abstract String getName();
		
	protected abstract String getShortcut();

	protected abstract GameObject createObject(GameWorld game, Position pos); //cada objeto del juego se crea a sí mismo
	
	protected boolean matchName(String name) { //devuelve si el string dado se corresponde con el nombre o el shortcut
		return getShortcut().equalsIgnoreCase(name) || 
				getName().equalsIgnoreCase(name);
	}
	
	public static Position createPosition (GameObject other) { // es estático porque lo llamamos en la constructora super,
		return new Position(other.pos);						   //Por tanto el método tiene que pertenecer a la clase, no a la instancia.
															   
	}
	
	//parse de la posición y el nombre
	//objDescription: pos, nombre (no tiene el ao)
	public GameObject parse(String[] objDescription, GameWorld game) throws GameParseException, OffBoardException{
		
		//if(objDescription.length >= 2 && matchName(objDescription[1])){ //parsea el objeto si la longitud es de dos o más, y si el nombre coincide
		if (matchName(objDescription[1])) {	
			try {
			Position userPos = Position.parsePos(objDescription); //parse de la posición (convierte un String en Position)
			if(userPos != null) { //es null cuando la posición no es válida
				return createObject(game, userPos);
			}
			}
			catch (PositionParseException pExc) {
				throw new GameParseException(Messages.INVALID_OBJ_POS.formatted(String.join(" ", objDescription)), pExc); //POSICIÓN MAL ESCRITA}
			//NO SE CATCHEA LA OFFBOARD EXCEPTION, SIMPLEMENTE LA LANZA (
			}
		}
		return null; //SI DEVUELVE NULL ES PORQUE EL OBJETO NO CONCORDABA CON EL NOMBRE (LO GESTIONA EL FACTORY) -> NO DA NULL SI HAY ERROR
	}
	
	@Override
	public String toString() {
		String p = pos.toString() + " ";
		return p + getName();
	}
}