package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements ActionListener {

    private Game game;
    private JComboBox<Integer> levels;
    private GUI gui;

    public MenuPanel(Game game, GUI gui) {
        super(new GridLayout(1, 3));
        this.game = game;
        this.gui = gui;
        createComponents();
    }

    private void createComponents() {
        levels = new JComboBox<>();
        levels.addActionListener(this);
        add(levels);

        JButton exit = new JButton("Exit");
        exit.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (ae.getActionCommand().equals("comboBoxChanged")) {
            JComboBox comboBox = (JComboBox) ae.getSource();
            int selectedNumber = comboBox.getSelectedIndex();
            //getSelectedIndex is 0 at the creation of the menu so a check is 
            //needed to make sure StarNewMapWithIndex is not called without
            //valid index.
            if (selectedNumber < 1 || selectedNumber > game.getLevels().size() + 1) {
                selectedNumber = 1;
            }
            game.startNewMapWithIndex(selectedNumber);
            gui.setFocusBackToFrame();
        }
    }
}
