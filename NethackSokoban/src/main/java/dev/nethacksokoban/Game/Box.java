package dev.nethacksokoban.Game;

public class Box {

    private Location location;
    
    public Box(int row, int col) {
        location = new Location(row, col);
    }
    public int getRow() {
        return location.getRow();
    }

    public int getCol() {
        return location.getCol();
    }

    public void setCol(int col) {
        this.location.setCol(col);
    }

    public void setRow(int row) {
        this.location.setRow(row);
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public Location getLocation() {
        return this.location;
    }
}
