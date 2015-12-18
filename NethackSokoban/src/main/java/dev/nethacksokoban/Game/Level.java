package dev.nethacksokoban.Game;

import java.awt.Point;
import java.util.ArrayList;

public class Level {

    private char[][] map;
    private Point PlayerStartingPosition;
    private ArrayList<Box> boxes;
    private int height;
    private int width;

    public Level(char[][] map) {
        //Array is copied so it can be edited safely
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
                    setPlayerStartingPosition(new Point(i, j));
                    eraseStartingMarker(i, j);
                }
                if (map[i][j] == '0') {
                    addBox(new Box(j, i));
                    replaceBox(i, j);
                }
            }
        }
    }

    /**
     * Method searches for the right Box object which is in a certain row/col
     * location. Called when it is known there's a box in that location and it
     * needs to be manipulated.
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

    public void deleteBox(Box box) {
        boxes.remove(box);
    }

    public void setPlayerStartingPosition(Point startPosition) {
        PlayerStartingPosition = startPosition;
    }

    public Point getPlayerStartingPosition() {
        return PlayerStartingPosition;
    }

    public void eraseStartingMarker(int row, int col) {
        map[row][col] = '.';
    }

    //Duplicate....
    public void replaceBox(int row, int col) {
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

}
