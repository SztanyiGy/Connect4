package org.connect4;

import org.connect4.gameworking.Game;

import java.util.Scanner;

/**
 * A Connect 4 játék fő osztálya, amely elindítja a játékot
 * és kezeli a játékosok interakcióját.
 */
public final class Main {
    /**
     * A játéktábla sorainak száma.
     */
    public static final int ROWS = 6;
    /**
     * A játéktábla oszlopainak száma.
     */
    public static final int COLUMNS = 7;

    /**
     * A játék belépési pontja.
     *
     * @param args A parancssori argumentumok.
     */
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        game.start();
    }

    // Privát konstruktor
    protected Main() {
        throw new UnsupportedOperationException(
                "Utility osztály, nem példányosítható.");
    }
}
