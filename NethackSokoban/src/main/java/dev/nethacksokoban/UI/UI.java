package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Level;
import dev.nethacksokoban.Game.Location;
import dev.nethacksokoban.Util.InputScanner;

public class UI {


    private InputScanner inputScanner;
    
    public UI(InputScanner inputScanner) {
        this.inputScanner = inputScanner;
    }

    public void printLine(String line) {
        System.out.println(line);
    }

    public void update(Level thisLevel) {
        for (int i = 0; i < thisLevel.getHeight(); i++) {
            for (int j = 0; j < thisLevel.getWidth(); j++) {
                if (thisLevel.getPlayer().getRow() == i && thisLevel.getPlayer().getCol() == j) {
                    System.out.print("@");
                } else if (thisLevel.getBoxInLocation(i, j) != null) {
                    System.out.print("0");
                } else {
                    System.out.print(thisLevel.getTileFromLocation(new Location(i, j)));
                }
            }
            System.out.println("");
        }
    }
    
    public char readCommand() {
        return inputScanner.readChar();
    }

    public void victory(Level level) {
        System.out.println("Victory!");
        System.out.println("Moves used: " + level.getPlayer().getMoves());
    }
}
