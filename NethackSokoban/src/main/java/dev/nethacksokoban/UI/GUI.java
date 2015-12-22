package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GUI implements Runnable {

    private JFrame frame;
    private Game game;
    private Board board;
    
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
        frame.setVisible(true);
    }



    private void createComponents(Container container) {
        board = new Board(game);
        container.add(board);
        KeyboardListener keyboardListener = new KeyboardListener(game);
        getFrame().addKeyListener(keyboardListener);
    }
    
    public JFrame getFrame() {
        return frame;
    }

    public Board getUpdatable() {
        return board;
    }
}
