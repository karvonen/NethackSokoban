package dev.nethacksokoban.Game;

public class Box {

    private int row;
    private int col;

    public Box(int row, int col) {
        this.row = col;
        this.col = row;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

}
