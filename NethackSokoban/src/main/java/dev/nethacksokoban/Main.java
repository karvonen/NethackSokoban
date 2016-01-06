package dev.nethacksokoban;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.UI.GUI;
import javax.swing.SwingUtilities;

/**
 * Main class for NethackSokoban
 *
 */
public class Main {

    /**
     * Starts a new game, creates and attaches a GUI to it.
     *
     * @param args 
     */
    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        game.setGUI(gui);
        SwingUtilities.invokeLater(gui);
        game.startGame();
    }
}
