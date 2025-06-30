package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.KeyInput;
import main.Sound;
import main.Utility;
import tile.TileHandler;

public class UI {
    public GamePanel gamePanel;
    public Player player;
    public KeyInput keyInput;
    public TileHandler tileHandler;
    public Sound sound;
    public int fpsCount;
    public Entity[] npc;
    public Utility utilTool;

    public Menu menuComponent;
    public Dialogue dialogueComponent;
    public Controls controlsComponent;
    public Inventory inventoryComponent;

    public String currentDialogue = "";
    public BufferedImage dialogueCharacterImage;

    public UI(GamePanel gamePanel, Player player, KeyInput keyInput, TileHandler tileHandler, Sound sound,
            Entity[] npc) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.keyInput = keyInput;
        this.tileHandler = tileHandler;
        this.sound = sound;
        this.npc = npc;

        utilTool = new Utility(this.gamePanel, this.keyInput);
        menuComponent = new Menu(gamePanel);
        dialogueComponent = new Dialogue(gamePanel);
        controlsComponent = new Controls(gamePanel);
        inventoryComponent = new Inventory(gamePanel);
    }

    public void update(int fpsCount) {
        this.fpsCount = fpsCount;
    }

    public void display(Graphics2D g2d) {
        // 0, 1, 2, 3 => MENU, PLAY, PAUSE, DIALOGUE
        switch (gamePanel.gameState) {
            case 0:
                menuComponent.menuScreen(g2d);
                break;
            case 1:
                renderPlayState(g2d);
                inventoryComponent.inventoryCollapsedScreen(g2d);
                break;
            case 2:
                pauseScreen(g2d);
                break;
            case 3:
                dialogueComponent.dialogueScreen(g2d);
                break;
            case 4: 
                controlsComponent.controlsScreen(g2d);
            case 5: 
                inventoryComponent.inventoryExpandedScreen(g2d);
            default:
                break;
        }

    }

    public void renderPlayState(Graphics2D g2d) {
        if (utilTool.isHUD()) {
            drawHUD(g2d);
        }
    }

    public void drawHUD(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(g2d.getFont().deriveFont(18f));
        g2d.drawString("FPS: " + gamePanel.fpsUpdate, 10, 30);
        g2d.drawString("X: " + player.getX() + " Y: " + player.getY(), 10, 50);
        g2d.drawString("VOLUME (FLOAT): " + sound.currentVolume + "F", 10, 70);
        g2d.drawString("CURRENT SONG: " + gamePanel.utilTool.extractSongName(), 10, 90);
        g2d.drawString("AUDIO DURATION: " + gamePanel.sound.currentAudioSecond + "/" + gamePanel.sound.currentAudioDuration + " SECONDS", 10, 110);
    }

    public void pauseScreen(Graphics2D g2d) {
        String text = "PAUSED";
        g2d.setFont(g2d.getFont().deriveFont(96F));
        FontMetrics fontMetric = g2d.getFontMetrics();

        int xCords = utilTool.centerTextX(fontMetric, text, g2d);
        int yCords = utilTool.centerTextY(fontMetric, text, g2d);

        g2d.drawString(text, xCords, yCords);
    }

    public void redraw(Graphics2D g2d) {
        tileHandler.draw(g2d);

        for (Entity ent : npc) {
            if (ent != null) {
                ent.draw(g2d);
            }
        }

        player.draw(g2d);
    }

    public void draw(Graphics2D g2d) {
        display(g2d);
    }
}
