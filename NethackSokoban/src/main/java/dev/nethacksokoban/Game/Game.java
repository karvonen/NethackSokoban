package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.UI;
import dev.nethacksokoban.Util.InputScanner;
import java.util.HashMap;

public class Game {

    private InputScanner inputScanner;
    private HashMap<Integer, char[][]> levels;
    private Level level;
    private boolean victory;
    private boolean quit;
    private UI ui;
    private boolean testMode;

    private char[][] testLevel1 = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '.', '.', '.', '.', '.', '.', '.', '#'},
        {'#', '.', '.', '.', '0', '@', '0', '^', '^', '#'},
        {'#', '.', '.', '.', '.', '.', '#', '#', '<', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    private char[][] testLevel2 = new char[][]{
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
            ui = new UI(inputScanner);
            inputScanner.setUi(ui);
            victory = false;
            quit = false;
            Integer chosenLevelIndex = inputScanner.selectLevel(levels.size());
            if (chosenLevelIndex == 999) {
                break;
            }
            level = new Level(levels.get(chosenLevelIndex));
            run();
        }
    }

    public void resetLevels() {
        levels.clear();
        if (testMode) {
            levels.put(1, testLevel999);
        } else {
            levels.put(1, testLevel1);
            levels.put(2, testLevel2);
        }
    }

    public void run() {
        while (true) {
            if (quit) {
                break;
            }
            ui.update(level);
            if (victory) {
                ui.victory(level);
                break;
            }
            executeGameCommand(ui.readCommand());
        }
    }

    public void executeGameCommand(char command) {
        Location newPlayerLoc = new Location(level.getPlayer().getRow(), level.getPlayer().getCol());
        int direction = 0;
        if (command == 'x') {
            quit = true;
        } else if (command == '1') {
            newPlayerLoc.setCol(newPlayerLoc.getCol() - 1);
            newPlayerLoc.setRow(newPlayerLoc.getRow() + 1);
        } else if (command == '2') {
            newPlayerLoc.setRow(newPlayerLoc.getRow() + 1);
            direction = 2;
        } else if (command == '3') {
            newPlayerLoc.setRow(newPlayerLoc.getRow() + 1);
            newPlayerLoc.setCol(newPlayerLoc.getCol() + 1);
        } else if (command == '4') {
            newPlayerLoc.setCol(newPlayerLoc.getCol() - 1);
            direction = 4;
        } else if (command == '6') {
            newPlayerLoc.setCol(newPlayerLoc.getCol() + 1);
            direction = 6;
        } else if (command == '7') {
            newPlayerLoc.setRow(newPlayerLoc.getRow() - 1);
            newPlayerLoc.setCol(newPlayerLoc.getCol() - 1);
        } else if (command == '8') {
            newPlayerLoc.setRow(newPlayerLoc.getRow() - 1);
            direction = 8;
        } else if (command == '9') {
            newPlayerLoc.setRow(newPlayerLoc.getRow() - 1);
            newPlayerLoc.setCol(newPlayerLoc.getCol() + 1);
        }
        if (newPlayerLoc != level.getPlayer().getLocation()) {
            attemptPlayerMove(newPlayerLoc, direction);
        }
    }

    public Location createNewBoxLocation(Box box, int direction) {
        Location newBoxLocation = new Location(box.getRow(), box.getCol());
        if (direction == 2) {
            newBoxLocation.setRow(newBoxLocation.getRow() + 1);
        } else if (direction == 4) {
            newBoxLocation.setCol(newBoxLocation.getCol() - 1);
        } else if (direction == 6) {
            newBoxLocation.setCol(newBoxLocation.getCol() + 1);
        } else if (direction == 8) {
            newBoxLocation.setRow(newBoxLocation.getRow() - 1);
        }
        return newBoxLocation;
    }

    public void attemptPlayerMove(Location newPlayerLocation, int direction) {
        if (!checkVictory(newPlayerLocation)) {
            if (level.getBoxInLocation(newPlayerLocation) != null) {
                if (direction != 0) {
                    Location newBoxLocation = createNewBoxLocation(level.getBoxInLocation(newPlayerLocation), direction);
                    if (attemptBoxMove(newBoxLocation, level.getBoxInLocation(newPlayerLocation))) {
                        level.getPlayer().move(newPlayerLocation);
                    }
                }
            } else if (level.getTileFromLocation(newPlayerLocation) == '.'
                    || level.getTileFromLocation(newPlayerLocation) == '*') {
                level.getPlayer().move(newPlayerLocation);
            }
        }
    }

    public boolean attemptBoxMove(Location newBoxLocation, Box push) {
        if (level.getBoxInLocation(newBoxLocation) == null) {
            if (level.getTileFromLocation(newBoxLocation) == '^') {
                level.deleteBox(push);
                level.fillTrap(newBoxLocation);
                return true;
            } else if (level.getTileFromLocation(newBoxLocation) == '*'
                    || level.getTileFromLocation(newBoxLocation) == '.') {
                push.setLocation(newBoxLocation);
                return true;
            }
        }
        return false;
    }

    public boolean checkVictory(Location location) {
        if (level.getTileFromLocation(location) == '<') {
            level.getPlayer().move(location);
            victory = true;
            return true;
        }
        return false;
    }
}
