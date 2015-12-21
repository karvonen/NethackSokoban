package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Level;
import dev.nethacksokoban.Game.Player;
import dev.nethacksokoban.Util.InputScanner;

public class UI {

    private Level level;
    private Player player;
    private InputScanner inputScanner;

    public UI(Level level, Player player, InputScanner inputScanner) {
        this.level = level;
        this.player = player;
        this.inputScanner = inputScanner;
    }

    public void printLine(String line) {
        System.out.println(line);
    }
    
    public void update() {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                if (player.getRow() == i && player.getCol() == j) {
                    System.out.print('@');
                } else if (level.getBoxInLocation(i, j) != null) {
                    System.out.print("0");
                } else {
                    System.out.print(level.getMap()[i][j]);
                }
            }
            System.out.println("");
        }
    }

    public char readCommand() {
        return inputScanner.readChar();
    }
    
    public void victory() {
        System.out.println("Victory!");
        System.out.println("Moves used: " + player.getMoves());
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
}
