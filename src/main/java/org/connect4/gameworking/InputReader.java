package org.connect4.gameworking;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Utility class for reading input from a specified input stream.
 */
public class InputReader {
    private final Scanner scanner;


    public InputReader(InputStream input) {
        this.scanner = new Scanner(input);
    }


    public String nextLine() {
        return scanner.nextLine();
    }
}
