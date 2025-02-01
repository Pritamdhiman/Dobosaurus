package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle {
    private int x = 1100;
    private int y = 330;
    private int w = 50;
    private int h = 60;
    private int speed = 3;
    private BufferedImage obstacleImage;
    private KeyHandler keyH;
    private ArrayList<Obstacle> obs;

    private long lastSpawnTime = 0;
    private static final long SPAWN_INTERVAL = 2000;

    public Obstacle(int x, KeyHandler keyH) {
        this.x = x;
        this.keyH = keyH;
        obs = new ArrayList<>(2);
        loadIMG();
    }

    private void loadIMG() {
        try {
            obstacleImage = ImageIO.read(getClass().getResourceAsStream("/cactus1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
            spawnObstacle();
            lastSpawnTime = currentTime;
        }

        if (keyH.moving) {
            for (Obstacle obstacle : obs) {
                obstacle.x -= speed;
            }
        }
        obs.removeIf(obstacle -> obstacle.x + obstacle.w < 0);
    }

    private void spawnObstacle() {
        if (!obs.isEmpty()) {
            Obstacle lastObstacle = obs.get(obs.size() - 1);
            int lastX = lastObstacle.x;
            if (lastX < 1100 - 800) {
                return;
            }
        }
        Random rand = new Random();
        int spawnX = rand.nextInt(700) + 1200;
        Obstacle newObstacle = new Obstacle(spawnX, keyH);
        obs.add(newObstacle);
    }


    public void reset_pos(){
        obs.clear();
    }

    public ArrayList<Obstacle> getObstacles() {
        return obs;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void render(Graphics g) {
        for (Obstacle obstacle : obs) {
            g.drawImage(obstacleImage, obstacle.x, obstacle.y, w, h, null);
        }
    }

}
