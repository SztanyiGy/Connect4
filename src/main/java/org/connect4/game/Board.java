package org.connect4.game;

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
     * Győzelmi feltétel: az egymást követő korongok száma,
     * amely szükséges a győzelemhez.
     */
    private static final int CONNECT_WIN_COUNT = 4;
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
    /**
     * Létrehoz egy táblát megadott méretekkel.
     *
     * @param boardRows    a tábla sorainak száma,
     *                     4 és 12 között kell lennie
     * @param boardColumns a tábla oszlopainak száma,
     *                     4 és 12 között kell lennie
     * @throws IllegalArgumentException ha a sorok vagy
     * oszlopok kívül esnek a megadott határokon
     */
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
     * Ellenőrzi, hogy van-e győzelmi feltétel a megadott színhez.
     *
     * @param color a szín, amelyet ellenőrizni kell a győzelemhez
     * @return true, ha a szín győz, különben false
     */
    public boolean checkForWin(final String color) {
        // Függőleges ellenőrzés
        for (int row = 0; row <= rows - CONNECT_WIN_COUNT; row++) {
            for (int col = 0; col < columns; col++) {
                if (isWinningSequence(row, col, color, 1, 0)) {
                    return true;
                }
            }
        }

        // Vízszintes ellenőrzés
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= columns - CONNECT_WIN_COUNT; col++) {
                if (isWinningSequence(row, col, color, 0, 1)) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (balról jobbra)
        for (int row = 0; row <= rows - CONNECT_WIN_COUNT; row++) {
            for (int col = 0; col <= columns - CONNECT_WIN_COUNT; col++) {
                if (isWinningSequence(row, col, color, 1, 1)) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (jobbról balra)
        for (int row = 0; row <= rows - CONNECT_WIN_COUNT; row++) {
            for (int col = CONNECT_WIN_COUNT - 1; col < columns; col++) {
                if (isWinningSequence(row, col, color, 1, -1)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * Segédfüggvény, amely ellenőrzi a győzelmi sorozatot egy adott irányban.
     *
     * @param row          a kezdősor
     * @param col          a kezdőoszlop
     * @param color        a szín, amelyet ellenőrizni kell
     * @param rowIncrement a sor irányának növelése
     * @param colIncrement az oszlop irányának növelése
     * @return true, ha van győzelmi sorozat, különben false
     */
    private boolean isWinningSequence(
            final int row, final int col,
            final String color, final int rowIncrement,
            final int colIncrement) {
        for (int i = 0; i < CONNECT_WIN_COUNT; i++) {
            if (grid[row + i * rowIncrement][col + i * colIncrement] == null
                    || !grid[row + i * rowIncrement][col + i * colIncrement]
                    .getColor().equals(color)) {
                return false;
            }
        }
        return true;
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
                    sb.append(". "); // Üres mező
                } else {
                    sb.append(grid[row][col].getColor()
                            .charAt(0)).append(" ");
                    // Korong színének első betűje
                }
            }
            sb.append("\n"); // Új sor minden sor végén
        }

        return sb.toString();
    }
    /**
     * Kinyomtatja a tábla aktuális állapotát.
     */
    public void printBoard() {
        System.out.print(this.toString()); // Kiírja a tábla állapotát
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
