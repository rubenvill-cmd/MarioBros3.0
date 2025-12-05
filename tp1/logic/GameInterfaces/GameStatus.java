package tp1.logic.GameInterfaces;
//GAMEVIEW
public interface GameStatus {
	public boolean playerWins();
	public boolean playerLoses();
	public int remainingTime();
	public int points();
	public int numLives();
	public String positionToString(int col, int row);
	public String toString();
	
}
