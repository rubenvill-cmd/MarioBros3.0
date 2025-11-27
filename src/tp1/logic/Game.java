package tp1.logic;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Box;
import tp1.logic.gameobjects.Mushroom;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Mario;
import tp1.view.Messages;
import tp1.logic.GameInterfaces.GameModel;
import tp1.logic.GameInterfaces.GameStatus;
import tp1.logic.GameInterfaces.GameWorld;

//Clase game implementa las 3 interfaces que componen al game.
public class Game implements GameModel, GameStatus, GameWorld{
	
	//Atributos
	private GameObjectContainer gameObjects;
	private Mario mario;
	private int nLevel; 
	private int remainingTime;
	private int points;
	private int lifes;
	private boolean exitRequested;
	private boolean exitedDoor;
	//Dimensiones del tablero.
	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;

	
	
	//CONSTRUCTORA
	public Game(int nLevel) { //se pasa al Game el nivel desde los argumentos que le pasamos al compilador.
		this.nLevel = nLevel;
		this.points = 0;
		this.lifes = 3;
		this.gameObjects = new GameObjectContainer(); //Creamos un nuevo contenedor de objetos.
		exitedDoor = false;
		exitRequested = false;
		
		//iniciar el nivel dependiendo del número de nivel que hayamos metido.
		if(nLevel == 0) initLevel0();
		else if (nLevel == 1) initLevel1();
		else if (nLevel == 2) initLevel2();
		else if(nLevel == -1) initLevelBlank();
		
	}
	
	//MÉTODOS DE GAMEMODEL
	
	@Override
	public boolean isFinished(){ //El juego se termina si Mario sale por la ExitDoor, Mario muere porque (lifes = 0)
		//O el comando "exit" ha sido ejecutado, que es una booleana que devuelve true en el game.
		return (playerWins() || playerLoses() || exitRequested);
	}
	
		//RESET DEL JUEGO
	@Override
	public void resetGame() { //reinicio del juego actual con this.nlevel.
		if(this.nLevel == 0) initLevel0();
		else if(this.nLevel == 1) initLevel1();
		else if (nLevel == 2) initLevel2();
		else if(this.nLevel == -1) initLevelBlank();
	}
	@Override
	public void resetGame(int newLevel) { //reinicio del nivel que solicita el usuario con el número de nivel.
		this.nLevel = newLevel;
		
		if(newLevel == 0) initLevel0();
		else if(newLevel == 1) initLevel1();
		else if (newLevel == 2) initLevel2();
		else if (newLevel == -1)initLevelBlank();
	}
	
		//UPDATE EL JUEGO
	@Override
	public void update() {
		remainingTime--; //Restamos el tiempo por cada iteración
		gameObjects.update();//Y llamamos al GameObjectContainer para que actualice todos los objetos.
	}
	
		//ACCIONES DE MARIO
	@Override
	public void addAction(Action act){ //Lo llama ActionCommand para añadirle acciones a la lista de acciones
		//a través de mario, si es que mario existe.
		if(mario != null)
			mario.addAction(act);
	}
		//EXIT
	@Override
	public void exit() { //Lo llama exitCommand.
		exitRequested = true;
	}
		//ADD OBJECT EN LA OBJECTCONTAINER LIST.
	@Override
	public GameObject addGameObject(String[] objWords) {
		//1º Creamos un Mario temporal en el caso que no exista (inItLevelBlank)
		Mario tempMario = new Mario();
		//2º Verificamos si el usuario quiere añadir un nuevo Mario, ya que su adición al juego se hace de forma diferente.
		Mario newMario = tempMario.parse(objWords, this);
		GameObject newObject;
		
		if (newMario != null) {
			//3º Si vamos a añadir un Mario, entonces llamamos a esta función
			changeMario(newMario);
			newObject = newMario;
			} else {
				//4º Si realmente el usuario no quería añadir un Mario, llamamos al parse de los demás objetos.
				newObject = GameObjectFactory.parse(objWords, this);
			}
		if (newObject != null) {
			gameObjects.add(newObject);
			return newObject;
		} else {
			return null;
		}

	}
	//el parse de mario se llama con el mario, primero se comprueba si ya hay un mario en el juego, si lo hay
	//ese mario se tiene que eliminar de la lista de objetos
	private void changeMario(Mario newMario) {
		if(this.mario != null) {
			//3.1º Si ya existe un Mario en el juego, lo quitamos
			gameObjects.remove(this.mario);
		}
		//3.2º Halla existido o no this.mario, le asignamos un nuevo mario y se lo returneamos addObjectCommand.
		this.mario = newMario;
	}
	
	
	//MÉTODOS DE GAMESTATUS
		//ESTADO DE LA PARTIDA
	@Override
	public boolean playerWins() {
		return exitedDoor; //El usuario gana si sale por la puerta (exitedDoor = true).
	}
	@Override
	public boolean playerLoses() { //El usuario pierde si no tiene vidas o si se la acaba el tiempo.
		return (lifes <= 0 || remainingTime <= 0);
	}
	
		//ESTADO DEL GAME
	@Override
	public int remainingTime() {return remainingTime;}
	@Override
	public int points() {return points;}
	@Override
	public int numLives() {return lifes;}
	
		//SALIDAS POR CONSOLA
	@Override
	public String positionToString(int col, int row) { 
		Position pos = new Position(row, col);
		return gameObjects.positionToString(pos); //Devolvemos icono del objeto en su posición.
	}
	@Override
	//devuelve los mensajes predeterminados ya formateados.
	public String toString() {
		 return Messages.GAME_NAME + " " + Messages.VERSION + "\n" +
         Messages.REMAINING_TIME.formatted(remainingTime) + "\n" +
         Messages.POINTS.formatted(points) + "\n" +
         Messages.NUM_LIVES.formatted(lifes);
	}
	
	
	
	
	//MÉTODOS DE GAMEWORLD
	@Override
	public boolean isPosSolid(Position pos) {
		return gameObjects.isPosSolid(pos); //Pregunta a los objetos si en una posición dada se encuentra un objeto sólido.
	}
	@Override
	public void marioDies() {
		lifes--; //Si mario muere ya sea si le ha matado goomba o si ha salido del tablero, le quitamos una vida y reiniciamos.
		if (lifes != 0) resetGame();
	}
	@Override
	public void marioExited() {
		points = points + (remainingTime*10); //Le añadimos puntos por ganar.
		remainingTime = 0;
		reachedExit(); //Indicamos que ha salido, exitDoor = true.
	}
	@Override
	public void reachedExit() {
		exitedDoor = true;
	}
	@Override
	public void interact(GameItem other) { //es para que mario cuando haga su update() verifique si se ha chocado con algo (antes de hacer el update del resto)
		gameObjects.doInteraction(other);
	}
	@Override
	public void incrPoints(int incr) {
		points = points + incr; //sumamos puntos 
	}
	@Override
	public void addNewObject(GameObject gameobject) {
		gameObjects.toAdd(gameobject);//Añadimos un objeto de la lista auxiliar de objetos por añadir a la lista de objetos.
	}
	
	
	
	
	//NIVELES 0, 1, 2 y BLANK
	private void initLevel0() {
		this.nLevel = 0;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));//Game.DIM_Y-3, 0
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
	}
	
	private void initLevel1() {
		this.nLevel = 1;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));
		//gameObjects.add(new Land(new Position(11,2))); //QUITAR
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0)); //(Game.DIM_Y-3, 0)
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(4, 6)));
	}
	
	private void initLevel2() {
		this.nLevel = 2;
		this.remainingTime = 100;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(this, new Position(13,col)));
			gameObjects.add(new Land(this, new Position(14,col)));		
		}

		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(this, new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(this, new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(this, new Position(9,2)));
		gameObjects.add(new Land(this, new Position(9,5)));
		gameObjects.add(new Land(this, new Position(9,6)));
		gameObjects.add(new Land(this, new Position(9,7)));
		gameObjects.add(new Land(this, new Position(5,6)));
		//gameObjects.add(new Land(new Position(11,2))); //QUITAR
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(this, new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(this, new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0)); //(Game.DIM_Y-3, 0)
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		gameObjects.add(new Goomba(this, new Position(0, 19)));
		gameObjects.add(new Goomba(this, new Position(4, 6)));
		
		gameObjects.add(new Box(this, new Position(9, 4)));
		gameObjects.add(new Mushroom(this, new Position(12, 8)));
		gameObjects.add(new Mushroom(this, new Position(2, 20)));
	}
	
	private void initLevelBlank() {
		this.nLevel = -1;
		this.remainingTime = 100;
		this.lifes = 3;
		this.points = 0;
		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
	}
}
