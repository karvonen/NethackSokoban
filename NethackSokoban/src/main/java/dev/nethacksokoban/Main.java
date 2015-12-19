package dev.nethacksokoban;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.Util.InputScanner;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(new InputScanner(new Scanner(System.in)), true);
        game.startGame();
    }
}
