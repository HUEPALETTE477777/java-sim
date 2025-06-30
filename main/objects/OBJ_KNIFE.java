package main.objects;

import main.GamePanel;
import main.Utility;

public class OBJ_Knife extends Object {
    public OBJ_Knife(GamePanel gamePanel, Utility utilTool) {
        super(gamePanel, utilTool);
        name = "Knife";
        this.worldX = 128;
        this.worldY = 128;

        fetchImage();
    }

}
