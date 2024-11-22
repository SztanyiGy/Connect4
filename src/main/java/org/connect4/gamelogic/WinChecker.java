package org.connect4.gamelogic;

import org.connect4.model.Board;

/**
 * A utility class for checking if there is a winning condition on the Connect-4 board.
 */
public class WinChecker {
    private static final int CONNECT_WIN_COUNT = 4;

    private final Board board;

    /**
     * Constructs a WinChecker for the specified board.
     *
     * @param board The game board to check for a winning condition.
     */
    public WinChecker(Board board) {
        this.board = board;
    }

    /**
     * Checks if the specified player color has a winning sequence on the board.
     *
     * @param color The color of the player's discs to check for a win.
     * @return True if a winning sequence is found, false otherwise.
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
