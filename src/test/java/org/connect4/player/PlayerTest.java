package org.connect4.player;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testHumanPlayerCreation() {
        HumanPlayer player = new HumanPlayer("Gyuri", "Sárga");
        assertEquals("Gyuri", player.getName());
        assertEquals("Sárga", player.getColor());
    }

    @Test
    public void testComputerPlayerCreation() {
        ComputerPlayer player = new ComputerPlayer("AI", "Piros");
        assertEquals("AI", player.getName());
        assertEquals("Piros", player.getColor());
    }

    @Test
    public void testInvalidPlayerColor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HumanPlayer("Gyuri", "Zöld"); // Hibás szín
        });

        String expectedMessage = "A színek 'Sárga' vagy 'Piros' lehet.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testComputerPlayerInvalidColor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ComputerPlayer("AI", "Kék"); // Hibás szín
        });

        String expectedMessage = "A színnek 'Sárga' vagy 'Piros'-nak kell lennie.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testComputerPlayerMakeMove() {
        ComputerPlayer player = new ComputerPlayer("Gép", "Sárga");
        int column = player.makeMove(7); // Példa maximális oszlopszám
        assertTrue(column >= 0 && column < 7, "A választott oszlopnak 0 és 6 között kell lennie.");
    }

    @Test
    public void testHumanPlayerMakeMove() {
        HumanPlayer player = new HumanPlayer("Gyuri", "Piros");

        // Szimuláljuk a bemenetet
        String input = "3\n"; // A játékos választása
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        int column = player.makeMove(7); // Példa maximális oszlopszám
        assertEquals(3, column, "A választott oszlopnak 3-nak kell lennie.");
    }
}
