package tp1.logic;

import tp1.logic.gameobjects.*;

public class LevelFactory {

    private final GameWorld game;

    public LevelFactory(GameWorld game) {
        this.game = game;
    }

    /** Construye el contenedor completo de objetos para un nivel */
    public GameObjectContainer createLevel(int lvl) {
        GameObjectContainer container = new GameObjectContainer();

        buildBaseGround(container);
        buildPlatforms(container);
        buildFinalJump(container);
        addCharacters(container, lvl);

        return container;
    }

    // =====================================================================================
    // SECCIONES DEL MAPA
    // =====================================================================================

    /* Terreno base del nivel */
    private void buildBaseGround(GameObjectContainer c) {
        // Franja izquierda del suelo
        for (int col = 0; col < 15; col++) {
            c.add(new Land(game, new Position(13, col)));
            c.add(new Land(game, new Position(14, col)));
        }

        // Dos bloques sueltos
        c.add(new Land(game, new Position(Game.DIM_Y - 3, 9)));
        c.add(new Land(game, new Position(Game.DIM_Y - 3, 12)));

        // Suelo derecho
        for (int col = 17; col < Game.DIM_X; col++) {
            c.add(new Land(game, new Position(Game.DIM_Y - 2, col)));
            c.add(new Land(game, new Position(Game.DIM_Y - 1, col)));
        }
    }

    /* Plataformas y bloques del centro */
    private void buildPlatforms(GameObjectContainer c) {
        c.add(new Land(game, new Position(9, 2)));
        c.add(new Land(game, new Position(9, 5)));
        c.add(new Land(game, new Position(9, 6)));
        c.add(new Land(game, new Position(9, 7)));
        c.add(new Land(game, new Position(5, 6)));
    }

    /* Pirámide final para el salto de salida */
    private void buildFinalJump(GameObjectContainer c) {
        int tamX = 8;
        int posIniX = Game.DIM_X - 3 - tamX;
        int posIniY = Game.DIM_Y - 3;

        for (int col = 0; col < tamX; col++) {
            for (int fila = 0; fila < col + 1; fila++) {
                c.add(new Land(game, new Position(posIniY - fila, posIniX + col)));
            }
        }
    }

    // =====================================================================================
    // PERSONAJES Y OBJETOS DEL NIVEL
    // =====================================================================================

    private void addCharacters(GameObjectContainer c, int lvl) {

        // Mario siempre aparece
        c.add(new Mario(game, new Position(Game.DIM_Y - 3, 0)));

        // Goomba base
        c.add(new Goomba(game, new Position(0, 19)));

        // En niveles 1 y 2: goombas extra
        if (lvl == 1 || lvl == 2) {
            c.add(new Goomba(game, new Position(4, 6)));
            c.add(new Goomba(game, new Position(12, 6)));
            c.add(new Goomba(game, new Position(12, 8)));
            c.add(new Goomba(game, new Position(10, 10)));
            c.add(new Goomba(game, new Position(12, 11)));
            c.add(new Goomba(game, new Position(12, 14)));
        }

        // Objetos especiales del nivel 2
        if (lvl == 2) {
            c.add(new Box(game, new Position(9, 4)));
            c.add(new Mushroom(game, new Position(12, 8)));
            c.add(new Mushroom(game, new Position(2, 20)));
        }

        // Puerta final
        c.add(new ExitDoor(game, new Position(Game.DIM_Y - 3, Game.DIM_X - 1)));
    }
}
