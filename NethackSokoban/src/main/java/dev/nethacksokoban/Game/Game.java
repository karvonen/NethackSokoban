package dev.nethacksokoban.Game;

import dev.nethacksokoban.Util.InputScanner;
import java.awt.Point;
import java.util.HashMap;

public class Game {

    private InputScanner inputScanner;
    private Player player;
    private HashMap<Integer, Level> levels;
    private Level currentLevel;
    private boolean victory;
    private boolean quit;

    private char[][] testlevel2 = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '0', '@', '0', '^', '^', '#'},
        {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    private char[][] testlevel = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '<', '#', '@', '.', '.', '.', '#', '#', '#', '.', '.', '.', '.', '#'},
        {'#', '^', '#', '#', '.', '0', '0', '.', '.', '.', '.', '0', '.', '.', '#'},
        {'#', '^', '#', '#', '.', '.', '0', '0', '#', '.', '0', '.', '0', '.', '#'},
        {'#', '^', '#', '#', '.', '.', '.', '.', '#', '.', '.', '.', '.', '.', '#'},
        {'#', '^', '#', '#', '#', '#', '#', '#', '#', '0', '#', '#', '#', '#', '#'},
        {'#', '^', '#', '#', '#', '#', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '^', '#', '#', '#', '#', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '^', '^', '^', '^', '0', '0', '0', '0', '.', '.', '.', '#'},
        {'#', '.', '.', '#', '#', '#', '#', '#', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    public Game(InputScanner inputScanner) {
        this.inputScanner = inputScanner;
        this.levels = new HashMap<>();
    }

    public void startGame() {
        victory = false;
        quit = false;
        initialiseLevel();
        initialisePlayer();
        run();
    }

    private void run() {
        while (true) {
            updateConsole();
            if (victory) {
                System.out.println("Victory!");
                System.out.println("Moves used: " + player.getMoves());
                break;
            }
            if (quit) {
                break;
            }
            char command = inputScanner.readChar();
            executeGameCommand(command);
        }
    }

    private void initialisePlayer() {
        Point startingPosition = currentLevel.getPlayerStartingPosition();
        player = new Player(startingPosition.x, startingPosition.y);
    }

    private void initialiseLevel() {
        currentLevel = new Level(testlevel);
        for (int i = 0; i < currentLevel.getHeight(); i++) {
            for (int j = 0; j < currentLevel.getWidth(); j++) {
                if (currentLevel.getMap()[i][j] == '@') {
                    currentLevel.setPlayerStartingPosition(new Point(i, j));
                    currentLevel.eraseStartingMarker(i, j);
                }
                if (currentLevel.getMap()[i][j] == '0') {
                    currentLevel.addBox(new Box(j, i));
                    currentLevel.replaceBox(i, j);
                }
            }
        }
    }

    private void updateConsole() {
        for (int i = 0; i < currentLevel.getHeight(); i++) {
            for (int j = 0; j < currentLevel.getWidth(); j++) {
                if (player.getRow() == i && player.getCol() == j) {
                    System.out.print('@');
                } else if (currentLevel.getBoxInLocation(i, j) != null) {
                    System.out.print("0");
                } else {
                    System.out.print(currentLevel.getMap()[i][j]);
                }
            }
            System.out.println("");
        }
//        for (Box box : currentLevel.getBoxes()) {
//            System.out.println("Box, row: " + box.getRow() + " col: " + box.getCol());
//        }
//        System.out.println("Player, row: " + player.getRow() + " col: " + player.getCol());
    }

    private void executeGameCommand(char command) {
        int newRow = player.getRow();
        int newCol = player.getCol();
        int direction = 0;
        if (command == 'x') {
            quit = true;
        } else if (command == '1') {
            newCol--;
            newRow++;
        } else if (command == '2') {
            newRow++;
            direction = 2;
        } else if (command == '3') {
            newRow++;
            newCol++;
        } else if (command == '4') {
            newCol--;
            direction = 4;
        } else if (command == '6') {
            newCol++;
            direction = 6;
        } else if (command == '7') {
            newRow--;
            newCol--;
        } else if (command == '8') {
            newRow--;
            direction = 8;
        } else if (command == '9') {
            newRow--;
            newCol++;
        }
        if (newRow != player.getRow() || newCol != player.getCol()) {
            playerMove(newRow, newCol, direction);
        }
    }

    private void playerMove(int newRow, int newCol, int direction) {
//        System.out.println("trying to move to row: " + newRow + " / col: " + newCol + ", at that location: " + currentLevel.getMap()[newRow][newCol]);
        if (!checkVictory(newRow, newCol)) {
            if (currentLevel.getBoxInLocation(newRow, newCol) != null) {
                if (direction != 0) {
                    if (boxMove(newRow, newCol, direction)) {
                        player.move(newRow, newCol);
                    }
                }
            } else if (currentLevel.getMap()[newRow][newCol] == '.' || currentLevel.getMap()[newRow][newCol] == '*') {
                player.move(newRow, newCol);
            }
        }
    }

    private boolean boxMove(int playerNewRow, int playerNewCol, int direction) {
//        System.out.println("haetaan: row: " + playerNewRow + " col: " + playerNewCol);
//        for (Box box : currentLevel.getBoxes()) {
//            System.out.println("box: row:" + box.getRow() + " col: " + box.getCol());
//        }
        Box push = currentLevel.getBoxInLocation(playerNewRow, playerNewCol);

        int boxNewRow = playerNewRow;
        int boxNewCol = playerNewCol;
        if (direction == 2) {
            boxNewRow++;
        } else if (direction == 4) {
            boxNewCol--;
        } else if (direction == 6) {
            boxNewCol++;
        } else if (direction == 8) {
            boxNewRow--;
        }
        if (currentLevel.getMap()[boxNewRow][boxNewCol] == '^') {
            currentLevel.deleteBox(push);
            currentLevel.fillTrap(boxNewRow, boxNewCol);
            return true;
        } else if (currentLevel.getMap()[boxNewRow][boxNewCol] == '*' || currentLevel.getMap()[boxNewRow][boxNewCol] == '.') {
            push.setRow(boxNewRow);
            push.setCol(boxNewCol);
            return true;
        }
        return false;
    }

    private boolean checkVictory(int row, int col) {
        if (currentLevel.getMap()[row][col] == '<') {
            player.move(row, col);
            victory = true;
            return true;
        }
        return false;
    }
}
