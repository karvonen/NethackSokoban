package dev.nethacksokoban.game;

import java.util.ArrayList;

/**
 * Class constructs and maintains a level of the game. Only one instance of this
 * class exists at one time and that is the currently being played level.
 */
public class Level {

    private char[][] map;
    private ArrayList<Box> boxes;
    private int height;
    private int width;
    private Player player;

    /**
     * All the information about the current map being played.
     *
     * @param map Character array of which the level is created form.
     */
    public Level(char[][] map) {
        //Array is copied so that it can be edited safely
        this.map = new char[map.length][map[0].length];
        for (int i = 0; i < this.map.length; i++) {
            System.arraycopy(map[i], 0, this.map[i], 0, map[i].length);
        }
        this.boxes = new ArrayList<>();
        this.height = map.length;
        this.width = map[0].length;
        initialise();
    }

    /**
     *
     * @return Player object of the level.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return Height of the level.
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return Width of the level.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return ArrayList of the boxes in the level.
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * Initializes the level by going through the character array which
     * represents the map. Creates the player object and box objects, then
     * replaces them in the level with a free tile.
     *
     */
    private void initialise() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (map[i][j] == '@') {
                    replaceWithOpenSpot(i, j);
                    player = new Player(new Location(i, j));
                }
                if (map[i][j] == '0') {
                    boxes.add(new Box(i, j));
                    replaceWithOpenSpot(i, j);
                }
            }
        }
    }

    /**
     * Method returns true if it's possible to move on to the tile which is
     * given as Location object parameter.
     *
     * @param location Location of the tile.
     *
     * @return Boolean for the tile being free to be moved on.
     */
    public boolean isTileFreeToBeMovedOn(Location location) {
        return (getTileFromLocation(location) == '.'
                || getTileFromLocation(location) == '*'
                || getTileFromLocation(location) == '<')
                && getBoxInLocation(location) == null;
    }

    public char getTileFromLocation(Location location) {
        return getMap()[location.getRow()][location.getCol()];
    }

    /**
     * Method searches for the right Box object which is in a certain row/col
     * location.
     *
     * @param row row where to search
     * @param col column where to search
     *
     * @return The box at parameters' location.
     */
    public Box getBoxInLocation(int row, int col) {
        for (Box box : boxes) {
            if (box.getRow() == row && box.getCol() == col) {
                return box;
            }
        }
        return null;
    }

    /**
     * Method searches for the right Box object which is in a certain Location
     *
     * @param location Location of the box in Location
     *
     * @return The box at parameters' location.
     */
    public Box getBoxInLocation(Location location) {
        for (Box box : boxes) {
            if (box.getRow() == location.getRow() && box.getCol() == location.getCol()) {
                return box;
            }
        }
        return null;
    }

    /**
     * Deletes a box from the level.
     *
     * @param box The box to be deleted.
     */
    public void deleteBox(Box box) {
        boxes.remove(box);
    }

    /**
     * Replaces given spot with a free tile. Used when constructing the level.
     *
     * @param row Row
     * @param col Column
     */
    public void replaceWithOpenSpot(int row, int col) {
        map[row][col] = '.';
    }

    /**
     *
     * @return Character array which represents the map of the level.
     */
    public char[][] getMap() {
        return map;
    }

    /**
     * Replaces given spot with a filled trap tile which can be moved on.
     *
     * @param location Location of the tile to to be changed.
     */
    public void fillTrap(Location location) {
        map[location.getRow()][location.getCol()] = '*';
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
        return (getTileFromLocation(location) == '<');
    }

}
