/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ , MATEI-CRISTIAN FLOREA */
package tp1.logic.gameobjects;

import java.util.Arrays;

import tp1.exceptions.ObjectParseException;
import tp1.logic.Action;
import tp1.view.Messages;

public class ParamParser {

    // ===== Parseo de dirección =====
    public static Action parseDirection(String[] words, int index) throws ObjectParseException{
        if(words.length <= index) return null;
        String a = words[index];
        switch(words[index].toLowerCase()) {
        case "left": return Action.LEFT;
        case "right": return Action.RIGHT;
        case "up": return Action.UP;
        case "down": return Action.DOWN;
        default:
            throw new ObjectParseException(Messages.UNKNOWN_ACTION.formatted(a));
        }
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
