package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Dobo {

    private BufferedImage[] runImages, crouchImage;
    private BufferedImage idleImage, gameOverImage;
    private int ground_pos = 290;
    private float x, y;
    private int aniTick = 0, aniIndex = 0;
    private int aniSpeed = 10;
    private int playerAction = 0;
    private float forward_speed = 1f;
    private float jumpVelocity = -12;
    private float gravity = 0.3f;
    private boolean isJumping = false;
    public boolean game_not_Over = true;
    KeyHandler keyH;
    private int[] rex_size = {100, 100};
    public long gameOverTime = 0;
    public static final int RESTART_DELAY = 500;

    public static final int RUNNING = 1;
    public static final int IDLE = 0;
    public static final int CROUCH = 2;

    public Dobo(int x, int y, KeyHandler keyH) {
        this.x = x;
        this.y = y;
        this.keyH = keyH;
        load_animation();  // Load animation frames
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, rex_size[0], rex_size[1]); // Adjust size if needed
    }

    public void reset_pos(){
        x = 70;
        y = ground_pos;
        game_not_Over = true;
        isJumping = false;
        jumpVelocity = -15;
    }

    private void load_animation() {
        try {
            runImages = new BufferedImage[2];
            runImages[0] = ImageIO.read(getClass().getResourceAsStream("/main-character1.png"));
            runImages[1] = ImageIO.read(getClass().getResourceAsStream("/main-character2.png"));

            idleImage = ImageIO.read(getClass().getResourceAsStream("/main-character3.png"));

            crouchImage = new BufferedImage[2];
            crouchImage[0] = ImageIO.read(getClass().getResourceAsStream("/main-character5.png"));
            crouchImage[1] = ImageIO.read(getClass().getResourceAsStream("/main-character6.png"));

            gameOverImage = ImageIO.read(getClass().getResourceAsStream("/main-character4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update the animation and movement
    public void update() {
        if(!game_not_Over){
            return;
        }

        updateAnimation();
        setAnimation();
        update_pos();
    }

    // Render the T-Rex on the screen
    public void render(Graphics g) {
        if (!game_not_Over) {
            g.drawImage(gameOverImage, (int)x, (int)y, rex_size[0], rex_size[1], null);
            g.drawImage(getGameOverTextImage(), 350, 100,500,40 , null);
            g.drawImage(getReplayButtonImage(), 565, 170, 50, 50, null);
            return;
        }

        switch (playerAction) {
            case RUNNING:
                g.drawImage(runImages[aniIndex], (int)x, (int)y, rex_size[0], rex_size[1], null);
                break;
            case IDLE:
                g.drawImage(idleImage, (int)x, (int)y, rex_size[0], rex_size[1], null);
                break;
            case CROUCH:
                g.drawImage(crouchImage[aniIndex], (int)x, (int)y, rex_size[0], rex_size[1], null);
                break;
        }
    }

    private void update_pos() {

        if (keyH.jump && !isJumping) {
            isJumping = true;
            y += jumpVelocity;
            Sound.playSound("jump.wav");
        }

        if (isJumping) {
            y += jumpVelocity;
            jumpVelocity += gravity;
            if (y >= ground_pos) {
                y = ground_pos;
                isJumping = false;
                jumpVelocity = -12;
            }
        }

    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= runImages.length) {
                aniIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if (keyH.moving) {
            if(keyH.jump){
                playerAction = RUNNING;
            } else if(keyH.crouch) {
                playerAction = CROUCH;
            }
        } else {
            playerAction = IDLE;
        }
    }

    public void endGame() {
        game_not_Over = false;
        gameOverTime = System.currentTimeMillis();
        Sound.playSound("dead.wav");
    }

    private BufferedImage getGameOverTextImage() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("/gameover_text.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Load the Replay button image
    private BufferedImage getReplayButtonImage() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("/replay_button.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}