package dev.nethacksokoban.Game;

import java.awt.Point;

public class Level {

    private char[][] map;
    private Point PlayerStartingPosition;

    public Level(char[][] map) {
        this.map = map;

    }

    public void setPlayerStartingPosition(Point startPosition) {
        PlayerStartingPosition = startPosition;
    }

    public Point getPlayerStartingPosition() {
        return PlayerStartingPosition;
    }

    public void eraseStartingMarker(int x, int y) {
        map[x][y] = '.';
    }

    public char[][] getMap() {
        return map;
    }

}
