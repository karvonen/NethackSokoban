package dev.nethacksokoban.UI;

import dev.nethacksokoban.Game.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class listens for keyboard input and calls gate to execute commands based on
 * the inputs.
 */
public class KeyboardListener implements KeyListener {

    private Game game;

    /**
     * Keylistener listens for key presses and executes game commands based on 
     * them
     *
     * @param game Game which is controlled by the key listener.
     */
    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD1
                || ke.getKeyCode() == KeyEvent.VK_B) {
            game.executeGameCommand('1');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2
                || ke.getKeyCode() == KeyEvent.VK_J) {
            game.executeGameCommand('2');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD3
                || ke.getKeyCode() == KeyEvent.VK_N) {
            game.executeGameCommand('3');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4
                || ke.getKeyCode() == KeyEvent.VK_H) {
            game.executeGameCommand('4');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6
                || ke.getKeyCode() == KeyEvent.VK_L) {
            game.executeGameCommand('6');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD7
                || ke.getKeyCode() == KeyEvent.VK_Y) {
            game.executeGameCommand('7');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8
                || ke.getKeyCode() == KeyEvent.VK_K) {
            game.executeGameCommand('8');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD9
                || ke.getKeyCode() == KeyEvent.VK_U) {
            game.executeGameCommand('9');
        }
        game.update();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}
