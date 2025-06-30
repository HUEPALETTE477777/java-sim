package entity.NPC;

import entity.DialogueNode;
import entity.Entity;
import main.GamePanel;
import main.Utility;

public class NPC_Nicole extends Entity {
    public NPC_Nicole(GamePanel gamePanel, Utility utilTool) {
        super(gamePanel, utilTool);
        collisionBox.x = 12;
        collisionBox.y = 21;
        collisionBox.width = 24;
        collisionBox.height = 42;

        entityName = "F*MALE";

        setDialogueTree();
        gamePanel.assets.setUpEntityImages(this, "npcs", "Nicole");
    }

    public void setDialogueTree() {
        rootDialogueNode = new DialogueNode("who is this bloxfruits noob");

        rootDialogueNode.addOption(1, "ARE YOU A FEMALE?", new DialogueNode("yes"));
        rootDialogueNode.addOption(2, "I HATE FEMALES", new DialogueNode("nice to know boy"));

        DialogueNode d1 = rootDialogueNode.getOption(1);
        d1.addOption(1, "BIOLOGICAL FEMALE?", new DialogueNode("yes jerk!!"));
        d1.addOption(2, "LETS SMASH RN", new DialogueNode("OFC LETS SMASH"));

        DialogueNode d2 = rootDialogueNode.getOption(2);
        d2.addOption(1, "DON'T CALL ME BOY!", new DialogueNode("xd"));
        d2.addOption(2, "WHAT IS UR CUP SIZE", new DialogueNode("b cup"));

        // TEMPORARILY TEST ROOT -> CHOICE 2 -> CHOICE 2 -> ADD SINGLE OPTION
        // DialogueNode d3 = d2.getOption(2);
        // d3.addOption(1, "Do you want to come over to my place, baby?", new
        // DialogueNode("Yes.. your hair is so fluffy xd"));

        currentDialogueNode = rootDialogueNode;
    }

    public void speak() {
        super.speak();
    }
}
