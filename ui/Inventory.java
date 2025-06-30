package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Inventory {
    GamePanel gamePanel;
    
    public Inventory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // SHOULD BE DRAWN ONLY IN PLAY STATE
    public void inventoryCollapsedScreen(Graphics2D g2d) {
        int containerOffset = 20;
        int containerX = (gamePanel.tileSize * 2) - containerOffset;
        int containerY = gamePanel.SCREEN_HEIGHT - (gamePanel.tileSize * 2) - containerOffset;
        int containerWidth = (gamePanel.SCREEN_WIDTH - (gamePanel.tileSize * 4)) + containerOffset;
        int containerHeight = gamePanel.tileSize + containerOffset * 2;

        Color c = new Color(255,0,0);
        g2d.setColor(c);
        g2d.fillRect(containerX, containerY, containerWidth, containerHeight);

        // ITEM FRAME WITHIN THE CONTAINER
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.SCREEN_HEIGHT - (gamePanel.tileSize * 2);

        int width = gamePanel.tileSize;
        int height = gamePanel.tileSize;

        c = new Color(0, 0, 0, 220);
        g2d.setColor(c);
        for (int i = 0; i < gamePanel.inventory.size(); i++) {
            BufferedImage itemImage = gamePanel.inventory.get(i).image;
            g2d.fillRect(x, y, width, height);
            g2d.drawImage(itemImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            x += 100;
        }

    }

    // DRAWN ONLY IN INVENTORY STATE
    public void inventoryExpandedScreen(Graphics2D g2d) {
        int x = gamePanel.tileSize;


        int y = gamePanel.tileSize;

        int width = gamePanel.SCREEN_WIDTH - (gamePanel.tileSize * 2);
        int height = gamePanel.SCREEN_HEIGHT - (gamePanel.tileSize * 2);

        Color c = new Color(255, 0, 0, 220);
        g2d.setColor(c);

        g2d.fillRect(x, y, width, height);

        

       
    }
    
}
