package dev.nethacksokoban.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class handles keyboard input.
 * 
 */
public class KeyboardListener implements KeyListener {

    private final GUI gui;

    /**
     * Keyboardlistener listens for key presses and executes game commands based on 
     * them
     *
     * @param gui Main GUI on to which the listener is attached to.
     */
    public KeyboardListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD1
                || ke.getKeyCode() == KeyEvent.VK_B) {
            gui.executeGameCommand('1');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2
                || ke.getKeyCode() == KeyEvent.VK_J) {
            gui.executeGameCommand('2');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD3
                || ke.getKeyCode() == KeyEvent.VK_N) {
            gui.executeGameCommand('3');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4
                || ke.getKeyCode() == KeyEvent.VK_H) {
            gui.executeGameCommand('4');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6
                || ke.getKeyCode() == KeyEvent.VK_L) {
            gui.executeGameCommand('6');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD7
                || ke.getKeyCode() == KeyEvent.VK_Y) {
            gui.executeGameCommand('7');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8
                || ke.getKeyCode() == KeyEvent.VK_K) {
            gui.executeGameCommand('8');
        }
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD9
                || ke.getKeyCode() == KeyEvent.VK_U) {
            gui.executeGameCommand('9');
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}
