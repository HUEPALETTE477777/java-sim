package main;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.NPC.NPC_GaryPearlson;
import entity.NPC.NPC_Nicole;
import main.objects.OBJ_Knife;
import main.objects.Object;

import java.awt.image.BufferedImage;

public class Assets {
    GamePanel gamePanel;
    Utility utilTool;

    public Assets(GamePanel gamePanel, Utility utilTool){
        this.gamePanel = gamePanel;
        this.utilTool = utilTool;
    }

    public void setNPC() {
        gamePanel.npc[0] = new NPC_GaryPearlson(gamePanel, utilTool);
        gamePanel.npc[0].worldX = 256;
        gamePanel.npc[0].worldY = 256;
        gamePanel.npc[0].speed = 1;
        gamePanel.npc[0].direction = "down";

        gamePanel.npc[1] = new NPC_Nicole(gamePanel, utilTool);
        gamePanel.npc[1].worldX = 256;
        gamePanel.npc[1].worldY = 250;
        gamePanel.npc[1].speed = 1;
        gamePanel.npc[1].direction = "right";
    }

    public void setObjects() {
        gamePanel.obj.add(new OBJ_Knife(gamePanel, utilTool));
    }

    /**********************************************************************************************************
        THE PLAYER IS AN EXCLUSION SINCE I AM LAZY AND DONT HAVE OTHER IMGS FOR ACTUAL NPC, ALL GOES DOWN
        PATH FORMAT + EXAMPLE:
        /images/npcs/GaryPearlson/GaryPearlson_down_0 
    **********************************************************************************************************/
    public void setUpEntityImages(Entity entity, String entityType, String entityName) {
        entity.left0 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_left_0");
        entity.left1 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");
        entity.left2 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");

        entity.right0 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_right_0");
        entity.right1 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");
        entity.right2 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");

        entity.down0 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");
        entity.down1 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");
        entity.down2 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");

        entity.up0 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_up_0");
        entity.up1 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");
        entity.up2 = ImageRetrieval("/images/" + entityType + "/" + entityName + "/" + entityName + "_down_0");
    }

    public void setUpObjectImages(Object obj, String objName) {
        obj.image = ImageRetrieval("/images/objects/" + objName);
    }

    // PLAYER CALLS THIS DIRECTLY
    public BufferedImage ImageRetrieval(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

}
