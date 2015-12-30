package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private Game game;
    private ButtonListener buttonListener;
    private JComboBox<Integer> levels;
    private GUI gui;

    public MenuPanel(Game game, GUI gui) {
        super(new GridLayout(1, 3));
        this.game = game;
        this.gui = gui;
        createComponents();
    }

    private void createComponents() {
        buttonListener = new ButtonListener(game, this);
        levels = new JComboBox<>();
        add(levels);

        JButton select = new JButton("Select");
        select.addActionListener(buttonListener);
        add(select);

        JButton exit = new JButton("Exit");
        exit.addActionListener(buttonListener);
        add(exit);
    }

    public void fillLevelSelector() {
        for (int i = 1; i <= game.getLevels().size(); i++) {
            levels.addItem(i);
        }
    }

    public JComboBox<Integer> getJComboBox() {
        return levels;
    }

    public void surrenderFocus() {
        gui.setFocusBackToFrame();
    }
}
