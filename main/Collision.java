package main;

import entity.Entity;

public class Collision {
    GamePanel gamePanel;

    public int collidingNPCIndex = -1;

    public Collision(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTileCollision(Entity entity) {
        // GETTING THE WORLD X FOR THE COLLISION BOX
        int entityCollisionLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityCollisionRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityCollisionTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityCollisionBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        // TILE ROWS AND COLS OF WHAT THEY ARE COLLIDING INTO
        int entityLeft = entityCollisionLeftWorldX / gamePanel.tileSize;
        int entityRight = entityCollisionRightWorldX / gamePanel.tileSize;
        int entityTop = entityCollisionTopWorldY / gamePanel.tileSize;
        int entityBottom = entityCollisionBottomWorldY / gamePanel.tileSize;

        int tileTouch1, tileTouch2;

        switch (entity.direction) {
            case "left":
                entityLeft = (entityCollisionLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileTouch1 = gamePanel.tileHandler.mapTileNum[entityTop][entityLeft]; // TOP LEFT TILE
                tileTouch2 = gamePanel.tileHandler.mapTileNum[entityBottom][entityLeft]; // BOTTOM LEFT TILE
                if (gamePanel.tileHandler.tile.get(tileTouch1).collision
                        || gamePanel.tileHandler.tile.get(tileTouch2).collision) {
                    entity.collisionToggle = true;
                }
                break;
            case "right":
                entityRight = (entityCollisionRightWorldX + entity.speed) / gamePanel.tileSize;
                tileTouch1 = gamePanel.tileHandler.mapTileNum[entityTop][entityRight]; // TOP RIGHT TILE
                tileTouch2 = gamePanel.tileHandler.mapTileNum[entityBottom][entityRight]; // BOTTOM RIGHT TILE
                if (gamePanel.tileHandler.tile.get(tileTouch1).collision
                        || gamePanel.tileHandler.tile.get(tileTouch2).collision) {
                    entity.collisionToggle = true;
                }
                break;
            case "up":
                entityTop = (entityCollisionTopWorldY - entity.speed) / gamePanel.tileSize;
                tileTouch1 = gamePanel.tileHandler.mapTileNum[entityTop][entityLeft]; // TOP LEFT TILE
                tileTouch2 = gamePanel.tileHandler.mapTileNum[entityTop][entityRight]; // TOP RIGHT TILE
                if (gamePanel.tileHandler.tile.get(tileTouch1).collision
                        || gamePanel.tileHandler.tile.get(tileTouch2).collision) {
                    entity.collisionToggle = true;
                }
                break;
            case "down":
                entityBottom = (entityCollisionBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileTouch1 = gamePanel.tileHandler.mapTileNum[entityBottom][entityLeft]; // BOTTOM LEFT TILE
                tileTouch2 = gamePanel.tileHandler.mapTileNum[entityBottom][entityRight]; // BOTTOM RIGHT
                if (gamePanel.tileHandler.tile.get(tileTouch1).collision
                        || gamePanel.tileHandler.tile.get(tileTouch2).collision) {
                    entity.collisionToggle = true;
                }
                break;
        }
    }

    // PLAYER TO NPC COLLISIONs
    public void playerToNPCCollision(Entity player, Entity[] entityArray) {
        for (int i = 0; i < entityArray.length; i++) {
            Entity currentEntity = entityArray[i];
            if (currentEntity != null) {
                if (isColliding(currentEntity, player)) {
                    collidingNPCIndex = i;
                    if (gamePanel.gameState == gamePanel.PLAY_STATE && gamePanel.keyInput.openNPCPrompt) {
                        gamePanel.gameState = gamePanel.DIALOGUE_STATE;
                        currentEntity.collisionToggle = true;
                        currentEntity.speed = 0;
                        currentEntity.speak(); // MUST INCLUDE THIS LINE TO INITIALLY SPEAK
                        gamePanel.ui.dialogueCharacterImage = currentEntity.down0;
                        System.out.println(
                                "CURRENT ENTITY: " + currentEntity.entityName + " " + currentEntity.collisionToggle);
                    } else {
                        currentEntity.speed = 1;
                        collidingNPCIndex = -1;
                    }
                }
            }
        }
    }

    public int getCollidingNPCIndex() {
        return collidingNPCIndex;
    }

    // USED FOR PLAYER TO NPC COLLISION!
    public boolean isColliding(Entity entity1, Entity entity2) {
        int entity1Left = entity1.worldX + entity1.collisionBox.x;
        int entity1Right = entity1.worldX + entity1.collisionBox.x + entity1.collisionBox.width;
        int entity1Top = entity1.worldY + entity1.collisionBox.y;
        int entity1Bottom = entity1.worldY + entity1.collisionBox.y + entity1.collisionBox.height;

        int entity2Left = entity2.worldX + entity2.collisionBox.x;
        int entity2Right = entity2.worldX + entity2.collisionBox.x + entity2.collisionBox.width;
        int entity2Top = entity2.worldY + entity2.collisionBox.y;
        int entity2Bottom = entity2.worldY + entity2.collisionBox.y + entity2.collisionBox.height;

        return entity1Right > entity2Left &&
                entity1Left < entity2Right &&
                entity1Bottom > entity2Top &&
                entity1Top < entity2Bottom;
    }

}
