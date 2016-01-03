package dev.nethacksokoban.Game;

import dev.nethacksokoban.UI.GUI;
import dev.nethacksokoban.Util.FileScanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

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
     * The constructor is called without parameters and it sets a test level
     * as the only existing level at creation.
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
    public void loadLevelsAndStartGame() {
        loadLevels();
        try {
            gui.addMenuPanel();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

    public void update() {
        if (victory) {
            gui.getUpdatable().reDraw();
            gui.victoryDialog();
            loadLevelsAndStartGame();
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
        Location newPlayerLoc = new Location(level.getPlayer().getRow(), level.getPlayer().getCol());
        int direction = 0;
        if (command == '1') {
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

    /**
     * Method attempts to move player towards a new Location object. Direction
     * is given as a parameter to be used to move box if the new player location
     * is occupied by a box. If box is found at the new location, then
     * attemptToMoveBox is called.
     *
     * @param newPlayerLocation New coordinates of the player.
     * @param direction Integer value of 4 cardinal movement directions.
     *
     */
    public void attemptPlayerMove(Location newPlayerLocation, int direction) {
        checkVictory(newPlayerLocation);
        Box boxAtNewLocation = level.getBoxInLocation(newPlayerLocation);
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
     * Method checks if a Location has a victory tile and then sets victory to
     * true.
     *
     * @param location Location object for the location where the check is done.
     *
     * @return boolean for victory condition.
     */
    public boolean checkVictory(Location location) {
        if (level.getTileFromLocation(location) == '<') {
            victory = true;
            return true;
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
}
