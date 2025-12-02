package tp1.logic.GameInterfaces;
import tp1.exceptions.GameModelException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.Action;
import tp1.logic.gameobjects.GameObject;
//CONTROLLER
public interface GameModel {
	public boolean isFinished();
	public void update();
	public void resetGame(int newLevel); //CAMBIAR RESET A UN MÉTODO
	public void resetGame();
	public void exit();
	public void addAction(Action act);
	public GameObject addGameObject(String[] objWords) throws ObjectParseException, OffBoardException;
	public void save(String fileName) throws GameModelException;
}
