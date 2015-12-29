package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.GUI;
import dev.nethacksokoban.UI.UI;
import dev.nethacksokoban.Util.FileScanner;
import dev.nethacksokoban.Util.InputScanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private InputScanner inputScanner;
    private HashMap<Integer, char[][]> levels;
    private Level level;
    private boolean victory;
    private boolean quit;
    private UI ui;
    private boolean testMode;
    private GUI gui;

    private char[][] testLevel1 = new char[][]{
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
        //For tests, removed when game is started properly.
        this.levels.put(1, testLevel1);

    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void startGame() {
        loadLevels();
        ui = new UI(inputScanner);
        inputScanner.setUi(ui);
        victory = false;
        quit = false;
        if (testMode) {
            while (true) {
                victory = false;
                quit = false;
                Integer chosenLevelIndex = inputScanner.selectLevel(levels.size());
                if (chosenLevelIndex == 999) {
                    System.exit(0);
                    break;
                }
                chooseLevel(chosenLevelIndex);
                runInTestMode();

            }
        } else {
            gui.addMenuPanel();
        }
    }

    public void run() {
        victory = false;
        quit = false;
        update();
    }

    public void startNewMapWithIndex(int index) {
        level = new Level(levels.get(index));
        run();
    }

    public void chooseLevel(int chosenLevelIndex) {
        level = new Level(levels.get(chosenLevelIndex));
    }

    public HashMap<Integer, char[][]> getLevels() {
        return levels;
    }

    public void loadLevels() {
        levels.clear();

        FileScanner levelLoader = new FileScanner();
        ArrayList<char[][]> loadedMaps = levelLoader.loadMaps();
        for (int i = 0; i < loadedMaps.size(); i++) {
            levels.put(i + 1, loadedMaps.get(i));
        }

    }

    public void runInTestMode() {
        ui.update(level);
        while (true) {
            if (quit) {
                break;
            }
            if (victory) {
                ui.victory(level);
                break;
            }
            if (testMode) {
                executeGameCommand(ui.readCommand());
                ui.update(level);
            }
        }
        startGame();
    }

    public void update() {
        if (victory) {
            gui.getUpdatable().reDraw();
            ui.victory(level);
            startGame();
        } else if (quit) {
            startGame();
        } else {
            gui.getUpdatable().reDraw();
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
        checkVictory(newPlayerLocation);
        Box boxAtNewLocation = level.getBoxInLocation(newPlayerLocation);
        if (boxAtNewLocation != null) {
            if (direction != 0) {
                Location newBoxLocation = createNewBoxLocation(boxAtNewLocation, direction);
                if (attemptBoxMove(newBoxLocation, boxAtNewLocation)) {
                    level.getPlayer().move(newPlayerLocation);
                }
            }
        } else if (level.isTileFreeToBeMovedOn(newPlayerLocation)) {
            level.getPlayer().move(newPlayerLocation);
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

    public void checkVictory(Location location) {
        if (level.getTileFromLocation(location) == '<') {
            victory = true;
        }
    }

    //Helper method for testing
    public Level getLevel() {
        return level;
    }
}
