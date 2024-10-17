package org.connect4.game;

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
        Board board = new Board(6, 7);
        assertEquals(6, board.getRows());
        assertEquals(7, board.getColumns());
    }

    @Test
    public void testDropDisc() {
        Board board = new Board(6, 7);
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");
        assertTrue(board.dropDisc(player, 0));
    }

    @Test
    public void testCheckForWinVertical() {
        Board board = new Board(6, 7);
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");
        for (int i = 0; i < 4; i++) {
            board.dropDisc(player, 0);
        }
        assertTrue(board.checkForWin(player.getColor()));
    }

    @Test
    public void testCheckForWinHorizontal() {
        Board board = new Board(6, 7);
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");
        for (int i = 0; i < 4; i++) {
            board.dropDisc(player, i); // Sorban dobjuk a korongokat
        }
        assertTrue(board.checkForWin(player.getColor()));
    }

    @Test
    public void testCheckForWinDiagonalLeftToRight() {
        Board board = new Board(6, 7);
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");

        // Diagonális balról jobbra
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                board.dropDisc(new HumanPlayer("TestPlayer2", "Piros"), i);
            }
            board.dropDisc(player, i); // A Sárga játékos átlósan helyezi el a korongokat
        }

        assertTrue(board.checkForWin(player.getColor()));
    }

    @Test
    public void testCheckForWinDiagonalRightToLeft() {
        Board board = new Board(6, 7);
        HumanPlayer player = new HumanPlayer("TestPlayer", "Sárga");

        // Diagonális jobbról balra
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                board.dropDisc(new HumanPlayer("TestPlayer2", "Piros"), 3 - i);
            }
            board.dropDisc(player, 3 - i); // A Sárga játékos jobbról balra helyezi el a korongokat
        }

        assertTrue(board.checkForWin(player.getColor()));
    }

    @Test
    public void testInvalidColumn() {
        Board board = new Board(6, 7);
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

}

