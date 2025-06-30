package entity.NPC;

import entity.DialogueNode;
import entity.Entity;
import main.GamePanel;
import main.Utility;

public class NPC_GaryPearlson extends Entity {
    public NPC_GaryPearlson(GamePanel gamePanel, Utility utilTool) {
        super(gamePanel, utilTool);

        collisionBox.x = 12;
        collisionBox.y = 21;
        collisionBox.width = 24;
        collisionBox.height = 42;

        entityName = "The Predatory Substitute";

        setDialogueTree();
        gamePanel.assets.setUpEntityImages(this, "npcs", "GaryPearlson");
    }


    public void setDialogueTree() {
        rootDialogueNode = new DialogueNode("HEYA!!11!");

        rootDialogueNode.addOption(1, "Who are you?", new DialogueNode("I'm Gary, the hottest guy in town!"));
        rootDialogueNode.addOption(2, "Nice to meet you!", new DialogueNode("Wow! You're really nice. Let's be besties!"));

        DialogueNode d1 = rootDialogueNode.getOption(1);
        d1.addOption(1, "You are indeed smoking hot..", new DialogueNode("Damn, you got good taste! Haha!"));
        d1.addOption(2, "I don't think so!", new DialogueNode("You're making me mad!"));

        DialogueNode d2 = rootDialogueNode.getOption(2);
        d2.addOption(1, "Hooray! I like the way your beer belly bulges through your shirt", new DialogueNode("Thanks!"));
        d2.addOption(2, "Thank you!! You wanna smash? ;)", new DialogueNode("Hehe, alright! ;)"));
        
        // TEMPORARILY TEST ROOT -> CHOICE 2 -> CHOICE 2 -> ADD SINGLE OPTION
        DialogueNode d3 = d2.getOption(2);
        d3.addOption(1, "Do you want to come over to my place, baby?", new DialogueNode("Yes.. your hair is so fluffy xd"));

        currentDialogueNode = rootDialogueNode;
    }
    

    public void speak() {
        super.speak();
    }
}