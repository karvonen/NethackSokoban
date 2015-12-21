package dev.nethacksokoban.Game;

public class Player {

    private int moves;
    private Location location;

    public Player(Location location) {
        moves = 0;
        this.location = location;
    }

    public int getRow() {
        return location.getRow();
    }

    public int getCol() {
        return location.getCol();
    }

    public int getMoves() {
        return moves;
    }

    public void move(Location location) {
        this.location = location;
        moves++;
    }

    public Location getLocation() {
        return location;
    }
}
