package UI;

import java.awt.*;

public class Game implements Runnable{
    Game_window gw;
    Game_panel gp;
    KeyHandler keyH;
    private Thread game_thread;
    private final static int FPS = 120;
    private final static int UPS = 120;
    private Ground ground;
    private Dobo rex;
    private Obstacle obs;
    private Cloud cld;
    private int score = 0;
    private int scoreTick = 0;
    private int highScore = 0;
    private final int SCORE_SPEED = 20;


    public Game() {
        keyH = new KeyHandler(gp);
        init_classes();
        gp = new Game_panel(this, keyH);
        gw = new Game_window(gp);
        gp.requestFocus();
        start_game_loop();
    }


    private void start_game_loop(){
        game_thread = new Thread(this);
        game_thread.start();
    }

    private void init_classes() {
        ground = new Ground(keyH);
        cld = new Cloud();
        obs = new Obstacle(1100, keyH);
        rex = new Dobo(150,290, keyH);


    }

    public void update(){
        if (rex.game_not_Over) {
            scoreTick++;
            if (scoreTick >= SCORE_SPEED) {
                score++;
                scoreTick = 0;
                if(score == 100){
                    Sound.playSound("scoreup.wav");
                }
            }
            ground.update();
            cld.update();
            obs.update();
            rex.update();

            // Check for collision
            if (checkCollision()) {
                rex.endGame();
                updateHighScore();
            }
        } else if (System.currentTimeMillis() - rex.gameOverTime >= Dobo.RESTART_DELAY) {
            if (keyH.jump) { // Check if the jump key is pressed to restart the game
                resetGame();
            }
        }

    }

    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
        }
    }

    private void resetGame() {
        init_classes();
        rex.reset_pos();
        obs.reset_pos();
        score= 0;
    }

    private boolean checkCollision() {
        Rectangle rexBounds = rex.getBounds();
        for (Obstacle obstacle : obs.getObstacles()) {
            if (rexBounds.intersects(obstacle.getBounds())) {
                return true; // Collision detected
            }
        }
        return false;
    }

    public void render(Graphics g){
        // Render everything like, game_panel, MC, NPC, etc.
        ground.render(g);
        cld.render(g);
        obs.render(g);
        rex.render(g);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 1000, 30);
        g.drawString("High Score: " + highScore, 1000, 50);


    }


    @Override
    public void run(){

        double time_per_frame = 1000000000.0 / FPS;
        double time_per_update = 1000000000.0 / UPS;
        long prev_time = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true){
            long current_time = System.nanoTime();
            deltaU += (current_time - prev_time) / time_per_update;
            deltaF += (current_time - prev_time) / time_per_frame;
            prev_time = current_time;
            if(deltaU >= 1){
                // update
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1){
                gp.repaint();
                Toolkit.getDefaultToolkit().sync();
                frames++;
                deltaF--;
            }


            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames + " | " + "UPS : "+ updates);
                frames = 0;
                updates = 0;
            }

            Thread.yield(); // Sleep for 1 ms
        }
    }

}
