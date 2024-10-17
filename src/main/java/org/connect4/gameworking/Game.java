package org.connect4.gameworking;

import org.connect4.Main;
import org.connect4.game.Board;
import org.connect4.game.Disc;
import org.connect4.player.ComputerPlayer;
import org.connect4.player.HumanPlayer;
import org.connect4.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
/**
 * A Connect 4 játék kezelése.
 */
public class Game {
    /**
     * A bemeneti Scanner példány.
     */
    private final Scanner scanner;

    /**
     * A játék állásának fájlba írásához használt fájl elérési útvonala.
     */
    public static final String FILE_PATH = "game_state.txt";

    /**
     * Konstruktor, amely inicializálja a játékot.
     *
     * @param inputScanner A bemenet olvasásához használt Scanner.
     */
    public Game(final Scanner inputScanner) {
        this.scanner = inputScanner; // Ezt a mezőt inicializáljuk
    }

    /**
     * A játék indítása.
     */
    public void start() {
        String playerName = getPlayerName();
        Player humanPlayer = new HumanPlayer(playerName, "Sárga");
        Player computerPlayer = new ComputerPlayer("Computer", "Piros");

        Board board = new Board(Main.ROWS, Main.COLUMNS);
        loadGameFromFile(board);

        boolean isHumanTurn = true;
        while (true) {
            board.printBoard();
            Player currentPlayer = isHumanTurn ? humanPlayer : computerPlayer;
            System.out.println(currentPlayer.getName() + " lép.");

            int column = currentPlayer.makeMove(board.getColumns());
            if (!board.dropDisc(currentPlayer, column)) {
                System.out.println("Oszlop megtelt. Próbáld újra.");
                continue;
            }

            if (board.checkForWin(currentPlayer.getColor())) {
                board.printBoard();
                System.out.println(currentPlayer.getName() + " nyert!");
                break;
            }

            isHumanTurn = !isHumanTurn;
        }

        saveGameToFile(board);
        System.out.println("A játék véget ért!");
    }
    /**
     * Játékos neve kérdése.
     *
     * @return A játékos neve.
     */
    protected String getPlayerName() {
        System.out.println("Játékos neve:");
        return scanner.nextLine();
    }

    /**
     * Játékállás betöltése fájlból.
     *
     * @param board A játéktábla, amit frissíteni kell.
     */
    protected void loadGameFromFile(final Board board) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            System.out.println("Fájl beolvasva: " + FILE_PATH);

            for (int row = 0; row < lines.size(); row++) {
                String[] colors = lines.get(row).split(" ");
                for (int col = 0; col < colors.length; col++) {
                    String color = colors[col].trim();
                    if (!color.isEmpty()) {
                        // Szín konvertálása
                        color = color.equals("S") ? "sárga" : color.equals("P") ? "piros" : color;
                        Disc disc = new Disc(color);
                        String finalColor = color;
                        board.dropDisc(new Player() {
                            @Override
                            public String getName() {
                                return ""; // Ideiglenes név
                            }

                            @Override
                            public String getColor() {
                                return finalColor; // A játékos színét visszaadjuk
                            }

                            @Override
                            public int makeMove(final int maxColumns) {
                                return 0; // Ideiglenes megvalósítás
                            }
                        }, col);
                    }
                }
            }
            System.out.println("Játékállás betöltve: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Nincs fájl. Üres pályáról indul a játék.");
        } catch (IllegalArgumentException e) {
            System.out.println("Érvénytelen korongszín: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Hiba történt a játékállás betöltésekor: " + e.getMessage());
        }
    }

    /**
     * Játékállás mentése fájlba.
     *
     * @param board A játéktábla, amit el kell menteni.
     */
    protected void saveGameToFile(final Board board) {
        try {
            Files.writeString(Paths.get(FILE_PATH), board.toString());
            System.out.println("Játékállás elmentve: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Nem sikerült menteni az állást: " + FILE_PATH);
        }
    }
}
