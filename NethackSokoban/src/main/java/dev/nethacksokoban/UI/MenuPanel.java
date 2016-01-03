package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class extends JPanel and creates on it a new grid layout containing the
 * game's menu.
 */
public class MenuPanel extends JPanel implements ActionListener {

    private Game game;
    private JComboBox<Integer> levels;
    private GUI gui;
    private JLabel moves;

    public MenuPanel(Game game, GUI gui) {
        super(new GridLayout(1, 3));
        this.game = game;
        this.gui = gui;
        createComponents();
    }

    /**
     * Creates the menu panel contents.
     *
     */
    private void createComponents() {
        levels = new JComboBox<>();
        levels.addActionListener(this);
        add(levels);

        moves = new JLabel("Moves: 0", SwingConstants.CENTER);
        add(moves);

        JButton exit = new JButton("Exit");
        exit.addActionListener(this);
        add(exit);
    }

    /**
     * Adds all the levels to the JComboBox level selector, starting from 1.
     *
     */
    public void fillLevelSelector() {
        for (int i = 1; i <= game.getLevels().size(); i++) {
            levels.addItem(i);
        }
    }

    /**
     * Updates the number of legal moves used.
     *
     */
    public void updateMoves() {
        moves.setText("Moves: " + game.getLevel().getPlayer().getMoves());
    }

    public JComboBox<Integer> getJComboBox() {
        return levels;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (ae.getActionCommand().equals("comboBoxChanged")) {
            JComboBox comboBox = (JComboBox) ae.getSource();
            int selectedNumber = comboBox.getSelectedIndex();
            game.startNewMapWithIndex(selectedNumber + 1);
            gui.setFocusBackToFrame();
        }
    }
}
