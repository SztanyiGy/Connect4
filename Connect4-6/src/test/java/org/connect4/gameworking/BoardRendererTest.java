package org.connect4.gameworking;

import org.connect4.model.Board;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardRendererTest {

    @Test
    void testRender() {
        // Given
        Board board = new Board(6, 7); // Üres tábla
        BoardRenderer renderer = new BoardRenderer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // When
        renderer.render(board);

        // Then
        assertEquals(board.toString(), outputStream.toString());
    }
}
