package main.objects;

import main.GamePanel;
import main.Utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ObjectHandler {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    GamePanel gamePanel;
    Utility utilTool;

    public ObjectHandler(GamePanel gamePanel, Utility utilTool) {
        this.gamePanel = gamePanel;
        this.utilTool = utilTool;
    }

    public void draw(Graphics2D g2d) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        boolean withinCameraBounds = gamePanel.utilTool.ObjectWithinScreenBounds(this);
        if (withinCameraBounds) {
            // BufferedImage currentImage = gamePanel.utilTool.updateSpriteImage(this);
            g2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            // gamePanel.utilTool.entityNameAlignment(g2d, screenX, screenY, entityName);
        }
    }

}
