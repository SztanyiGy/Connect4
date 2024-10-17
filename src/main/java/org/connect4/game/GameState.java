package org.connect4.game;

import org.connect4.player.ComputerPlayer;
import org.connect4.player.HumanPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
/**
 * A Connect 4 játékot megvalósító osztály, amely kezeli a játéktáblát,
 * a játékosokat és a játékállás betöltését vagy mentését.
 */
public final class GameState {
    /** A játéktábla sorainak száma. */
    private static final int ROWS = 6;
    /** A játéktábla oszlopainak száma. */
    private static final int COLUMNS = 7;
    /** A játéktábla. */
    private Board board;
    /** Az első játékos, aki emberi. */
    private HumanPlayer player1;
    /** A második játékos, aki számítógép. */
    private ComputerPlayer player2;
    /**
     * Létrehoz egy új játékot egy üres táblával.
     */
    public GameState() {
        this.board = new Board(ROWS, COLUMNS); // Üres tábla inicializálása
    }
    /**
     * Játékállás betöltése fájlból.
     *
     * @param filePath A fájl elérési útvonala.
     */
    public void loadGameFromFile(final String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            for (int row = 0; row < lines.size() && row < ROWS; row++) {
                String line = lines.get(row);
                String[] colors = line.split(" ");
                // Szóközök alapján bontsd le a sort
                for (int col = 0; col < colors.length && col < COLUMNS; col++) {
                    String color = colors[col].trim();
                    if (color.equals("P")) {
                        board.getGrid()[row][col] = new Disc("Piros");
                    } else if (color.equals("S")) {
                        board.getGrid()[row][col] = new Disc("Sárga");
                    } else {
                        board.getGrid()[row][col] = null; // Üres mező
                    }
                }
            }
            System.out.println("Játékállás betöltve: " + filePath);
        } catch (IOException e) {
            System.out.println(
                    "A fájl nem található. Üres pályáról indul a játék.");
        }
    }

    /**
     * Játékállás mentése fájlba.
     *
     * @param filePath A fájl elérési útvonala.
     */
    public void saveGameToFile(final String filePath) {
        try {
            List<String> lines = new ArrayList<>();

            // Játéktábla sorainak bejárása és mentése
            for (int row = 0; row < ROWS; row++) {
                StringBuilder line = new StringBuilder();
                for (int col = 0; col < COLUMNS; col++) {
                    Disc disc = board.getGrid()[row][col];
                    if (disc == null) {
                        line.append(". "); // Üres mező
                    } else {
                        line.append(disc.getColor().charAt(0)).append(" ");
                        // Korong színének első betűje
                    }
                }
                lines.add(line.toString().trim()); // Sor mentése
            }

            Files.write(Paths.get(filePath), lines);
            System.out.println("Játékállás elmentve: " + filePath);
        } catch (IOException e) {
            System.out.println("Nem sikerült elmenteni a játékállást.");
        }
    }
    /**
     * Visszaadja a játéktáblát.
     *
     * @return A játéktábla, amely tartalmazza a játék állását.
     */
    public Board getBoard() {
        return board;
    }
    /**
     * Beállítja a játéktáblát a megadott táblára.
     *
     * @param newBoard A játéktábla, amelyet be szeretnénk állítani.
     */
    public void setBoard(final Board newBoard) {
        this.board = newBoard;
    }
}
