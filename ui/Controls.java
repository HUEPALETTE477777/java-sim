package ui;

import main.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Controls {
    GamePanel gamePanel;

    public Controls(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void controlsScreen(Graphics2D g2d) {
        int x = gamePanel.tileSize;
        int y = gamePanel.tileSize;
        int width = gamePanel.SCREEN_WIDTH - (gamePanel.tileSize * 2);
        int height = gamePanel.SCREEN_HEIGHT - (gamePanel.tileSize * 2);

        Color c = new Color(0, 0, 0, 220);
        g2d.setColor(c);
        g2d.fillRect(x, y, width, height);

        g2d.setColor(Color.white);
        ArrayList<String> textList = new ArrayList<>();
        populateControlsList(textList);

        int yVal = 0;
        for (int i = 0; i < textList.size(); i++) {
            String text = textList.get(i);

            yVal += 20;
            g2d.drawString(text, gamePanel.tileSize, gamePanel.tileSize + yVal);
        }

    }

    public void populateControlsList(ArrayList<String> input) {
        input.add("PAUSE: P");
        input.add("FORWARD MUSIC: F");
        input.add("VOLUME UP: F7");
        input.add("VOLUME DOWN: F8");
        input.add("MUTE MUSIC: B");
        input.add("DIALOGUE CHOICES: 1-3 (DEPENDS ON NUMBER OF CHOICES)");
        input.add("CONTROLS: \\");
    }

}
