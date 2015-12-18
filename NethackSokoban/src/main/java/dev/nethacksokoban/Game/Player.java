package dev.nethacksokoban.Game;

public class Player {

    private int row;
    private int col;
    private int moves;

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
        moves = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getMoves() {
        return moves;
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
        moves++;
    }
}
