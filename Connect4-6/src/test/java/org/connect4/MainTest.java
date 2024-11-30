package org.connect4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void testPrivateConstructor() {
        // Ellenőrizzük, hogy a konstruktor kivételt dob
        assertThrows(UnsupportedOperationException.class, () -> {
            // Ez a sor megpróbálja példányosítani a Main osztályt
            Main mainInstance = new Main();
        });
    }
}
