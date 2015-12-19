package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.UI;
import dev.nethacksokoban.Util.InputScanner;
import java.awt.Point;
import java.util.HashMap;

public class Game {

    private InputScanner inputScanner;
    private Player player;
    private HashMap<Integer, char[][]> levels;
    private Level currentLevel;
    private boolean victory;
    private boolean quit;
    private UI ui;
    private boolean testMode;

    private char[][] testlevel1 = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '0', '@', '0', '^', '^', '#'},
        {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    private char[][] testlevel2 = new char[][]{
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

    private char[][] testLevel999 = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '@', '.', '.', '.', '#', '#', '.', '#'},
        {'#', '.', '0', '.', '0', '.', '^', '^', '.', '#'},
        {'#', '0', '.', '.', '.', '.', '#', '#', '<', '#'},
        {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    public Game(InputScanner inputScanner, boolean testMode) {
        this.inputScanner = inputScanner;
        this.levels = new HashMap<>();
        this.testMode = testMode;
    }

    public void startGame() {
        while (true) {
            resetLevels();
            victory = false;
            quit = false;
            Integer chosenLevelIndex = inputScanner.selectLevel(levels.size());
            if (chosenLevelIndex == 999) {
                break;
            }
            currentLevel = new Level(levels.get(chosenLevelIndex));
            initialisePlayer();
            ui = new UI(currentLevel, player, inputScanner);
            run();
        }
    }

    private void resetLevels() {
        levels.clear();
        if (testMode) {
            levels.put(1, testLevel999);
        } else {
            levels.put(1, testlevel1);
            levels.put(2, testlevel2);
        }
    }

    private void run() {
        while (true) {
            if (quit) {
                break;
            }
            ui.update();
            if (victory) {
                ui.victory();
                break;
            }
            executeGameCommand(ui.readCommand());
        }
    }

    private void initialisePlayer() {
        Point startingPosition = currentLevel.getPlayerStartingPosition();
        player = new Player(startingPosition.x, startingPosition.y);
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
                    if (boxAtLocation(newRow, newCol, direction)) {
                        player.move(newRow, newCol);
                    }
                }
            } else if (currentLevel.getMap()[newRow][newCol] == '.' || currentLevel.getMap()[newRow][newCol] == '*') {
                player.move(newRow, newCol);
            }
        }
    }

    private boolean boxAtLocation(int playerNewRow, int playerNewCol, int direction) {
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
        return moveBox(boxNewRow, boxNewCol, push);
    }

    private boolean moveBox(int boxNewRow, int boxNewCol, Box push) {
        if (currentLevel.getBoxInLocation(boxNewRow, boxNewCol) == null) {
            if (currentLevel.getMap()[boxNewRow][boxNewCol] == '^') {
                currentLevel.deleteBox(push);
                currentLevel.fillTrap(boxNewRow, boxNewCol);
                return true;
            } else if (currentLevel.getMap()[boxNewRow][boxNewCol] == '*'
                    || currentLevel.getMap()[boxNewRow][boxNewCol] == '.') {
                push.setRow(boxNewRow);
                push.setCol(boxNewCol);
                return true;
            }
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
