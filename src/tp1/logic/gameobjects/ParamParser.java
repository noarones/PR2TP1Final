/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ , MATEI-CRISTIAN FLOREA */
package tp1.logic.gameobjects;

import tp1.exceptions.ActionParseException;

import tp1.logic.Action;
import tp1.view.Messages;

public class ParamParser {

	//Predicados para condiciones especiales de los objetos: No significa que haya que crear un predicado por cada objeto,
	//solo se incluyen si hay condiciones especiales como Goomba solo puede ir derecha o izq
	public static boolean validGoomba = false;
	
	
    // ===== Parseo de dirección =====
    public static Action parseDirection(String[] words, int index) throws ActionParseException{
        if(words.length <= index) return null;
        Action a = Action.parseAction(words[index].toLowerCase());
        if( a == null) 
        	throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(words[index]));
        
               	
        	
        return a;
    }

    // ===== Parseo de booleanos con múltiples alias =====
    public static boolean parseBoolean(String[] words, int index, String true1, 
                                       String true2, String false1, String false2,
                                       boolean defaultVal)
    {
        if(words.length <= index) return defaultVal;

        String w = words[index].toLowerCase();

        if(w.equals(true1) || w.equals(true2)) return true;
        if(w.equals(false1) || w.equals(false2)) return false;

       return defaultVal;
    }
}
