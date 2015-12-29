package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private Game game;
    private ButtonListener buttonListener;
    private JComboBox<Integer> levels;

    public MenuPanel(Game game) {
        super(new GridLayout(1, 3));
        this.game = game;
        buttonListener = new ButtonListener(game, this);
        createComponents();
    }

    private void createComponents() {
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
}
