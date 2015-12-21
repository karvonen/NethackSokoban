package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.UI;
import dev.nethacksokoban.Util.InputScanner;
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
            ui = new UI(null, null, inputScanner);
            inputScanner.setUi(ui);
            victory = false;
            quit = false;
            Integer chosenLevelIndex = inputScanner.selectLevel(levels.size());
            if (chosenLevelIndex == 999) {
                break;
            }
            currentLevel = new Level(levels.get(chosenLevelIndex));
            initialisePlayer();
            ui.setPlayer(player);
            ui.setLevel(currentLevel);
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
        Location startingPosition = currentLevel.getPlayerStartingLocation();
        player = new Player(startingPosition);
    }

    private void executeGameCommand(char command) {
        Location newPlayerLocation = new Location(player.getRow(), player.getCol());
        int direction = 0;
        if (command == 'x') {
            quit = true;
        } else if (command == '1') {
            newPlayerLocation.setCol(player.getCol() - 1);
            newPlayerLocation.setRow(player.getRow() + 1);
        } else if (command == '2') {
            newPlayerLocation.setRow(player.getRow() + 1);
            direction = 2;
        } else if (command == '3') {
            newPlayerLocation.setRow(player.getRow() + 1);
            newPlayerLocation.setCol(player.getCol() + 1);
        } else if (command == '4') {
            newPlayerLocation.setCol(player.getCol() - 1);
            direction = 4;
        } else if (command == '6') {
            newPlayerLocation.setCol(player.getCol() + 1);
            direction = 6;
        } else if (command == '7') {
            newPlayerLocation.setRow(player.getRow() - 1);
            newPlayerLocation.setCol(player.getCol() - 1);
        } else if (command == '8') {
            newPlayerLocation.setRow(player.getRow() - 1);
            direction = 8;
        } else if (command == '9') {
            newPlayerLocation.setRow(player.getRow() - 1);
            newPlayerLocation.setCol(player.getCol() + 1);
        }
        if (newPlayerLocation != player.getLocation()) {
            attemptPlayerMove(newPlayerLocation, direction);
        }
    }

    private void attemptPlayerMove(Location newPlayerLocation, int direction) {
//        System.out.println("trying to move to row: " + newPlayerLocation.getRow() 
//                + " / col: " + newPlayerLocation.getCol() + ", at that location: " 
//                + currentLevel.getMap()[newPlayerLocation.getRow()][newPlayerLocation.getCol()]);

        if (!checkVictory(newPlayerLocation)) {
            if (currentLevel.getBoxInLocation(newPlayerLocation) != null) {
                if (direction != 0) {
                    if (boxAtLocation(newPlayerLocation, direction)) {
                        player.move(newPlayerLocation);
                    }
                }
            } else if (currentLevel.getCharFromLocation(newPlayerLocation) == '.'
                    || currentLevel.getCharFromLocation(newPlayerLocation) == '*') {
                player.move(newPlayerLocation);
            }
        }
    }

    private boolean boxAtLocation(Location location, int direction) {
//        System.out.println("haetaan: row: " + playerNewRow + " col: " + playerNewCol);
//        for (Box box : currentLevel.getBoxes()) {
//            System.out.println("box: row:" + box.getRow() + " col: " + box.getCol());
//        }
        Box push = currentLevel.getBoxInLocation(location);

        int boxNewRow = location.getRow();
        int boxNewCol = location.getCol();
        if (direction == 2) {
            boxNewRow++;
        } else if (direction == 4) {
            boxNewCol--;
        } else if (direction == 6) {
            boxNewCol++;
        } else if (direction == 8) {
            boxNewRow--;
        }
        return attemptBoxMove(new Location(boxNewRow, boxNewCol), push);
    }

    private boolean attemptBoxMove(Location newBoxLocation, Box push) {
        if (currentLevel.getBoxInLocation(newBoxLocation) == null) {
            if (currentLevel.getCharFromLocation(newBoxLocation) == '^') {
                currentLevel.deleteBox(push);
                currentLevel.fillTrap(newBoxLocation);
                return true;
            } else if (currentLevel.getCharFromLocation(newBoxLocation) == '*'
                    || currentLevel.getCharFromLocation(newBoxLocation) == '.') {
                push.setLocation(newBoxLocation);
                return true;
            }
        }
        return false;
    }

    private boolean checkVictory(Location location) {
        if (currentLevel.getCharFromLocation(location) == '<') {
            player.move(location);
            victory = true;
            return true;
        }
        return false;
    }
}
