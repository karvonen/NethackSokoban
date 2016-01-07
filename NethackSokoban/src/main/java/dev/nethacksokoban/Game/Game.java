package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.GUI;
import dev.nethacksokoban.Util.FileScanner;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class has methods for starting and playing the game. There is no game loop,
 * and instead the game works in an event driven way so the methods of this
 * class are called when a key is pressed for example.
 *
 */
public class Game {

    private HashMap<Integer, char[][]> levels;
    private Level level;
    private boolean victory;
    private GUI gui;

    private char[][] testLevel1 = new char[][]{
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', '.', '@', '.', '.', '.', '#', '#', '.', '#'},
        {'#', '.', '0', '.', '0', '.', '^', '^', '.', '#'},
        {'#', '0', '.', '.', '.', '.', '#', '#', '<', '#'},
        {'#', '.', '.', '.', '.', '.', '#', '#', '.', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

    /**
     * The constructor is called without parameters and it sets a test level as
     * the only existing level at creation.
     *
     */
    public Game() {
        this.levels = new HashMap<>();
        this.levels.put(1, testLevel1);
    }

    /**
     * @param gui Attaches a GUI to the game.
     *
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * Sets up the levels and starts the game.
     *
     */
    public void startGame() {
        loadLevels();
        try {
            gui.addMenuPanel();
        } catch (InterruptedException ex) {
            System.exit(1);
        }
        victory = false;
    }

    /**
     * Method creates and sets the level to be played and then starts a new
     * game.
     *
     * @param index Key of the chosen level in Levels HashMap.
     */
    public void startNewMapWithIndex(int index) {
        level = new Level(levels.get(index));
        victory = false;
        gui.getUpdatable().setup();
        update();
    }

    /**
     * Method creates and sets the level to be played.
     *
     * @param chosenLevelIndex HashMap key which is the index of the level.
     *
     */
    public void createCurrentLevel(int chosenLevelIndex) {
        level = new Level(levels.get(chosenLevelIndex));
    }

    public HashMap<Integer, char[][]> getLevels() {
        return levels;
    }

    /**
     * Method clears HashMap<Integer, char[][]> levels and uses FileScanner to
     * load all levels from files.
     *
     * @see FileScanner
     */
    public void loadLevels() {
        levels.clear();

        FileScanner levelLoader = new FileScanner();
        ArrayList<char[][]> loadedMaps = levelLoader.loadMaps();
        for (int i = 0; i < loadedMaps.size(); i++) {
            levels.put(i + 1, loadedMaps.get(i));
        }
    }

    /**
     * Updates the UI and stops + restarts the game if the player has won.
     *
     * @see GUI
     */
    public void update() {
        if (victory) {
            gui.getUpdatable().reDraw();
            gui.victoryDialog();
            startGame();
        }
        gui.getUpdatable().reDraw();
        gui.updateMenuMoveCount();
    }

    /**
     * Method executes a movement command that is given as a char. The chars are
     * directions as seen on normal computer keyboard's numpad.
     *
     * @param command char that is interpreted as a direction.
     */
    public void executeGameCommand(char command) {
        Location newPlayerLocation = new Location(level.getPlayer().getLocation());
        int direction = 0;
        if (command == '1') {
            newPlayerLocation.setCol(newPlayerLocation.getCol() - 1);
            newPlayerLocation.setRow(newPlayerLocation.getRow() + 1);
            direction = 1;
        } else if (command == '2') {
            newPlayerLocation.setRow(newPlayerLocation.getRow() + 1);
            direction = 2;
        } else if (command == '3') {
            newPlayerLocation.setRow(newPlayerLocation.getRow() + 1);
            newPlayerLocation.setCol(newPlayerLocation.getCol() + 1);
            direction = 3;
        } else if (command == '4') {
            newPlayerLocation.setCol(newPlayerLocation.getCol() - 1);
            direction = 4;
        } else if (command == '6') {
            newPlayerLocation.setCol(newPlayerLocation.getCol() + 1);
            direction = 6;
        } else if (command == '7') {
            newPlayerLocation.setRow(newPlayerLocation.getRow() - 1);
            newPlayerLocation.setCol(newPlayerLocation.getCol() - 1);
            direction = 7;
        } else if (command == '8') {
            newPlayerLocation.setRow(newPlayerLocation.getRow() - 1);
            direction = 8;
        } else if (command == '9') {
            newPlayerLocation.setRow(newPlayerLocation.getRow() - 1);
            newPlayerLocation.setCol(newPlayerLocation.getCol() + 1);
            direction = 9;
        }

        if (newPlayerLocation != level.getPlayer().getLocation()) {
            attemptPlayerMove(newPlayerLocation, direction);
        }
    }

    /**
     * Method creates and returns a new Location object according to the
     * direction the box is being moved.
     *
     * @param box The box which new location is being created.
     * @param direction Integer value of 4 cardinal movement directions.
     *
     * @return Location-object for the new box location.
     */
    public Location createNewBoxLocation(Box box, int direction) {
        Location newBoxLocation = new Location(box.getLocation());
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

    /**
     * Method attempts to move player towards a new Location object. Direction
     * is given as a parameter to be used to move box if the new player location
     * is occupied by a box. If box is found at the new location, then
     * attemptToMoveBox is called.
     *
     * @param newPlayerLocation New coordinates of the player.
     * @param direction Integer value of movement direction.
     *
     */
    public void attemptPlayerMove(Location newPlayerLocation, int direction) {
        victory = level.checkVictory(newPlayerLocation);
        Box boxAtNewLocation = level.getBoxInLocation(newPlayerLocation);
        if (checkDiagonal(direction)) {
            if (boxAtNewLocation != null) {
                if (direction != 0) {
                    Location newBoxLocation = createNewBoxLocation(boxAtNewLocation, direction);
                    if (attemptBoxMove(newBoxLocation, boxAtNewLocation)) {
                        level.getPlayer().setPlayerLocation(newPlayerLocation);
                    }
                }
            } else if (level.isTileFreeToBeMovedOn(newPlayerLocation)) {
                level.getPlayer().setPlayerLocation(newPlayerLocation);
            }
        }
    }

    /**
     * Method checks if diagonal movement is possible. Also returns true if
     * movement is not diagonal.
     *
     * @param direction Integer value of movement direction.
     *
     * @return boolean whether move is allowed.
     */
    public boolean checkDiagonal(int direction) {
        if (direction == 1 || direction == 3 || direction == 7 || direction == 9) {
            Location verticalAxis = new Location(level.getPlayer().getLocation());
            Location horizontalAxis = new Location(level.getPlayer().getLocation());
            if (direction == 1) {
                verticalAxis.setRow(verticalAxis.getRow() + 1);
                horizontalAxis.setCol(verticalAxis.getCol() - 1);
            } else if (direction == 3) {
                verticalAxis.setRow(verticalAxis.getRow() + 1);
                horizontalAxis.setCol(verticalAxis.getCol() + 1);
            } else if (direction == 7) {
                verticalAxis.setRow(verticalAxis.getRow() - 1);
                horizontalAxis.setCol(verticalAxis.getCol() - 1);
            } else if (direction == 9) {
                verticalAxis.setRow(verticalAxis.getRow() - 1);
                horizontalAxis.setCol(verticalAxis.getCol() + 1);
            }
            return (level.isTileFreeToBeMovedOn(horizontalAxis) || level.isTileFreeToBeMovedOn(verticalAxis));
        }
        return true;
    }

    /**
     * Method attempts to move a box to a Location. If the new Location contains
     * a trap then the box is deleted and the trap is filled. If the Location is
     * filled or free then the box is moved on to it.
     *
     * @param newBoxLocation Location object of the coordinate where box is
     * being moved to.
     * @param push Box object of the box being moved.
     *
     * @return boolean of the success of the box move.
     */
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

    /**
     * @return Currently active level.
     *
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Method to help with testing.
     *
     * @param level Set active level.
     *
     */
    public void setLevel(Level level) {
        this.level = level;
    }
}
