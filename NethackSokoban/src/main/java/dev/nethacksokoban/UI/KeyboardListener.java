package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD1) {
            game.executeGameCommand('1');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            game.executeGameCommand('2');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD3) {
            game.executeGameCommand('3');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            game.executeGameCommand('4');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            game.executeGameCommand('6');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD7) {
            game.executeGameCommand('7');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            game.executeGameCommand('8');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD9) {
            game.executeGameCommand('9');
        }
        if (ke.getKeyCode() == KeyEvent.VK_X) {
            game.executeGameCommand('x');
        }
        game.update();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}
