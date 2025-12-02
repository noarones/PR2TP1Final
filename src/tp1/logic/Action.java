/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;

/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);
	
	private int x;
	private int y;
	
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static Action parseAction(String action) {
		Action ret = null;
		
		switch(action) {
		case "left" : ret = Action.LEFT; break;
		case "l"    : ret = Action.LEFT; break;
		case "r" : ret = Action.RIGHT; break;
		case "right"    : ret = Action.RIGHT; break;
		case "down" : ret = Action.DOWN; break;
		case "d"    : ret = Action.DOWN; break;
		case "up" : ret = Action.UP; break;
		case "u"    : ret = Action.UP; break;
		case "stop" : ret = Action.STOP; break;
		case "s"    : ret = Action.STOP; break;
		default: break;
		}
		return ret;
	}

	public static Action oppositeAction(Action a) {
		switch(a) {
		case LEFT: return Action.RIGHT;
		case UP:  return Action.DOWN;
		case DOWN: return Action.UP;
		case RIGHT: return Action.LEFT;
		
		default:
			return Action.STOP;
		}
	}
    
    public static boolean isXMove(Action dir) {
    	return dir == Action.LEFT || dir == Action.RIGHT;
    }
 
    public static boolean isUpMove(Action dir) {
    	return dir == Action.UP;
    }
    public static boolean isDownMove(Action dir) {
    	return dir == Action.DOWN;
    }
    
    public static boolean isYMove(Action dir) {
    	return isDownMove(dir) || isUpMove(dir);
    }
    
	
	
	public String toString() {
		String aux = "";
		switch(this) {
			case LEFT ->  aux = "LEFT";
			case RIGHT -> aux = "RIGHT";
			case DOWN -> aux = "DOWN";
			case UP -> aux = "UP";
			case STOP -> aux = "STOP";
		};
		return aux;
	}
	
	
	
	
}
