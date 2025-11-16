/* GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ , MATEI-CRISTIAN FLOREA */
package tp1.logic.gameobjects;

import tp1.logic.Action;

public class ParamParser {

    // ===== Parseo de dirección =====
    public static Action parseDirection(String[] words, int index) {
        if(words.length <= index) return null;
        Action a = Action.parseAction(words[index].toLowerCase());
        return (a == Action.LEFT || a == Action.RIGHT ? a : null);
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
