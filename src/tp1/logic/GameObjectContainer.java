/**
 * GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
 * MATEI-CRISTIAN FLOREA
 */
package tp1.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tp1.logic.gameobjects.*;
import tp1.view.Messages;

/**
 * Contenedor polimórfico de todos los objetos del juego.
 * Reemplaza el manejo por tipo (Goomba[], Land[], etc.) con una lista genérica.
 */
public class GameObjectContainer {

    // ============================================================
    // == Atributos ===============================================
    // ============================================================
    private List<GameObject> objects;
    
    // ============================================================
    // == Constructor =============================================
    // ============================================================
    public GameObjectContainer() {
        this.objects = new ArrayList<>();
    }

    // ============================================================
    // == Métodos principales =====================================
    // ============================================================

    /**
     * Añade un objeto al contenedor.
     */
    public boolean add(GameObject object) {
        if (object != null)
            objects.add(object);
        return true;
    }

 
    /**
     * Actualiza todos los objetos y gestiona interacciones y limpieza.
     */
    public void update() {
    	
        // Primero actualizamos todos los objetos
        for (GameObject obj : objects) {
            obj.update();
            doInteractions(obj);
            
        }
        // Limpiamos los objetos muertos
        clean();
        
         
        
    }

    public void doInteractions(GameObject obj) {
        for (GameObject other : objects) {
            if (obj.isAlive()) {
                if (obj.interactWith(other)) {
                    if(!other.interactWith(obj)) break;
                }
            }
        }
    }

    /**
     * Elimina los objetos marcados como muertos.
     */
    private void clean() {
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            if (!obj.isAlive()) 
                it.remove();
            
        }
   }

    // ============================================================
    // == Métodos de consulta ==
    // ============================================================

    /**
     * Devuelve una representación textual de la posición en el tablero.
     */
    public String positionToString(Position pos) {
        StringBuilder cellContent = new StringBuilder();

        for (GameObject obj : objects) {
            if (obj.isInPosition(pos)) {
                cellContent.append(obj.getIcon());
            }
        }

        // Si no hay ningún objeto en esa posición, mostrar el carácter vacío
        return cellContent.length() > 0 ? cellContent.toString() : Messages.EMPTY;
    }


    /**
     * Indica si una posición está ocupada por un objeto sólido (por ejemplo, Land).
     */
    public boolean isSolid(Position pos) {
    	//  return objects.stream()   #Lmbd. expresion para 1 sola linea. TP2? 
       // .anyMatch(ob -> ob.isInPosition(pos) && ob.isSolid());
    	
    	for (GameObject ob : objects) {
			if(ob.isInPosition(pos)&&ob.isSolid()) return true;
		}
      return false;
    }

  


    // ============================================================
    // == Representación textual ==
    // ============================================================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (GameObject obj : objects) {
            sb.append(obj.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
