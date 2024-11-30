package org.connect4.model;

import org.connect4.player.Player;

/**
 * Egy Connect 4 játék tábláját képviseli.
 */
public final class Board {
    /**
     * A tábla minimális mérete.
     */
    private static final int MIN_SIZE = 4;
    /**
     * A tábla maximális mérete.
     */
    private static final int MAX_SIZE = 12;
    /**
     * A tábla sorainak száma.
     */
    private final int rows;
    /**
     * A tábla oszlopainak száma.
     */
    private final int columns;
    /**
     * A játék tábláját reprezentáló rács.
     */
    private final Disc[][] grid;

    public Board(final int boardRows, final int boardColumns) {
        if (boardRows < MIN_SIZE || boardColumns < MIN_SIZE
                || boardRows > MAX_SIZE || boardColumns > MAX_SIZE) {
            throw new IllegalArgumentException(
                    "A táblaméretnek 4 és 12 között kell lennie."
            );
        }
        this.rows = boardRows;
        this.columns = boardColumns;
        this.grid = new Disc[rows][columns];
    }

    /**
     * Eldob egy korongot a megadott játékos számára az adott oszlopban.
     *
     * @param player a játékos, aki a lépést végrehajtja
     * @param column az oszlop, ahova a korongot dobni kell
     * @return true, ha a korong sikeresen leesett,
     *         false, ha az oszlop tele van
     * @throws IllegalArgumentException ha az oszlopindex érvénytelen
     */
    public boolean dropDisc(final Player player, final int column) {
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("Érvénytelen oszlop.");
        }
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][column] == null) {
                grid[row][column] = new Disc(player.getColor());
                return true;
            }
        }
        return false; // Az oszlop tele van
    }

    /**
     * Visszaadja a korong színét a megadott sorban és oszlopban.
     *
     * @param row a vizsgálandó sor indexe
     * @param col a vizsgálandó oszlop indexe
     * @return a korong színe, vagy null, ha a mező üres
     * @throws IllegalArgumentException ha a sor vagy oszlop index érvénytelen
     */
    public String getDiscColor(final int row, final int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns) {
            throw new IllegalArgumentException("Érvénytelen sor vagy oszlop.");
        }
        return grid[row][col] != null ? grid[row][col].getColor() : null;
    }

    /**
     * Visszaadja a rács aktuális állapotát sztringként.
     *
     * @return a rács sztring reprezentációja
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (grid[row][col] == null) {
                    sb.append("- "); // Üres mező karaktere
                } else {
                    sb.append(grid[row][col].getColor().charAt(0)).append(" ");
                    // Korong színének első betűje
                }
            }
            sb.append("\n"); // Új sor minden sor végén
        }

        return sb.toString();
    }

    /**
     * Visszaadja a tábla sorainak számát.
     *
     * @return a sorok száma
     */
    public int getRows() {
        return rows;
    }

    /**
     * Visszaadja a tábla oszlopainak számát.
     *
     * @return az oszlopok száma
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Visszaadja a rács aktuális állapotát.
     *
     * @return a táblát reprezentáló rács
     */
    public Disc[][] getGrid() {
        return grid;
    }

}
