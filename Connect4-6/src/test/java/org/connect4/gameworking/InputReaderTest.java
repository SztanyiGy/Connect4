package org.connect4.gameworking;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputReaderTest {

    @Test
    void testNextLine() {
        // Given
        String input = "Teszt bemenet\n";
        InputReader inputReader = new InputReader(new ByteArrayInputStream(input.getBytes()));

        // When
        String result = inputReader.nextLine();

        // Then
        assertEquals("Teszt bemenet", result);
    }
}