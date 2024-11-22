package org.connect4.gamelogic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.connect4.model.Board;



/**
 * A FileManager osztály felelős a Connect-4 játékállás fájlba mentéséért és onnan való betöltéséért.
 * Az állásokat alapértelmezés szerint a "game_state.txt" fájlba menti.
 */
public class FileManager {
    /**
     * Az alapértelmezett fájl elérési útvonala, ahová a játékállás mentésre kerül.
     */
    private static final String FILE_PATH = "game_state.txt";

    /**
     * Elmenti a játék aktuális állapotát egy fájlba.
     *
     * @param board a játék táblája, amelyet menteni kell.
     */
    public void saveGameToFile(Board board) {
        try {
            Files.writeString(Paths.get(FILE_PATH), board.toString());
            System.out.println("Játékállás elmentve: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Nem sikerült menteni az állást: " + FILE_PATH);
        }
    }

    /**
     * Betölti a játékállást egy fájlból, és alkalmazza a megadott táblára.
     *
     * @param board a tábla, amelyre a betöltött állást alkalmazni kell.
     */
    public void loadGameFromFile(Board board) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            // Implementáld a betöltési logikát
        } catch (IOException e) {
            System.out.println("Nincs fájl. Üres pályáról indul a játék.");
        }
    }
}
