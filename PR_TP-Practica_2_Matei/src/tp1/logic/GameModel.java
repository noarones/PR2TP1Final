package tp1.logic;

public interface GameModel {

	public void update();
	public void addAction(Action act);
	public boolean addGameObject(String[] objectDescription, String Mode);
	public void exit();
	
	public void reset(int nLevel);
	
	
}
