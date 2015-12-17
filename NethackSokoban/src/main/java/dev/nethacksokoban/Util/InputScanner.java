package dev.nethacksokoban.Util;

import java.util.Scanner;

public class InputScanner {

    private Scanner scanner;

    public InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public char readChar() {
        String line = "";
        while (line.isEmpty()) {
            line = scanner.nextLine();
        }
        return line.charAt(0);
    }

}
