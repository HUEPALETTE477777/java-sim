package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import main.GamePanel;
import main.Utility;

public class Entity {
    GamePanel gamePanel;

    public int worldX, worldY;
    public int speed; // IT IS DISPLACEMENT!

    // REPRESENTS THE SPRITE IMAGE DIRECTIONS
    public BufferedImage up0, down0, left0, right0,
            up1, down1, left1, right1,
            up2, down2, left2, right2;
    public String direction; // HOLDS THE DIRECTION

    // SPRITE ANIM VARIABLES
    public int spriteCount = 0;
    public int spriteNum = 0;

    // HITBOX/COLLISION BOX
    public Rectangle collisionBox = new Rectangle(0, 0, 64, 64);
    public boolean collisionToggle = false;

    // NPC IMAGE SIZE
    public int entityImageWidth = 64;
    public int entityImageHeight = 64;

    public String entityName;
    Utility utilTool;

    public DialogueNode rootDialogueNode;
    public DialogueNode currentDialogueNode;

    // CHILDREN NEEDS TO SUPER THEIR GAMEPANEL INTO THIS CTOR
    public Entity(GamePanel gamePanel, Utility utilTool) {
        this.gamePanel = gamePanel;
        this.utilTool = utilTool;
    }

    // ANY ENTITY SPEAKING (DIALOGUE)
    public void speak() {
        if (currentDialogueNode == null) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
            return;
        }
    
        gamePanel.ui.currentDialogue = this.entityName + ": " + currentDialogueNode.getMessage() + "\n";
    
        if (currentDialogueNode.hasOptions()) {
            for (Map.Entry<Integer, DialogueNode> entry : currentDialogueNode.getOptions().entrySet()) {
                int choice = entry.getKey();
                gamePanel.ui.currentDialogue += currentDialogueNode.getOptionText(choice) + " (" + choice + ")\n";
            }
        }
    }

    public void handleChoice(int choice) {
        if (currentDialogueNode.hasOption(choice) && gamePanel.ui.dialogueCharacterImage != gamePanel.player.down0) {
            gamePanel.ui.currentDialogue = gamePanel.player.entityName + ": "  + currentDialogueNode.getOptionText(choice);
            gamePanel.ui.dialogueCharacterImage = gamePanel.player.down0;
            currentDialogueNode = currentDialogueNode.getOption(choice);
            // speak();
        }
    }

    public void resetDialogue() {
        currentDialogueNode = rootDialogueNode;
    }

    // THIS ONLY UPDATES THE NPCS
    public void update() {
        utilTool.updateNPCDirection(this);
        utilTool.updateTileCollision(this);

        if (collisionToggle == false) {
            switch (direction) {
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
            }
        }

    }

    // ENTITY DRAWING
    public void draw(Graphics2D g2d) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        boolean withinCameraBounds = utilTool.EntityWithinScreenBounds(this);
        if (withinCameraBounds) {
            BufferedImage currentImage = utilTool.updateSpriteImage(this);
            g2d.drawImage(currentImage, screenX, screenY, entityImageWidth, entityImageHeight, null);
            utilTool.entityNameAlignment(g2d, screenX, screenY, entityName);
        }
    }

}
