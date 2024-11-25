package org.connect4.gamelogic;

import org.connect4.model.Board;

/**
 * Segédosztály a Connect-4 táblán található győzelmi feltétel ellenőrzésére.
 */
public class WinChecker {
    private static final int CONNECT_WIN_COUNT = 4;

    private final Board board;

    /**
     * Létrehoz egy WinChecker példányt a megadott táblához.
     *
     * @param board A játék tábla, amelyen a győzelmi feltételt ellenőrizni kell.
     */
    public WinChecker(Board board) {
        this.board = board;
    }

    /**
     * Ellenőrzi, hogy a megadott játékosszínnek van-e nyerő sorozata a táblán.
     *
     * @param color A játékos korongjainak színe, amelyet nyerésre ellenőriz.
     * @return Igaz, ha található nyerő sorozat, egyébként hamis.
     */
    public boolean checkForWin(final String color) {
        // Függőleges ellenőrzés
        for (int row = 0; row <= board.getRows() - CONNECT_WIN_COUNT; row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                if (isWinningSequence(row, col, color, 1, 0)) {
                    return true;
                }
            }
        }

        // Vízszintes ellenőrzés
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getColumns() - CONNECT_WIN_COUNT; col++) {
                if (isWinningSequence(row, col, color, 0, 1)) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (balról jobbra)
        for (int row = 0; row <= board.getRows() - CONNECT_WIN_COUNT; row++) {
            for (int col = 0; col <= board.getColumns() - CONNECT_WIN_COUNT; col++) {
                if (isWinningSequence(row, col, color, 1, 1)) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (jobbról balra)
        for (int row = 0; row <= board.getRows() - CONNECT_WIN_COUNT; row++) {
            for (int col = CONNECT_WIN_COUNT - 1; col < board.getColumns(); col++) {
                if (isWinningSequence(row, col, color, 1, -1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isWinningSequence(final int row, final int col, final String color, final int rowIncrement, final int colIncrement) {
        for (int i = 0; i < CONNECT_WIN_COUNT; i++) {
            if (board.getGrid()[row + i * rowIncrement][col + i * colIncrement] == null
                    || !board.getGrid()[row + i * rowIncrement][col + i * colIncrement].getColor().equals(color)) {
                return false;
            }
        }
        return true;
    }
}
