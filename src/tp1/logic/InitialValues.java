/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ  ,   MATEI-CRISTIAN FLOREA */
package tp1.logic;

//Clase dedicada exclusivamente a almacenar y aplicar los valores iniciales del juego para almacenar en fichero
public class InitialValues {
	
	private int remainingTime;
	private int points;
	private int numLives;

	
	public InitialValues(int time, int points, int numLives) {
	 
		this.remainingTime = time;
	    this.points = points;
	    this.numLives = numLives;
		
	}
	

	public InitialValues(InitialValues v) {
		 
		this.remainingTime = v.remainingTime;
	    this.points = v.points;
	    this.numLives = v.numLives;
		
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
	
	public void applyTo(GameStatusWriter g) {
		g.setNumLives(numLives);
		g.setPoints(points);
		g.setRemainingTime(remainingTime);
	    
	}
	
	public void applyTo(GameStatusWriter g, int points, int lives) {
		g.setNumLives(lives);
		g.setPoints(points);
		g.setRemainingTime(remainingTime);
	    
	}
	

	
	
}
