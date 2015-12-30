package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.Game.Location;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Game game;

    public Board(Game game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        if (game.getLevel() != null) {
            Graphics board = g.create();
            int size = 30;
            for (int i = 0; i < game.getLevel().getMap().length; i++) {
                for (int j = 0; j < game.getLevel().getMap()[i].length; j++) {
                    if (game.getLevel().getPlayer().getCol() == j
                            && game.getLevel().getPlayer().getRow() == i) {
                        //Player is an oval and this sets its background color
                        //to whatever is under the player
                        if (game.getLevel().getTileFromLocation(new Location(i, j)) == '*') {
                            board.setColor(Color.BLUE);
                            board.fillRect(j * size, i * size, 30, 30);
                        } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '<') {
                            board.setColor(Color.GREEN);
                            board.fillRect(j * size, i * size, 30, 30);
                        }
                        board.setColor(Color.PINK);
                        board.fillOval(j * size, i * size, 30, 30);
                    } else if (game.getLevel().getBoxInLocation(i, j) != null) {
                        board.setColor(Color.orange);
                        board.fillRect(j * size, i * size, 30, 30);
                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '^') {
                        board.setColor(Color.RED);
                        board.fillRect(j * size, i * size, 30, 30);
                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '*') {
                        board.setColor(Color.BLUE);
                        board.fillRect(j * size, i * size, 30, 30);
                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '#') {
                        board.setColor(Color.BLACK);
                        board.fillRect(j * size, i * size, 30, 30);
                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '<') {
                        board.setColor(Color.GREEN);
                        board.fillRect(j * size, i * size, 30, 30);
                    } else {
                        board.setColor(Color.WHITE);
                        board.fillRect(j * size, i * size, 30, 30);
                    }
                }
            }
        }
    }

    public void reDraw() {
        repaint();
    }

}
