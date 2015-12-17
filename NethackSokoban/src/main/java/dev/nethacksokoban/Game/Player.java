
package dev.nethacksokoban.Game;

public class Player {
    
    private int x;
    private int y;
    private int moves;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        moves = 0;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void move(int newX, int newY) {
        x = newX;
        y = newY;
    
    }
    
    
    
    
}
