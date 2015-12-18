package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Level;
import dev.nethacksokoban.Game.Player;

public class UI {

    private Level level;
    private Player player;

    public UI(Level level, Player player) {
        this.level = level;
        this.player = player;
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

    public void victory() {
        System.out.println("Victory!");
        System.out.println("Moves used: " + player.getMoves());
    }
}
