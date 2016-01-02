package dev.nethacksokoban.Game;

/**
 * Instances of this class are objects drawn on top of the level.
 */
public class Box {

    private Location location;

    public Box(int row, int col) {
        location = new Location(row, col);
    }

    /**
     *
     * @return Row where the box is located.
     */
    public int getRow() {
        return location.getRow();
    }

    /**
     *
     * @return Column where the box is located.
     */
    public int getCol() {
        return location.getCol();
    }

    /**
     *
     * @param col Sets objects column to parameter.
     */
    public void setCol(int col) {
        this.location.setCol(col);
    }

    /**
     *
     * @param row Sets objects row to parameter.
     */
    public void setRow(int row) {
        this.location.setRow(row);
    }

    /**
     *
     * @param location Sets objects location to parameter.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return Location object where the box is located.
     */
    public Location getLocation() {
        return this.location;
    }
}
