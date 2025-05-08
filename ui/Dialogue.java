package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Dialogue {
    public GamePanel gamePanel;

    public Dialogue(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void dialogueScreen(Graphics2D g2d) {
        int x = gamePanel.tileSize;
        int y = gamePanel.SCREEN_HEIGHT - (gamePanel.tileSize * 5);
        int width = (gamePanel.SCREEN_WIDTH) - (gamePanel.tileSize * 2);
        int height = gamePanel.SCREEN_HEIGHT / 4;
    
        Color c = new Color(0, 0, 0, 200);
        g2d.setColor(c);
        g2d.fillRect(x, y, width, height);
    
        g2d.setColor(Color.WHITE);  
        g2d.setStroke(new BasicStroke(3));  
        g2d.drawRect(x, y, width, height); 

        x += gamePanel.tileSize / 2;
        y += gamePanel.tileSize / 1.5;

        g2d.setFont(g2d.getFont().deriveFont(24F));

        for (String line : gamePanel.ui.currentDialogue.split("\n")) {
            g2d.drawString(line, x, y);
            y += 40;
        }

        int dialogueCharacterImageX = gamePanel.tileSize;
        int dialogueCharacterImageY =  gamePanel.SCREEN_HEIGHT - gamePanel.tileSize * 8;      
        int dialogueCharacterImageWidth = gamePanel.tileSize * 10;
        int dialogueCharacterImageHeight = gamePanel.tileSize * 3;
        
        g2d.drawImage(gamePanel.ui.dialogueCharacterImage, dialogueCharacterImageX, dialogueCharacterImageY, dialogueCharacterImageWidth, dialogueCharacterImageHeight, null);
    }
    
}
