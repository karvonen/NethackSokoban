package dev.nethacksokoban.game;

/**
 * Class provides location as a column/row coordinate. Other classes use this
 * class as instead of a x/y coordinate to know where they are located in the
 * two dimensional level.
 */
public class Location {

    private int row;
    private int col;

    /**
     * Constructor for creating Location objects from row/column coordinate.
     *
     * @param row Row of the new location.
     * @param col Column of the new location.
     */
    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Constructor for copying Location objects.
     *
     * @param location Will be copied to a new Location object.
     */
    public Location(Location location) {
        this.row = location.getRow();
        this.col = location.getCol();
    }

    /**
     *
     * @return Row of the location object.
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @return Column of the location object.
     */
    public int getCol() {
        return col;
    }

    /**
     *
     * @param row Sets location object's row to parameter.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     *
     * @param col Sets location object's column to parameter.
     */
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return true;
    }

}
