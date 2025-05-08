package main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import main.objects.ObjectHandler;

public class Utility {
    GamePanel gamePanel;

    Entity entity;
    KeyInput keyInput;

    public Utility(GamePanel gamePanel, KeyInput keyInput) {
        this.gamePanel = gamePanel;
        this.keyInput = keyInput;
    }

    // GAME STATE UTILS
    public boolean isMenuState() {
        return gamePanel.gameState == gamePanel.MENU_STATE;
    }

    public boolean isPlayState() {
        return gamePanel.gameState == gamePanel.PLAY_STATE;
    }

    public boolean isPauseState() {
        return gamePanel.gameState == gamePanel.PAUSE_STATE;
    }

    public boolean isDialogueState() {
        return gamePanel.gameState == gamePanel.DIALOGUE_STATE;
    }

    // UPDATE COLLISION
    public void updateTileCollision(Entity entity) {
        entity.collisionToggle = false;
        gamePanel.collision.checkTileCollision(entity);
    }

    // GET THE NPC WE ARE CURRENTLY COLLIDING WITH
    public Entity fetchCollidingNPC() {
        int collidingNPCIndex = gamePanel.collision.getCollidingNPCIndex();
        if (gamePanel.npc[collidingNPCIndex] != null) {
            return gamePanel.npc[collidingNPCIndex];
        }
        return null;
    }

    // UPDATE NPC SPRITE
    public void updateNPCDirection(Entity entity) {
        this.entity = entity;

        entity.spriteCount++;
        if (entity.spriteCount >= 400 && isPlayState()) {
            Random rand = new Random();
            int value = rand.nextInt(100) + 1;

            if (value <= 25) {
                entity.direction = "up";
            }
            if (value <= 50 && value > 25) {
                entity.direction = "down";
            }
            if (value > 50 && value <= 75) {
                entity.direction = "left";
            }
            if (value > 75 && value <= 100) {
                entity.direction = "right";
            }
            entity.spriteCount = 0;
        }
    }

    public BufferedImage updateSpriteImage(Entity entity) {
        this.entity = entity;
        BufferedImage image = null;

        switch (entity.direction) {
            case "left":
                if (entity.spriteNum == 0) {
                    image = entity.left0;
                }
                if (entity.spriteNum == 1) {
                    image = entity.left1;
                }
                if (entity.spriteNum == 2) {
                    image = entity.left2;
                }
                break;
            case "right":
                if (entity.spriteNum == 0) {
                    image = entity.right0;
                }
                if (entity.spriteNum == 1) {
                    image = entity.right1;
                }
                if (entity.spriteNum == 2) {
                    image = entity.right2;
                }
                break;
            case "up":
                if (entity.spriteNum == 0) {
                    image = entity.up0;
                }
                if (entity.spriteNum == 1) {
                    image = entity.up1;
                }
                if (entity.spriteNum == 2) {
                    image = entity.up2;
                }
                break;
            case "down":
                if (entity.spriteNum == 0) {
                    image = entity.down0;
                }
                if (entity.spriteNum == 1) {
                    image = entity.down1;
                }
                if (entity.spriteNum == 2) {
                    image = entity.down2;
                }
                break;
        }
        return image;
    }

    public boolean isMovementKeysPressed() {
        return (keyInput.up || keyInput.down || keyInput.left || keyInput.right);
    }

    public void entityNameAlignment(Graphics2D g2d, int screenX, int screenY, String entityName) {
        g2d.setColor(Color.ORANGE);
        g2d.setFont(g2d.getFont().deriveFont(20f));

        FontMetrics fontMetric = g2d.getFontMetrics();
        int stringWidth = fontMetric.stringWidth(entityName);
        int entityNameX = screenX + (gamePanel.tileSize - stringWidth) / 2;
        int entityNameY = screenY;
        g2d.drawString(entityName, entityNameX, entityNameY);
    }

    // UI UTILS
    public boolean isHUD() {
        return keyInput.HUD;
    }

    public boolean EntityWithinScreenBounds(Entity entity) {
        return entity.worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                entity.worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                entity.worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                entity.worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;
    }

    public boolean ObjectWithinScreenBounds(ObjectHandler obj) {
        return obj.worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
            obj.worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
            obj.worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
            obj.worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY;
    }

    public int centerTextX(FontMetrics fontMetric, String text, Graphics2D g2d) {
        int stringWidth = (int) fontMetric.getStringBounds(text, g2d).getWidth();
        int xCords = (gamePanel.SCREEN_WIDTH / 2) - (stringWidth / 2);
        return xCords;
    }

    public int centerTextY(FontMetrics fontMetric, String text, Graphics2D g2d) {
        int stringHeight = (int) fontMetric.getStringBounds(text, g2d).getHeight();
        int yCords = (gamePanel.SCREEN_WIDTH / 2) - (stringHeight / 2);
        return yCords;
    }

    public void toggleBoolean(boolean input) {
        input = !input;
    }

    public void volumeUp() {
        gamePanel.sound.currentVolume += 1.0f;
        if (gamePanel.sound.currentVolume > 6.0f) {
            gamePanel.sound.currentVolume = 6.0f;
        }
        gamePanel.sound.fc.setValue(gamePanel.sound.currentVolume);
    }

    public void volumeDown() {
        gamePanel.sound.currentVolume -= 1.0f;
        if (gamePanel.sound.currentVolume <= -80.0f) {
            gamePanel.sound.currentVolume = -80.0f;
        }
        gamePanel.sound.fc.setValue(gamePanel.sound.currentVolume);
    }

    public void muteMusic() {
        gamePanel.sound.stop();
    }

}
