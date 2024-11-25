package org.connect4.gamelogic;

import org.connect4.model.Board;
import org.connect4.player.Player;

/**
 * A GameState osztály felelős a Connect-4 játék tábla állásainak megjelenítésében.
 *
 */
public class GameState {
    private Board board;

    public GameState(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

}
