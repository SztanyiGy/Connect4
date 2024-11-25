package org.connect4.gamelogic;

import org.connect4.model.Board;
import org.connect4.player.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WinCheckerTest {

    private Board board;
    private WinChecker winChecker;
    private HumanPlayer player1;
    private HumanPlayer player2;

    @BeforeEach
    public void setUp() {
        // Inicializáljunk egy 6x7-es táblát
        board = new Board(6, 7);
        winChecker = new WinChecker(board);
        player1 = new HumanPlayer("TestPlayer1", "Sárga");
        player2 = new HumanPlayer("TestPlayer2", "Piros");
    }

    @Test
    public void testCheckForWinVertical() {
        for (int i = 0; i < 4; i++) {
            board.dropDisc(player1, 0);
        }
        assertTrue(winChecker.checkForWin(player1.getColor()));
    }

    @Test
    public void testCheckForWinHorizontal() {
        for (int i = 0; i < 4; i++) {
            board.dropDisc(player1, i); // Sorban dobjuk a korongokat
        }
        assertTrue(winChecker.checkForWin(player1.getColor()));
    }

    @Test
    public void testNoWinOnEmptyBoard() {
        assertFalse(winChecker.checkForWin(player1.getColor()));
    }

    @Test
    public void testNoWinWhenLessThanFourDiscs() {
        for (int i = 0; i < 3; i++) {
            board.dropDisc(player1, i);
        }
        assertFalse(winChecker.checkForWin(player1.getColor()));
    }

    @Test
    public void testNoWinWithMixedColors() {
        // Létrehozunk egy mintát, ami nem ad győzelmet
        board.dropDisc(player1, 0);
        board.dropDisc(player2, 1);
        board.dropDisc(player1, 2);
        board.dropDisc(player2, 3);
        board.dropDisc(player1, 0);
        board.dropDisc(player2, 1); // Az utolsó lehelyezés után: 3 Sárga és 2 Piros, de nincs győztes
        assertFalse(winChecker.checkForWin(player1.getColor()));
        assertFalse(winChecker.checkForWin(player2.getColor()));
    }

    @Test
    public void testMultipleWinningConditions() {
        // Létrehozunk egy győzelmi mintát, amely több győzelmet is hozhat
        board.dropDisc(player1, 0);
        board.dropDisc(player1, 1);
        board.dropDisc(player1, 2);
        board.dropDisc(player1, 3); // Sárga nyer
        assertTrue(winChecker.checkForWin(player1.getColor()));
    }
}
