package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

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
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setSize(new Dimension(1000, 600));
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        board = new Board(game);
        container.setLayout(new BorderLayout());
        container.add(board);

        KeyboardListener keyboardListener = new KeyboardListener(game);
        frame.setFocusable(true);

        frame.addKeyListener(keyboardListener);
    }

    public void addMenuPanel() {
        menu = new MenuPanel(game, this);
        frame.getContentPane().add(menu, BorderLayout.SOUTH);
        menu.fillLevelSelector();
    }
    
    public void victoryDialog() {
        JOptionPane.showMessageDialog(frame, "Victory with " + 
                game.getLevel().getPlayer().getMoves() + " moves used!");
    }

    public JFrame getFrame() {
        return frame;
    }

    public Board getUpdatable() {
        return board;
    }

    public void setFocusBackToFrame() {
        frame.requestFocus();
    }
}
