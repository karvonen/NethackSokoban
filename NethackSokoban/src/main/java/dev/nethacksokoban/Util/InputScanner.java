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

    public int selectLevel(int max) {
        int input = 0;
        do {
            System.out.println("Select level between 1 and " + max);
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.println("Select level between 1 and " + max);
            }
            input = scanner.nextInt();
        } while (input < 1 || input > max);

        return input;
    }
}
