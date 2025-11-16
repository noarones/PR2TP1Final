/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic;

public interface GameStatus {

	public boolean isFinished();
	public boolean playerWins();
	public boolean playerLoses();
	public int remainingTime();
	public int points();
	public int numLives();
	

	public String positionToString(int col, int row);
	
}
