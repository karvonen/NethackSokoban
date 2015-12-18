package dev.nethacksokoban.Util;

import java.util.Scanner;
/**
 * Class reads user input with Scanner(System.in)
 */
public class InputScanner {

    private Scanner scanner;

    public InputScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Method asks user for input until it gets one that is not empty or first
     * char is not ' '. 
     *
     * @return returns first char of an accepted input
     */
    public char readChar() {
        String line = "";
        while (line.isEmpty() || line.charAt(0) == ' ') {
            line = scanner.nextLine();
        }
        return line.charAt(0);
    }

    /**
     * Method asks user for input and until it is given an integer between
     * 1 and parameter max
     *
     * @param max upperbound (inclusive) of integer to accept
     *
     * @return returns input given by user
     */
    public int selectLevel(int max) {
        int input = 0;
        do {
            if (input == 999) {
                return 999;
            }
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
