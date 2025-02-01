package UI;

import javax.swing.*;
import java.awt.*;

public class Game_panel extends JPanel{

    private Game game;
    private KeyHandler keyH = new KeyHandler(this);

    public Game_panel(Game game, KeyHandler keyH) {
        this.keyH = keyH;
        addKeyListener(keyH);
        this.game = game;
        this.setPreferredSize(new Dimension(1200, 500));
        this.setFocusable(true);
        this.setBackground(new Color(247,247,247));
        this.setDoubleBuffered(true);
        this.requestFocusInWindow();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);

    }

    public Game getGame() {
        return game;
    }


}
