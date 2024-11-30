package org.connect4.gamelogic;

import org.connect4.model.Board;
/**
 * A Logic osztály ellenőrzi, hogy melyik játékos nyert.
 */

public class Logic {
    private final WinChecker winChecker;

    public Logic(Board board) {
        this.winChecker = new WinChecker(board);
    }

    public boolean checkForWin(String color) {
        return winChecker.checkForWin(color);
    }

}
