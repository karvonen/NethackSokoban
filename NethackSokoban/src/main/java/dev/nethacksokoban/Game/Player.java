package dev.nethacksokoban.Game;

/**
 * Class provides functionality for a Player object. Only one instance of this
 * class exists at one time and it is tied to the Level object. Player object is
 * drawn on top of level.
 */
public class Player {

    private int moves;
    private Location location;

    /**
     *
     * @param location Starting location.
     */
    public Player(Location location) {
        moves = 0;
        this.location = location;
    }

    /**
     *
     * @return Row where the player is located.
     */
    public int getRow() {
        return location.getRow();
    }

    /**
     *
     * @return Column where the player is located.
     */
    public int getCol() {
        return location.getCol();
    }

    /**
     *
     * @return Number of legal moves the player has done.
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Sets the player location to the Location object coordinates given as the
     * parameter and increases move count.
     *
     * @param location Sets player location to parameter.
     */
    public void setPlayerLocation(Location location) {
        this.location = location;
        moves++;
    }

    /**
     *
     * @return Location object where the box is located.
     */
    public Location getLocation() {
        return location;
    }
}
