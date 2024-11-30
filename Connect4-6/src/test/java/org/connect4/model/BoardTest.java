package org.connect4.model;

import org.connect4.player.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        // Inicializáljunk egy 6x7-es táblát
        board = new Board(6, 7);
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(6, board.getRows());
        assertEquals(7, board.getColumns());
    }

    @Test
    public void testDropDisc() {
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");
        assertTrue(board.dropDisc(player, 0));
    }

    @Test
    public void testInvalidColumn() {
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");

        // Teszteljük, hogy dob-e kivételt, ha érvénytelen oszlopot adunk meg
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.dropDisc(player, 8); // Nem létező oszlop
        });

        String expectedMessage = "Érvénytelen oszlop.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInvalidBoardSize() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Board(3, 3); // Túl kicsi tábla
        });

        String expectedMessage = "A táblaméretnek 4 és 12 között kell lennie.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInvalidBoardInitialization() {
        assertThrows(IllegalArgumentException.class, () -> new Board(3, 7));
        assertThrows(IllegalArgumentException.class, () -> new Board(6, 13));
    }

    @Test
    public void testGetGrid() {
        // Ellenőrizzük, hogy a grid mérete helyes
        Disc[][] grid = board.getGrid();
        assertEquals(6, grid.length); // Sorok száma
        assertEquals(7, grid[0].length); // Oszlopok száma

        // Ellenőrizzük, hogy az összes mező null
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                assertNull(grid[row][col], "A(z) [" + row + "][" + col + "] mező nem üres.");
            }
        }
    }

    @Test
    void testDropDiscColumnFull() {
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");
        for (int i = 0; i < 6; i++) {
            board.dropDisc(player, 0); // Töltsd fel az oszlopot
        }
        assertFalse(board.dropDisc(player, 0)); // Oszlop tele van
    }

    @Test
    void testGetDiscColor() {
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");
        board.dropDisc(player, 0);
        assertEquals("Sárga", board.getDiscColor(5, 0)); // Meg kell kapni a korong színét
        assertNull(board.getDiscColor(0, 0)); // Üres mező
    }

    @Test
    public void testToString() {
        Board board = new Board(6, 7);
        String expected = "- - - - - - - \n" + // Üres mezők
                "- - - - - - - \n" +
                "- - - - - - - \n" +
                "- - - - - - - \n" +
                "- - - - - - - \n" +
                "- - - - - - - \n"; // Az elvárt kimenet
        assertEquals(expected, board.toString());
    }
}
