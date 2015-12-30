package dev.nethacksokoban;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.UI.GUI;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        game.setGUI(gui);
        SwingUtilities.invokeLater(gui);
        game.startGame();
    }
}
