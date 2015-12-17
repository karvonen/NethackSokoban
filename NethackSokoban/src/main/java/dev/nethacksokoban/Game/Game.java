package dev.nethacksokoban.Game;

import dev.nethacksokoban.Util.InputScanner;
import java.awt.Point;
import java.util.ArrayList;

public class Game {

    private InputScanner inputScanner;
    private Player player;
    private ArrayList<Level> levels;
    private Level currentLevel;

    private char[][] testlevel = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '#', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '@', '.', '.', '.', '#', '#', '.', '#'},
        {'#', '.', '.', '.', '#', '.', '#', '#', '.', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    public Game(InputScanner inputScanner) {
        this.inputScanner = inputScanner;
    }

    public void startGame() {

        initialiseLevel();
        initialisePlayer();
        run();
    }

    private void run() {
        while (true) {
            updateConsole();
            char command = inputScanner.readChar();
            execute(command);
        }

    }

    private void initialisePlayer() {
        Point startingPosition = currentLevel.getPlayerStartingPosition();
        player = new Player(startingPosition.x, startingPosition.y);
    }

    private void initialiseLevel() {

        currentLevel = new Level(testlevel);
        boolean validStartingPosition = false;
        for (int i = 0; i < currentLevel.getMap().length; i++) {
            for (int j = 0; j < currentLevel.getMap()[i].length; j++) {
                if (currentLevel.getMap()[i][j] == '@') {
                    currentLevel.setPlayerStartingPosition(new Point(i, j));
                    validStartingPosition = true;
                    currentLevel.eraseStartingMarker(i, j);
                }
            }
        }
        if (!validStartingPosition) {
            //do something here
        }

    }

    private void updateConsole() {
        for (int i = 0; i < currentLevel.getMap().length; i++) {
            for (int j = 0; j < currentLevel.getMap()[i].length; j++) {
                if (player.getX() == j && player.getY() == i) {
                    System.out.print('@');
                } else {
                    System.out.print(currentLevel.getMap()[i][j]);
                }
            }
            System.out.println("");
        }
    }

    private void execute(char command) {
        int newX = player.getX();
        int newY = player.getY();
        if (command == '1') {
            newY++;
            newX--;
        } else if (command == '2') {
            newY++;
        } else if (command == '3') {
            newX++;
            newY++;
        } else if (command == '4') {
            newX--;
        } else if (command == '6') {
            newX++;
        } else if (command == '7') {
            newX--;
            newY--;
        } else if (command == '8') {
            newY--;
        } else if (command == '9') {
            newX++;
            newY--;
        }

        playerMove(newX, newY);

    }

    private void playerMove(int newX, int newY) {
        System.out.println("trying to move to : " + newX + " / " + newY + ", at that location: " + currentLevel.getMap()[newY][newX]);
        if (currentLevel.getMap()[newY][newX] == '.') {
            player.move(newX, newY);
        }
    }

}
