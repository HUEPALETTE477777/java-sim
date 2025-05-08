package entity;

/** 
	DIALOGUE CLASS! A TRIBUTE TO STEVEN
	EACH DIALOGUE NODE CONTAINS A HASHMAP OF A CHOICE (KEY) AND POINTS TO THE NEXT DIALOGUE NODE,
    AND ADDITIONALLY A OPTION TEXT FOR DISPLAY
	ANOTHER HASHMAP IS NEEDED TO CONTAIN THE OPTION TEXTS FOR THE CURRENT DIALOGUE NODE, WHICH WE MAP OVER
	LARA SPANK: APRIL 7 2025
 **/

import java.util.HashMap;
import java.util.Map;

public class DialogueNode {
    public String message;
    public Map<Integer, String> optionTexts; 
    public Map<Integer, DialogueNode> options;

    public DialogueNode(String message) {
        this.message = message;
        this.optionTexts = new HashMap<>();
        this.options = new HashMap<>();
    }

    public void addOption(int choice, String optionText, DialogueNode nextNode) {
        optionTexts.put(choice, optionText); 
        options.put(choice, nextNode);
    }

    public DialogueNode getOption(int choice) { return options.get(choice); }
    public String getMessage() { return message; }
    public String getOptionText(int choice) { return optionTexts.get(choice);}
    public Map<Integer, DialogueNode> getOptions() { return options; }
    public Map<Integer, String> getOptionTexts() { return optionTexts;}
    public boolean hasOption(int choice) { return options.containsKey(choice); }
    public boolean hasOptions() { return !options.isEmpty(); }

}

