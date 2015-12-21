package dev.nethacksokoban.Game;

import java.util.ArrayList;

public class Level {

    private char[][] map;
    private Location playerStartingLocation;
    private ArrayList<Box> boxes;
    private int height;
    private int width;

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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    private void initialise() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (map[i][j] == '@') {
                    setPlayerStartingLocation(new Location(i, j));
                    replaceWithOpenSpot(i, j);
                }
                if (map[i][j] == '0') {
                    addBox(new Box(i, j));
                    replaceWithOpenSpot(i, j);
                }
            }
        }
    }

    public char getCharFromLocation(Location location) {
        return getMap()[location.getRow()][location.getCol()];
    }

    /**
     * Method searches for the right Box object which is in a certain row/col
     * location.
     *
     * @param row row of the box
     * @param col column of the box
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

    public void deleteBox(Box box) {
        boxes.remove(box);
    }

    public void setPlayerStartingLocation(Location location) {
        playerStartingLocation = location;
    }

    public Location getPlayerStartingLocation() {
        return playerStartingLocation;
    }

    public void replaceWithOpenSpot(int row, int col) {
//        System.out.println("replacing: " + row + "  " + col);
        map[row][col] = '.';
    }

    public char[][] getMap() {
        return map;
    }

    public void fillTrap(int row, int col) {
//        System.out.println("x: " + x + " / y: " + y);
        map[row][col] = '*';
    }
    
    public void fillTrap(Location location) {
        map[location.getRow()][location.getCol()] = '*';
    }

}
