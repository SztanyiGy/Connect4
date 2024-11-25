package org.connect4.gameworking;

import org.connect4.model.Board;

/**
 * A BoardRenderer osztály felelős a Connect-4 játéktábla megjelenítéséért a konzolon.
 * Ez az osztály a {@link Board} objektum aktuális állapotát jeleníti meg.
 */
public class BoardRenderer {

    /**
     * Megjeleníti a tábla aktuális állapotát a konzolon.
     *
     * @param board a tábla objektum, amelyet meg kell jeleníteni.
     */
    public void render(Board board) {
        System.out.print(board.toString()); // A Board toString() metódusa adja vissza a tábla állapotát
    }
}
