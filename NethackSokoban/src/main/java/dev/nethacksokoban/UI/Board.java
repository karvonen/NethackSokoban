package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.Game.Location;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class extends JPanel and draws on it the current state of the game after an
 * update method is called.
 */
public class Board extends JPanel {

    private Game game;
    private JPanel mapPanel;
    private JPanel playerPanel;
    private JLabel[][] mapLabels;
    private HashMap<Character, ImageIcon> assets;
    private HashMap<String, ImageIcon> playerAssets;
    private int maxHeight = 18;
    private int maxWidth = 31;

    public Board(Game game) {
        this.game = game;
        assets = new HashMap<>();
        playerAssets = new HashMap<>();
        playerAssets.put("down", new ImageIcon("assets/CharacterDown.png"));
        assets.put('0', new ImageIcon("assets/BrownBox.png"));
        assets.put('.', new ImageIcon("assets/GroundConcrete.png"));
        assets.put('#', new ImageIcon("assets/Wall.png"));
        assets.put('^', new ImageIcon("assets/OpenTrap.png"));
        assets.put('*', new ImageIcon("assets/FilledTrap.png"));
        assets.put('<', new ImageIcon("assets/Goal.png"));
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        setBackground(Color.WHITE);
//        if (game.getLevel() != null) {
//            Graphics board = g.create();
//            int size = 30;
//            for (int i = 0; i < game.getLevel().getMap().length; i++) {
//                for (int j = 0; j < game.getLevel().getMap()[i].length; j++) {
//                    if (game.getLevel().getPlayer().getCol() == j
//                            && game.getLevel().getPlayer().getRow() == i) {
//                        //Player is an oval and this sets its background color
//                        //to whatever is under the player
//                        if (game.getLevel().getTileFromLocation(new Location(i, j)) == '*') {
//                            board.setColor(Color.BLUE);
//                            board.fillRect(j * size, i * size, 30, 30);
//                        } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '<') {
//                            board.setColor(Color.GREEN);
//                            board.fillRect(j * size, i * size, 30, 30);
//                        }
//                        board.setColor(Color.PINK);
//                        board.fillOval(j * size, i * size, 30, 30);
//                    } else if (game.getLevel().getBoxInLocation(i, j) != null) {
//                        board.setColor(Color.orange);
//                        board.fillRect(j * size, i * size, 30, 30);
//                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '^') {
//                        board.setColor(Color.RED);
//                        board.fillRect(j * size, i * size, 30, 30);
//                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '*') {
//                        board.setColor(Color.BLUE);
//                        board.fillRect(j * size, i * size, 30, 30);
//                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '#') {
//                        board.setColor(Color.BLACK);
//                        board.fillRect(j * size, i * size, 30, 30);
//                    } else if (game.getLevel().getTileFromLocation(new Location(i, j)) == '<') {
//                        board.setColor(Color.GREEN);
//                        board.fillRect(j * size, i * size, 30, 30);
//                    } else {
//                        board.setColor(Color.WHITE);
//                        board.fillRect(j * size, i * size, 30, 30);
//                    }
//                }
//            }
//        }
//    }
    public void setup() {
        this.removeAll();
        this.setLayout(new GridLayout(maxHeight, maxWidth, 0, 0));
        mapLabels = new JLabel[maxHeight][maxWidth];

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                JLabel mapLabel = new JLabel("", JLabel.CENTER);
//                mapLabel.setPreferredSize(new Dimension(64, 64));
                mapLabels[i][j] = mapLabel;
                this.add(mapLabel);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        if (game.getLevel() != null && mapLabels != null) {
            int height = game.getLevel().getHeight();
            int width = game.getLevel().getWidth();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    char tile = game.getLevel().getTileFromLocation(new Location(i, j));
                    ImageIcon test = assets.get(tile);
                    mapLabels[i][j].setIcon(test);
                    if (game.getLevel().getPlayer().getCol() == j
                            && game.getLevel().getPlayer().getRow() == i) {
                        mapLabels[i][j].setIcon(playerAssets.get("down"));

                    } else if (game.getLevel().getBoxInLocation(i, j) != null) {
                        mapLabels[i][j].setIcon(assets.get('0'));
                    }

                }
                for (int j = width; j < maxWidth; j++) {
                    mapLabels[i][j].setIcon(null);
                    mapLabels[i][j].setText("");
                }
            }
        }
    }

    public void reDraw() {
        repaint();
    }

}
