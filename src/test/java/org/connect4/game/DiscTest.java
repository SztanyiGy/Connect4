package org.connect4.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiscTest {

    @Test
    public void testValidDiscCreationYellow() {
        // Test valid creation of a yellow disc
        Disc disc = new Disc("Sárga");
        assertEquals("Sárga", disc.getColor(), "Disc color should be 'Sárga'.");
    }

    @Test
    public void testValidDiscCreationRed() {
        // Test valid creation of a red disc
        Disc disc = new Disc("Piros");
        assertEquals("Piros", disc.getColor(), "Disc color should be 'Piros'.");
    }

    @Test
    public void testInvalidDiscCreation() {
        // Test invalid disc creation
        assertThrows(IllegalArgumentException.class, () -> {
            new Disc("Kék"); // Invalid color
        }, "Disc color must be 'Sárga' or 'Piros'.");
    }

    @Test
    public void testGetColor() {
        // Test getting the color of the disc
        Disc disc = new Disc("Sárga");
        assertEquals("Sárga", disc.getColor(), "getColor() should return the disc color.");
    }

    @Test
    public void testToString() {
        // Test the toString method
        Disc disc = new Disc("Piros");
        assertEquals("Piros", disc.toString(), "toString() should return the disc color.");
    }
}
