package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class ButtonListener implements ActionListener {

    private Game game;
    private MenuPanel panel;

    public ButtonListener(Game game, MenuPanel panel) {
        this.game = game;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
        if (ae.getActionCommand().equals("Select")) {
            JComboBox<Integer> selector = panel.getJComboBox();
            int selectedNumber = (int) selector.getSelectedItem();
            game.startNewMapWithIndex(selectedNumber);
        }
    }
}
