package org.connect4.gamelogic;

import org.connect4.model.Board;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    private static final String TEST_FILE_PATH = "test_game_state.txt";
    private FileManager fileManager;
    private Board testBoard;

    @BeforeEach
    void setUp() {
        fileManager = new FileManager();
        testBoard = new Board(6, 7); // Például egy 6x7-es tábla
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH)); // Töröljük a fájlt, ha létezik
        } catch (IOException e) {
            System.out.println("Hiba a fájl törlésénél: " + e.getMessage());
        }
    }


    @Test
    void testLoadGameFromFile() {
        // Given
        fileManager.saveGameToFile(testBoard);

        // When
        Board loadedBoard = new Board(6, 7);
        fileManager.loadGameFromFile(loadedBoard);

        // Then
        assertNotNull(loadedBoard, "A betöltött tábla nem lehet null.");
        // Itt további validációk is szükségesek lehetnek, ha a loadGameFromFile logika be van fejezve
    }

    @Test
    void testLoadGameFromFile_NoFile() {
        // Given
        // Nem mentünk semmit a fájlba

        // When
        Board loadedBoard = new Board(6, 7);
        fileManager.loadGameFromFile(loadedBoard);

        // Then
        assertNotNull(loadedBoard, "A tábla nem lehet null.");
        // A fájl nem létezett, tehát a loadGameFromFile nem módosítja a tábla állapotát
        // Itt esetleg valami konkrét ellenőrzés is történhet, ha van implementálva a betöltési logika
    }
}
