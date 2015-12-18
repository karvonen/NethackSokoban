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
        this.map = map;
        this.boxes = new ArrayList<>();
        this.height = map.length;
        this.width = map[0].length;
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

    public void deleteBoxIndex(int index) {
        boxes.remove(index);
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
