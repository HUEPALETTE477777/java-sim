package main.objects;

import main.GamePanel;
import main.Utility;

public class OBJ_KNIFE extends Object {

    public OBJ_KNIFE(GamePanel gamePanel, Utility utilTool) {
        super(gamePanel, utilTool);
        name = "Knife";
        
        fetchImage();
    }

    public void fetchImage() {
        gamePanel.assets.setUpObjectImages(this, name);
    }
    

    
}
