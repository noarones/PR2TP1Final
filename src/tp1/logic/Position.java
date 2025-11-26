/**
 *  GRUPO 19 : NOÉ HARIM ARONES DE LA CRUZ
MATEI-CRISTIAN FLOREA
 */
package tp1.logic;

import java.util.Objects;

/**
 * Clase inmutable que representa una posición en el tablero del juego.
 * 
 * Cada instancia contiene una coordenada de fila (row) y columna (col).
 * Proporciona métodos para desplazarse en distintas direcciones, verificar
 * límites y comparar posiciones.
 */
public class Position {

    // ============================================================
    // == Atributos ==
    // ============================================================
    private final int row;
    private final int col;

    // ============================================================
    // == Constructor ==
    // ============================================================
    /**
     * Crea una nueva posición con la fila y columna dadas.
     *
     * @param row Fila de la posición.
     * @param col Columna de la posición.
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // ============================================================
    // == Métodos de movimiento ==
    // ============================================================

    /**
     * Devuelve una nueva posición desplazada según una dirección de acción.
     *
     * @param dir Dirección en la que se mueve.
     * @return Nueva posición resultante del movimiento.
     */
    public Position move(Action dir) {
        return new Position(this.row + dir.getY(), this.col + dir.getX());
    }

    /** @return Nueva posición una fila debajo. */
    public Position under() {
        return new Position(this.row + 1, this.col);
    }

    /** @return Nueva posición una columna a la derecha. */
    public Position right() {
        return new Position(this.row, this.col + 1);
    }

    /** @return Nueva posición una columna a la izquierda. */
    public Position left() {
        return new Position(this.row, this.col - 1);
    }

    /** @return Nueva posición una fila arriba. */
    public Position up() {
        return new Position(this.row - 1, this.col);
    }

    // ============================================================
    // == Métodos de comprobación ==
    // ============================================================

    /**
     * Comprueba si la posición está junto al límite derecho o izquierdo.
     *
     * @param pos Otra posición (no usada en esta implementación, pero mantenida por compatibilidad).
     * @param dim Valor del límite (columna máxima o mínima).
     * @return true si esta posición está justo al límite especificado.
     */
    public boolean nextToLimit(Position pos, int dim) {
        return this.col == dim;
    }

    /**
     * Comprueba si la posición ha caído fuera del tablero verticalmente.
     *
     * @param pos Otra posición (no usada en esta implementación, pero mantenida por compatibilidad).
     * @param dim Valor del límite inferior (última fila).
     * @return true si esta posición está fuera del tablero.
     */
    public boolean fallenOut(Position pos, int dim) {
        return this.row >= dim;
    }

    // ============================================================
    // == Métodos utilitarios ==
    // ============================================================

    public boolean isInBoard(int DIM_X, int DIM_Y) {
        return this.row >= 0 && this.row < DIM_Y &&
               this.col >= 0 && this.col < DIM_X;
    }

    /**
     * Crea una copia de otra posición.
     * 
     * @param pos Posición a copiar.
     * @return Nueva instancia con los mismos valores.
     */
    public Position copy(Position pos) {
        return new Position(pos.row, pos.col);
    }

    // ============================================================
    // == Métodos estándar de Object ==
    // ============================================================

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    @Override 
    public String toString() {
    	return "("+Integer.toString(row) + "," +Integer.toString(col) + ")";
    }
}
