package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Menu {
    public GamePanel gamePanel;

    public Menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void menuScreen(Graphics2D g2d) {
        int width = gamePanel.SCREEN_WIDTH;
        int height = gamePanel.SCREEN_HEIGHT;

        BufferedImage backgroundImage = gamePanel.assets.ImageRetrieval("/images/menu/BackgroundImage");
        g2d.drawImage(backgroundImage, 0, 0, width, height, null);

        Color c = new Color(255, 0, 0);
        String titleText = "DATING FREAKSHOW";
        g2d.setFont(g2d.getFont().deriveFont(69F));
        g2d.setColor(c);
        FontMetrics fontMetric = g2d.getFontMetrics();

        int titleXCords = gamePanel.utilTool.centerTextX(fontMetric, titleText, g2d);
        g2d.drawString(titleText, titleXCords, gamePanel.tileSize * 4);

        String instructionText = "PRESS ENTER TO START GAME";
        int instructionTextXCords = gamePanel.utilTool.centerTextX(fontMetric, instructionText, g2d);
        g2d.drawString(instructionText, instructionTextXCords, gamePanel.tileSize * 3);

        BufferedImage menuGirl = gamePanel.player.down0;
        int menuGirlX = gamePanel.tileSize * 12;
        int menuGirlY =  gamePanel.SCREEN_HEIGHT - gamePanel.tileSize * 7;      
        int menuGirlWidth = gamePanel.tileSize * 10;
        int menuGirlHeight = gamePanel.tileSize * 4;
        g2d.drawImage(menuGirl, menuGirlX, menuGirlY, menuGirlWidth, menuGirlHeight, null);

        String volumeInstructionText = "PRESS F7 TO INCREASE VOLUME, F8 TO LOWER VOLUME";
        g2d.setFont(g2d.getFont().deriveFont(30F));
        fontMetric = g2d.getFontMetrics();
        int volumeInstructionTextXCords = gamePanel.utilTool.centerTextX(fontMetric, volumeInstructionText, g2d);

        g2d.drawString(volumeInstructionText, volumeInstructionTextXCords, gamePanel.tileSize * 13);
    }
}
