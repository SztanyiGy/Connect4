package org.connect4.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerTest {

    private HumanPlayer mockHumanPlayer;
    private ComputerPlayer mockComputerPlayer;

    @BeforeEach
    public void setUp() {
        // Mock objektumok létrehozása
        mockHumanPlayer = mock(HumanPlayer.class);
        mockComputerPlayer = mock(ComputerPlayer.class);
    }

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
        // Mockolt metódus viselkedés definiálása
        when(mockComputerPlayer.makeMove(7)).thenReturn(3);

        int column = mockComputerPlayer.makeMove(7);
        assertEquals(3, column, "A választott oszlopnak 3-nak kell lennie.");

        // Ellenőrzés, hogy meghívtuk-e a metódust
        verify(mockComputerPlayer).makeMove(7);
    }

    @Test
    public void testHumanPlayerMakeMove() {
        // Mockolt metódus viselkedés definiálása
        when(mockHumanPlayer.makeMove(7)).thenReturn(4);

        int column = mockHumanPlayer.makeMove(7);
        assertEquals(4, column, "A választott oszlopnak 4-nek kell lennie.");

        // Ellenőrzés, hogy meghívtuk-e a metódust
        verify(mockHumanPlayer).makeMove(7);
    }
}
