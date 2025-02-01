package UI;

import javax.swing.*;

public class Game_window extends JFrame {
    public Game_window(Game_panel gp){
        this.setTitle("Dobosaurus");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(gp);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
