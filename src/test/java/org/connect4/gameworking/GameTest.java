package org.connect4.gameworking;

import org.connect4.game.Board;
import org.connect4.player.HumanPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static final String TEST_FILE_PATH = "test_game_state.txt";
    private Board board;
    private Game game;
    private Scanner scanner;

    @BeforeEach
    public void setUp() {
        board = new Board(6, 7);
        scanner = new Scanner(System.in);
        game = new Game(scanner);
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    @Test
    public void testGetPlayerName() {
        String simulatedInput = "János\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            Scanner scanner = new Scanner(System.in);
            Game game = new Game(scanner);
            String playerName = game.getPlayerName();
            assertEquals("János", playerName, "A játékos nevét helyesen kellene visszaadni.");
        } finally {
            System.setIn(originalIn);
        }
    }
    @Test
    public void testSaveGameToFile() throws IOException {
        board.dropDisc(new HumanPlayer("Gyuri", "Sárga"), 0);
        board.dropDisc(new HumanPlayer("Gyuri", "Sárga"), 1);

        game.saveGameToFile(board);

        // Ellenőrizzük, hogy a fájl létrejött-e
        File file = new File(Game.FILE_PATH);
        assertTrue(file.exists(), "A játékállás fájlnak léteznie kellene.");

        // Ellenőrizzük a fájl tartalmát
        List<String> lines = Files.readAllLines(Paths.get(Game.FILE_PATH));
        assertFalse(lines.isEmpty(), "A fájl nem lehet üres.");
    }


    @Test
    public void testInvalidMoveHandling() {
        HumanPlayer player = new HumanPlayer("János", "Sárga");
        for (int i = 0; i < 6; i++) {
            board.dropDisc(player, 0); // Az oszlop megtelik
        }
        // Próbálunk még egy korongot bedobni ugyanabba az oszlopba
        assertFalse(board.dropDisc(player, 0), "Nem lehet korongot dobni egy teljesen megtelt oszlopba.");
    }

    @Test
    public void testLoadGameFromFileNoFile() {
        assertDoesNotThrow(() -> game.loadGameFromFile(board));
    }



    @Test
    public void testWinningConditionHorizontal() {
        HumanPlayer player = new HumanPlayer("Gyuri", "Sárga");
        board.dropDisc(player, 0);
        board.dropDisc(player, 1);
        board.dropDisc(player, 2);
        board.dropDisc(player, 3);

        assertTrue(board.checkForWin(player.getColor()), "A játékosnak nyernie kellene vízszintes vonallal.");
    }

    @Test
    public void testWinningConditionVertical() {
        HumanPlayer player = new HumanPlayer("Gyuri", "Sárga");
        board.dropDisc(player, 0);
        board.dropDisc(player, 0);
        board.dropDisc(player, 0);
        board.dropDisc(player, 0);

        assertTrue(board.checkForWin(player.getColor()), "A játékosnak nyernie kellene függőleges vonallal.");
    }

    @Test
    public void testWinningConditionDiagonal() {
        HumanPlayer player = new HumanPlayer("Gyuri", "Sárga");
        board.dropDisc(player, 0);
        board.dropDisc(player, 1);
        board.dropDisc(player, 1);
        board.dropDisc(player, 2);
        board.dropDisc(player, 2);
        board.dropDisc(player, 2);
        board.dropDisc(player, 3);

        assertTrue(board.checkForWin(player.getColor()), "A játékosnak nyernie kellene átlós vonallal.");
    }

    @Test
    public void testFullColumn() {
        HumanPlayer player = new HumanPlayer("Gyuri", "Sárga");
        for (int i = 0; i < 6; i++) {
            board.dropDisc(player, 0);
        }
        assertFalse(board.dropDisc(player, 0), "Nem lehet korongot dobni egy tele oszlopba.");
    }


}
