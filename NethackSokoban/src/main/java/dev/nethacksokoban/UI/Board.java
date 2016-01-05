package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.Game.Location;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Class extends JPanel and draws on it the current state of the game after an
 * update method is called.
 */
public class Board extends JLayeredPane {
    
    private Game game;
    private JLayeredPane mapPanel;
    private JLayeredPane playerPanel;
    private JLabel[][] mapLabels;
    private JLabel[][] playerLabels;
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

    public void setup() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        mapPanel = new JLayeredPane();
        mapPanel.setLayout(new GridLayout(maxHeight, maxWidth, 0, 0));
        
        playerPanel = new JLayeredPane();
        playerPanel.setLayout(new GridLayout(maxHeight, maxWidth, 0, 0));
        
        mapLabels = new JLabel[maxHeight][maxWidth];
        playerLabels = new JLabel[maxHeight][maxWidth];
        
        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                JLabel mapLabel = new JLabel("", JLabel.CENTER);
                JLabel playerLabel = new JLabel("", JLabel.CENTER);
                playerLabel.setVisible(false);
                playerLabel.setOpaque(false);
//                mapLabel.setPreferredSize(new Dimension(64, 64));
                mapLabels[i][j] = mapLabel;
                playerLabels[i][j] = playerLabel;
                mapPanel.add(mapLabel);
                playerPanel.add(playerLabel);
            }
        }
        this.add(mapPanel, 0);
        this.add(playerPanel, 1);
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
                    playerLabels[i][j].setIcon(null);
                    playerLabels[i][j].setVisible(false);
//                    playerLabels[i][j] = null;

                    char tile = game.getLevel().getTileFromLocation(new Location(i, j));
                    ImageIcon test = assets.get(tile);
                    mapLabels[i][j].setIcon(test);
                    
                    if (game.getLevel().getBoxInLocation(i, j) != null) {
                        mapLabels[i][j].setIcon(assets.get('0'));
                    }
                    
                }
                for (int j = width; j < maxWidth; j++) {
                    mapLabels[i][j].setIcon(null);
                    mapLabels[i][j].setText("");
                }
            }
            int playerRow = game.getLevel().getPlayer().getRow();
            int playerCol = game.getLevel().getPlayer().getCol();
//            playerLabels[playerRow][playerCol] = new JLabel("", JLabel.CENTER);
            playerLabels[playerRow][playerCol].setVisible(true);
            playerLabels[playerRow][playerCol].setIcon(playerAssets.get("down"));
        }
    }
    
    public void reDraw() {
        repaint();
    }
    
}
