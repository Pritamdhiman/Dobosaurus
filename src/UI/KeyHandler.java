package UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean jump = false;
    public boolean crouch = false;
    public boolean moving = false;
    Game_panel gp;

    public KeyHandler(Game_panel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                jump = true;
                moving = true;
                break;
            case KeyEvent.VK_D:
                crouch = true;
                moving = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                jump = false;
                break;
            case KeyEvent.VK_D:
                crouch = false;
                break;
        }
    }
}
