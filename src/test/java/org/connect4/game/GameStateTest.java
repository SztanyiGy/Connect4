package org.connect4.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameStateTest {

    @Test
    public void testSaveAndLoadGame() throws Exception {
        GameState gameState = new GameState();
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
        GameState loadedGameState = new GameState(); // Új GameState példány a betöltéshez
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
        GameState gameState = new GameState();

        // Új tábla létrehozása és beállítása
        Board newBoard = new Board(6, 7); // 6 sor, 7 oszlop
        gameState.setBoard(newBoard);

        // Ellenőrizzük, hogy a board változó az új táblát tartalmazza-e
        assertEquals(newBoard, gameState.getBoard(), "A tábla nem lett helyesen beállítva.");
    }
}
