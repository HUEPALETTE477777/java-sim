package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyInput;
import main.Utility;

public class Player extends Entity {
    KeyInput keyInput;

    public final int screenX;
    public final int screenY;
    
    public Player(GamePanel gamePanel, KeyInput keyInput, Utility utilTool) {
        super(gamePanel, utilTool);
        this.keyInput = keyInput;

        screenX = (gamePanel.SCREEN_WIDTH / 2) - (gamePanel.tileSize / 2);
        screenY = (gamePanel.SCREEN_HEIGHT / 2) - (gamePanel.tileSize / 2);

        collisionBox.x = 12;
        collisionBox.y = 21;
        collisionBox.width = 24;
        collisionBox.height = 42;
        
        setDefaultValues();
        fetchImages();
    }

    public void setDefaultValues() {
        worldX = 64;
        worldY = 64;
        speed = 3;
        direction = "down";
        entityName = "THE ALFA GIRL";
    }

    // THIS WILL STAY HARDCODED HERE
    public void fetchImages() {
        left0 = gamePanel.assets.ImageRetrieval("/images/player/player_left_0");
        left1 = gamePanel.assets.ImageRetrieval("/images/player/player_left_1");
        left2 = gamePanel.assets.ImageRetrieval("/images/player/player_left_2");
   
        right0 = gamePanel.assets.ImageRetrieval("/images/player/player_right_0");
        right1 = gamePanel.assets.ImageRetrieval("/images/player/player_right_1");
        right2 = gamePanel.assets.ImageRetrieval("/images/player/player_right_2");
   
        down0 = gamePanel.assets.ImageRetrieval("/images/player/player_down_0");
        down1 = gamePanel.assets.ImageRetrieval("/images/player/player_down_1");
        down2 = gamePanel.assets.ImageRetrieval("/images/player/player_down_2");
   
        up0 = gamePanel.assets.ImageRetrieval("/images/player/player_up_0");
        up1 = gamePanel.assets.ImageRetrieval("/images/player/player_up_1");
        up2 = gamePanel.assets.ImageRetrieval("/images/player/player_up_2");
    }

    public int getX() {
        return worldX;
    }

    public int getY() {
        return worldY;
    }

    public void update() {
        if (keyInput.up) {
            direction = "up";
            collisionBounceCheck();
            if (!collisionToggle) { worldY -= speed; }
        }
        if (keyInput.down) {
            direction = "down";
            collisionBounceCheck();
            if (!collisionToggle) { worldY += speed; }
        }
        if (keyInput.left) {
            direction = "left";
            collisionBounceCheck();
            if (!collisionToggle) { worldX -= speed; }
        }
        if (keyInput.right) {
            direction = "right";
            collisionBounceCheck();
            if (!collisionToggle) { worldX += speed; }
        }

        spriteCount++;

        if (!keyInput.up && !keyInput.down && !keyInput.left && !keyInput.right) {
            spriteNum = 0;
        }

        if (utilTool.isMovementKeysPressed()) {
            updateAnimation();
        }

        gamePanel.collision.playerToNPCCollision(this, gamePanel.npc);
        
    }

    public void collisionBounceCheck() {
        utilTool.updateTileCollision(this);
    }

    // SHOULD BASE OFF TICKS INSTEAD! TF IS GOING ON
    public void updateAnimation() {
        if (spriteCount >= 10) {
            if (spriteNum == 0) {
                spriteNum = 1;
            } else if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 0;
            }
            spriteCount = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = gamePanel.utilTool.updateSpriteImage(this);
        g2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        utilTool.entityNameAlignment(g2d, screenX, screenY, entityName);
    }
}