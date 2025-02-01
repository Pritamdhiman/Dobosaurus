package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Cloud {
    private int x, y;
    BufferedImage cloudImage;
    private int h = 50;
    private int w = 100;
    private int speed = 2;

    private ArrayList<Cloud> clouds;
    private long lastSpawnTime = 0;
    private static final long SPAWN_INTERVAL = 700;


    public Cloud() {
        this.x = x;
        this.y = y;
        clouds = new ArrayList<>();
        loadIMG();

    }

    private void loadIMG() {
        try {
            cloudImage = ImageIO.read(getClass().getResourceAsStream("/cloud.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSpawnTime > SPAWN_INTERVAL) {
            spawnCloud();
            lastSpawnTime = currentTime;
        }

        for (Cloud cloud : clouds) {
            cloud.x -= speed;
        }

        // Remove clouds that are off-screen
        clouds.removeIf(cloud -> cloud.x + w < 0);
    }

    private void spawnCloud() {
        Random rand = new Random();
        int spawnX = rand.nextInt(700) + 1200;  // Spawn off-screen on the right
        int spawnY = rand.nextInt(250) + 50;     // Random height between 50 and 200
        Cloud newCloud = new Cloud();
        newCloud.x = spawnX;
        newCloud.y = spawnY;
        clouds.add(newCloud);
    }

    public void render(Graphics g) {
        for (Cloud cloud : clouds) {
            g.drawImage(cloudImage, cloud.x, cloud.y, w, h, null);
        }
    }


}
