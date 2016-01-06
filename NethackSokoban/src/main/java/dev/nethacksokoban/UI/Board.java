package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import dev.nethacksokoban.Game.Location;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
    private HashMap<Character, ImageIcon> playerAssets;
    private int maxHeight = 18;
    private int maxWidth = 32;
    
    public Board(Game game) {
        this.game = game;
        assets = new HashMap<>();
        playerAssets = new HashMap<>();
        int iconHeight = 42;
        int iconWidth = 42;
        
        playerAssets.put('.', new ImageIcon(new ImageIcon("assets/PlayerGroundConcrete.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        playerAssets.put('*', new ImageIcon(new ImageIcon("assets/PlayerFilledTrap.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        playerAssets.put('<', new ImageIcon(new ImageIcon("assets/PlayerGoal.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        
        assets.put('0', new ImageIcon(new ImageIcon("assets/BrownBox.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        assets.put('.', new ImageIcon(new ImageIcon("assets/GroundConcrete.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        assets.put('#', new ImageIcon(new ImageIcon("assets/Wall.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        assets.put('^', new ImageIcon(new ImageIcon("assets/OpenTrap.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        assets.put('*', new ImageIcon(new ImageIcon("assets/FilledTrap.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
        assets.put('<', new ImageIcon(new ImageIcon("assets/Goal.png").getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT)));
    }
    
    public void setup() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        mapPanel = new JLayeredPane();
        mapPanel.setLayout(new GridLayout(maxHeight, maxWidth, 0, 0));

        mapLabels = new JLabel[maxHeight][maxWidth + 1];

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                JLabel mapLabel = new JLabel("", JLabel.CENTER);
                mapLabels[i][j] = mapLabel;
                mapPanel.add(mapLabel);
            }
        }
        this.add(mapPanel, 0);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        if (game.getLevel() != null && mapLabels != null && mapLabels[0][0] != null) {
            int height = game.getLevel().getHeight();
            int width = game.getLevel().getWidth();
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (mapLabels[i][j] == null) {
                        continue;
                    }
                    char tile = game.getLevel().getTileFromLocation(new Location(i, j));
                    ImageIcon tileIcon = assets.get(tile);
                    mapLabels[i][j].setIcon(tileIcon);
                    
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
            mapLabels[playerRow][playerCol].setIcon(playerAssets.get(game.getLevel().getTileFromLocation(new Location(playerRow, playerCol))));
        }
    }
    
    public void reDraw() {
        repaint();
    }
    
}
