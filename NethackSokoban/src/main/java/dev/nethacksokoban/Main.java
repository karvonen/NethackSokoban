package dev.nethacksokoban;

import dev.nethacksokoban.game.Game;
import dev.nethacksokoban.ui.GUI;
import javax.swing.SwingUtilities;

/**
 * Main class for NethackSokoban
 *
 */
public class Main {

    /**
     * Starts a new game, creates and attaches a GUI to it.
     *
     * @param args arguments 
     */
    public static void main(String[] args) {
        Game game = new Game();
        GUI gui = new GUI(game);
        game.setGUI(gui);
        SwingUtilities.invokeLater(gui);
        game.startGame();
    }
}
