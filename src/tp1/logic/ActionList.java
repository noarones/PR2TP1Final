package tp1.logic;

import java.util.ArrayList;

public class ActionList {

    private final ArrayList<Action> actions; //array incompleto

    public ActionList(int maxActions) {
        actions = new ArrayList<>(maxActions);
    }

    // Añade una acción si hay espacio
    public void add(Action act) {
        actions.add(act);
    }

    // Obtiene la acción en posición i
    public Action get(int i) {
        if (i >= 0 && i < actions.size()) {
            return actions.get(i);
        }
        return null;
    }

    // Devuelve cuántas acciones hay
    public int size() {
        return actions.size();
    }

    // Vacía la lista al finalizar el turno
    public void clear() {
        actions.clear();
    }

    public void addAll(ActionList actions2) {
        for (int i = 0; i < actions2.size(); i++) {
            actions.add(actions2.get(i));
        }
    }
}
