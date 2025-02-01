package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Ground {
    private BufferedImage[] groundImages;
    private BufferedImage cloudImage;
    private final int groundTileWidth = 1200;
    private final int speed = 2;
    private final int[] xPositions = new int[3];
    private int y = 350;
    private KeyHandler keyH;



    public Ground(KeyHandler keyH) {
        this.keyH = keyH;
        loadGroundImages();
        for (int i = 0; i < 3; i++) {
            xPositions[i] = i * groundTileWidth;
        }
    }


    private void loadGroundImages() {
        try {
            groundImages = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/land1.png")),
                    ImageIO.read(getClass().getResourceAsStream("/land2.png")),
                    ImageIO.read(getClass().getResourceAsStream("/land3.png")),
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void update() {
        if (keyH.moving) {
            for (int i = 0; i < 3; i++) {
                xPositions[i] -= speed;
                if (xPositions[i] + groundTileWidth < 0) {
                    xPositions[i] = (xPositions[(i + 2) % 3] + groundTileWidth);
                }
            }
        }

    }


    public void render(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(groundImages[i], xPositions[i], y, groundTileWidth, 50, null);
        }
    }



}
