package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * Class creates and maintains graphic user interface elements.
 */
public class GUI implements Runnable {

    private JFrame frame;
    private Game game;
    private Board board;
    private MenuPanel menu;

    public GUI(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        frame = new JFrame("NethackSokoban");
        frame.setPreferredSize(new Dimension(1344, 756));
        frame.setSize(new Dimension(1344, 756));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        board = new Board(game);
        container.setLayout(new BorderLayout());
        container.add(board);

        KeyboardListener keyboardListener = new KeyboardListener(this);
        frame.setFocusable(true);

        frame.addKeyListener(keyboardListener);
    }

    /**
     * Creates and adds a menu panel to the bottom of the game window.
     *
     */
    public void addMenuPanel() throws InterruptedException {
        menu = new MenuPanel(game, this);
//        System.out.println(frame + "  debug: onko null??");

        //Bugi!? Frame on välillä null jos tässä ei odoteta hetken aikaa? Ja
        //taas välillä toimii... En tajua..
        Thread.sleep(1100);

        frame.getContentPane().add(menu, BorderLayout.SOUTH);
        menu.fillLevelSelector(game.getLevels().size());
    }

    /**
     * Shows a message dialog informing about victory and moves used.
     *
     */
    public void victoryDialog() {
        JOptionPane.showMessageDialog(frame, "Victory with "
                + game.getLevel().getPlayer().getMoves() + " moves used!");
    }

    /**
     * @return JFrame which is the game window.
     *
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * @return Board that is updated after each move.
     *
     */
    public Board getUpdatable() {
        return board;
    }

    /**
     * Method sets the focus back to JFrame so that KeyListener can react to new
     * key presses.
     *
     */
    public void setFocusBackToFrame() {
        frame.requestFocus();
    }

    /**
     * Updates the number of legal moves used in the menu panel.
     *
     */
    public void updateMenuMoveCount() {
        menu.updateMoves(game.getLevel().getPlayer().getMoves());
    }

    /**
     * Method calls game to start a new game with selected Level.
     *
     * @param selectedNumber Selected number from the JComboBox in MenuPanel.
     */
    public void startNewMapWithIndex(int selectedNumber) {
        game.startNewMapWithIndex(selectedNumber + 1);
    }

    public void executeGameCommand(char command) {
        game.executeGameCommand(command);
        game.update();
    }
}
