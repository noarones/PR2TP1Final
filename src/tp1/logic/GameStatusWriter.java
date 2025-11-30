package tp1.logic;

public interface GameStatusWriter extends GameStatus {

     public void setRemainingTime(int time);
     public void setPoints(int points);
     public void setNumLives(int lives) ;
}
