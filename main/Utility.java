package main;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import entity.Entity;
import main.objects.Object;

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

    public boolean isControlState() {
        return gamePanel.gameState == gamePanel.CONTROL_STATE;
    }

    // UPDATE COLLISION
    public void updateTileCollision(Entity entity) {
        entity.collisionToggle = false;
        gamePanel.collision.checkTileCollision(entity);
        gamePanel.collision.isCollidingWithItem(entity);
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

    public boolean ObjectWithinScreenBounds(Object obj) {
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

    public void muteMusic(boolean input) {
        if (!keyInput.muteMusic) {
            gamePanel.sound.stop();
            keyInput.muteMusic = true;
        } else {
            gamePanel.sound.play();
            keyInput.muteMusic = false;
        }
    }

    public void playForwardMusic(int gameMusicIdx) {
        int newGameMusicIdx = gameMusicIdx + 1;
        if (newGameMusicIdx > gamePanel.sound.sounds.size() - 1) {
            newGameMusicIdx = 1;
            gamePanel.sound.gameMusicIdx = newGameMusicIdx;
            playNewMusic(gamePanel.sound.gameMusicIdx);
        } else {
            gamePanel.sound.gameMusicIdx = newGameMusicIdx;
            playNewMusic(gamePanel.sound.gameMusicIdx);
        }

    }

    public void playNewMusic(int inputIndex) {
        gamePanel.sound.stop();
        gamePanel.sound.setSound(inputIndex);
        gamePanel.sound.play();
        gamePanel.sound.fc.setValue(gamePanel.sound.currentVolume);
        gamePanel.sound.loop();
    }

    public String extractSongName() {
        URL song = gamePanel.sound.sounds.get(gamePanel.sound.gameMusicIdx);
        String path = song.getPath();
        File file = new File(path);
        String fileName = file.getName();

        String decodedName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        int dotIndex = decodedName.lastIndexOf('.');
        if (dotIndex > 0) {
            decodedName = decodedName.substring(0, dotIndex);
        }

        return decodedName;
    }

    

}
