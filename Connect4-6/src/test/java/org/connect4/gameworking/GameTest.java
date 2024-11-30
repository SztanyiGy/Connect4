package org.connect4.gameworking;

import org.connect4.Main;

import org.connect4.player.HumanPlayer;
import org.connect4.player.Player;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameTest {

    @Test
    void testInitializePlayers() {
        // Given
        String input = "JátékosNév\n";
        InputReader inputReader = new InputReader(new ByteArrayInputStream(input.getBytes()));
        Game game = new Game(inputReader);

        // When
        game.initializePlayers();

        // Then
        Player humanPlayer = game.humanPlayer;
        assertEquals("JátékosNév", humanPlayer.getName());
        assertEquals("Sárga", humanPlayer.getColor());
    }

    @Test
    void testGetPlayerMove() {
        // Given
        String input = "3\n";
        InputReader inputReader = new InputReader(new ByteArrayInputStream(input.getBytes()));
        Game game = new Game(inputReader);
        HumanPlayer player = new HumanPlayer("Játékos", "Sárga");

        // When
        int move = game.getPlayerMove(player);

        // Then
        assertEquals(3, move); // Ellenőrizzük, hogy a bemenet helyesen értelmeződött
    }

    @Test
    void testPlayerSwitch() {
        // Given
        InputReader inputReader = new InputReader(new ByteArrayInputStream(new byte[0]));
        Game game = new Game(inputReader);

        // When
        Player initialPlayer = game.getCurrentPlayer();
        game.isHumanTurn = !game.isHumanTurn;
        Player switchedPlayer = game.getCurrentPlayer();

        // Then
        assertEquals(game.humanPlayer, initialPlayer); // Első játékos az ember
        assertEquals(game.computerPlayer, switchedPlayer); // Második a gép
    }

    @Test
    void testResetGame() {
        // Given
        InputReader inputReader = new InputReader(new ByteArrayInputStream(new byte[0]));
        Game game = new Game(inputReader);

        // When
        game.resetGame();

        // Then
        assertEquals(Main.ROWS, game.gameState.getBoard().getRows());
        assertEquals(Main.COLUMNS, game.gameState.getBoard().getColumns());
        assertTrue(game.isHumanTurn); // Az embernek kell kezdenie
    }

    @Test
    void testShowMenuWithExitOption() {
        // Given
        String input = "3\n"; // A felhasználó a kilépést választja
        InputReader inputReader = new InputReader(new ByteArrayInputStream(input.getBytes()));
        Game game = new Game(inputReader);

        // When
        game.showMenu();
    }

    @Test
    void testProcessWin() {
        // Mockolás
        BoardRenderer boardRenderer = mock(BoardRenderer.class);
        Player winner = mock(Player.class);
        when(winner.getName()).thenReturn("Játékos1");

        Game game = new Game(new InputReader(new ByteArrayInputStream(new byte[0])));
        game.boardRenderer = boardRenderer;

        // Metódus hívás
        game.processWin(winner);

        // Ellenőrzések
        verify(boardRenderer).render(any()); // Tábla kirajzolása
        verify(winner).getName(); // Győztes nevének lekérdezése
    }
}
