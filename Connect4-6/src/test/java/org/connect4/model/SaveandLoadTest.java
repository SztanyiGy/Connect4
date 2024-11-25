package org.connect4.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveandLoadTest {

    @Test
    public void testSaveAndLoadGame() throws Exception {
        SaveandLoad gameState = new SaveandLoad();
        String filePath = "test_game_state.txt";

        // Korongok hozzáadása a táblához a teszt során
        gameState.getBoard().getGrid()[0][0] = new Disc("Piros");
        gameState.getBoard().getGrid()[0][1] = new Disc("Sárga");
        gameState.getBoard().getGrid()[1][0] = new Disc("Piros");
        gameState.getBoard().getGrid()[1][1] = new Disc("Sárga");

        // Játékállás mentése fájlba
        gameState.saveGameToFile(filePath);
        assertTrue(Files.exists(Paths.get(filePath)), "A fájl nem létezik.");

        // Játékállás betöltése fájlból
        SaveandLoad loadedGameState = new SaveandLoad(); // Új GameState példány a betöltéshez
        loadedGameState.loadGameFromFile(filePath);

        // Ellenőrizzük, hogy a betöltött játékállás megegyezik a mentett állással
        assertEquals(gameState.getBoard().getGrid()[0][0].getColor(), loadedGameState.getBoard().getGrid()[0][0].getColor());
        assertEquals(gameState.getBoard().getGrid()[0][1].getColor(), loadedGameState.getBoard().getGrid()[0][1].getColor());
        assertEquals(gameState.getBoard().getGrid()[1][0].getColor(), loadedGameState.getBoard().getGrid()[1][0].getColor());
        assertEquals(gameState.getBoard().getGrid()[1][1].getColor(), loadedGameState.getBoard().getGrid()[1][1].getColor());

        // Takarítsuk el a fájlt a teszt után
        Files.deleteIfExists(Paths.get(filePath));
    }
    @Test
    public void testSetBoard() {
        SaveandLoad gameState = new SaveandLoad();

        // Új tábla létrehozása és beállítása
        Board newBoard = new Board(6, 7); // 6 sor, 7 oszlop
        gameState.setBoard(newBoard);

        // Ellenőrizzük, hogy a board változó az új táblát tartalmazza-e
        assertEquals(newBoard, gameState.getBoard(), "A tábla nem lett helyesen beállítva.");
    }
}
