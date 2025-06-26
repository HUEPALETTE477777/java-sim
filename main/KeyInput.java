package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

import entity.Entity;

public class KeyInput implements KeyListener {
    public boolean up, left, right, down;
    public boolean HUD = true;
    public boolean openNPCPrompt = false;
    public boolean muteMusic = false;

    private final GamePanel gamePanel; // REQUIRED FOR GAME STATE!

    public KeyInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (Set.of(KeyEvent.VK_W, KeyEvent.VK_UP).contains(keyCode)) {
            up = true;
        }

        if (Set.of(KeyEvent.VK_S, KeyEvent.VK_DOWN).contains(keyCode)) {
            down = true;
        }

        if (Set.of(KeyEvent.VK_A, KeyEvent.VK_LEFT).contains(keyCode)) {
            left = true;
        }

        if (Set.of(KeyEvent.VK_D, KeyEvent.VK_RIGHT).contains(keyCode)) {
            right = true;
        }

        switch (keyCode) {
            case KeyEvent.VK_BACK_QUOTE -> gamePanel.utilTool.toggleBoolean(HUD);
            case KeyEvent.VK_B -> {
                gamePanel.utilTool.muteMusic(muteMusic);
            }
            case KeyEvent.VK_F7 -> gamePanel.utilTool.volumeUp();
            case KeyEvent.VK_F8 -> gamePanel.utilTool.volumeDown();
            case KeyEvent.VK_P -> togglePauseState();
            case KeyEvent.VK_ENTER -> handleEnterKey();
            case KeyEvent.VK_MINUS -> gamePanel.sound.togglePause();
            case KeyEvent.VK_E -> handleEKey();
            // DIALOGUE OPTIONS
            case KeyEvent.VK_1 -> handleDialogueChoice(1);
            case KeyEvent.VK_2 -> handleDialogueChoice(2);

            case KeyEvent.VK_F -> {
                if (gamePanel.gameState != gamePanel.MENU_STATE) {
                    int currentMusicIdx = gamePanel.sound.gameMusicIdx;
                    gamePanel.utilTool.playForwardMusic(currentMusicIdx);
                    System.out.println("CURRENT GAME MUSIC INDEX: " + gamePanel.sound.gameMusicIdx);
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (Set.of(KeyEvent.VK_W, KeyEvent.VK_UP).contains(keyCode)) {
            up = false;
        }

        if (Set.of(KeyEvent.VK_S, KeyEvent.VK_DOWN).contains(keyCode)) {
            down = false;
        }

        if (Set.of(KeyEvent.VK_A, KeyEvent.VK_LEFT).contains(keyCode)) {
            left = false;
        }

        if (Set.of(KeyEvent.VK_D, KeyEvent.VK_RIGHT).contains(keyCode)) {
            right = false;
        }

        switch (keyCode) {
            case KeyEvent.VK_E -> openNPCPrompt = false;
        }
    }

    public void togglePauseState() {
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            gamePanel.gameState = gamePanel.PAUSE_STATE;
            gamePanel.sound.stop();
        } else if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
            gamePanel.sound.play();
        }
    }

    public void handleEnterKey() {
        if (gamePanel.gameState == gamePanel.DIALOGUE_STATE) {
            Entity npc = gamePanel.utilTool.fetchCollidingNPC();
            gamePanel.ui.dialogueCharacterImage = npc.down0;
            if (npc != null) {
                npc.speak();
            }
            // if (!npc.currentDialogueNode.hasOptions()) {
            // npc.resetDialogue();
            // gamePanel.gameState = gamePanel.PLAY_STATE;
            // }
        }

        // FUTURE ME HAS TO DEAL WITH THIS LOGIC LATER BECAUSE WE WANT THE USER
        // TO BE ABLE TO GO BACK TO MENU, FOR NOW YOU CANNOT
        if (gamePanel.gameState == gamePanel.MENU_STATE) {
            gamePanel.gameState = gamePanel.PLAY_STATE;
            gamePanel.utilTool.playNewMusic(gamePanel.sound.gameMusicIdx);
        }
    }

    public void handleEKey() {
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            openNPCPrompt = true;
        }
        if (gamePanel.gameState == gamePanel.DIALOGUE_STATE) {
            Entity npc = gamePanel.utilTool.fetchCollidingNPC();
            npc.resetDialogue();
            gamePanel.gameState = gamePanel.PLAY_STATE;
            openNPCPrompt = false;
        }
    }

    public void handleDialogueChoice(int choice) {
        if (gamePanel.gameState == gamePanel.DIALOGUE_STATE) {
            Entity npc = gamePanel.utilTool.fetchCollidingNPC();
            if (npc != null) {
                npc.handleChoice(choice);
            }
        }
    }

}
