package tp1.logic;

public class InitialValues {
	
	private int remainingTime;
	private int points;
	private int numLives;
	
	public InitialValues(int time, int points, int numLives) {
	 
		this.remainingTime = time;
	    this.points = points;
	    this.numLives = numLives;
		
	}
	

	public int getNumLives() {
		return numLives;
	}

	public int getPoints() {
		return points;
	}

	public int getRemainingTime() {
		return remainingTime;
	}
	
}
