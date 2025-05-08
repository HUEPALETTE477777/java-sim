package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import main.objects.ObjectHandler;
import tile.TileHandler;
import ui.UI;

public class GamePanel extends JPanel implements Runnable {
    // TILE SIZE + WORLD VARIABLES
    public final int originalTileSize = 32;
    public final int scale = 2;

    public final int tileSize = originalTileSize * scale; // 64 pixels (SCALES FOR EVERY TILE)
    public final int SCREEN_WIDTH = 900;
    public final int SCREEN_HEIGHT = 690;

    // SCREEN ROWS/COLS
    public final int maxScreenRow = SCREEN_WIDTH / tileSize; // 20 ROWS
    public final int maxScreenCol = SCREEN_HEIGHT / tileSize; // 15 COLUMNS

    // WORLD ROWS/COLS
    public final int maxWorldRow = 30;
    public final int maxWorldCol = 30;

    // KRAP FPS
    public int fpsUpdate = 0;

    // GAME STATE, SHOULD BE ENUMS INSTEAD JERK.. NOT CHANGING NOW
    public int gameState;
    public final int MENU_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOGUE_STATE = 3;

    // GAME COMPONENTS
    public TileHandler tileHandler = new TileHandler(this);
    public KeyInput keyInput = new KeyInput(this);
    public Utility utilTool = new Utility(this, keyInput);
    public Assets assets = new Assets(this, utilTool);
    public Player player = new Player(this, keyInput, utilTool);
    public Sound sound = new Sound(this, keyInput, utilTool);

    public Entity[] npc = new Entity[10];
    public ObjectHandler[] obj = new ObjectHandler[10];

    public UI ui = new UI(this, player, keyInput, tileHandler, sound, npc);
    public Collision collision = new Collision(this);

    public Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true); // RENDER FRAMES AS A BUFFER (LOADING FRAMES BEFORE THE NEXT)

        this.setFocusable(true);
        this.addKeyListener(keyInput);

        assets.setNPC();
        assets.setObjects();
        
        sound.playBackgroundMusic(0);
        gameState = MENU_STATE;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double targetTick = 200.0;
        double targetFPS = 69420; // ARBITRARY NUMBER, CAN LIMIT AS WELL TO SAVE CPU TIME
        double nsPerTick = 1e9 / targetTick;
        double nsPerFrame = 1e9 / targetFPS;

        long lastTime = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;

        long lastTimer = System.currentTimeMillis();
        int ticks = 0;
        int frames = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - lastTime) / nsPerTick;
            deltaF += (currentTime - lastTime) / nsPerFrame;
            lastTime = currentTime;

            if (deltaU >= 1.0) {
                update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1.0) {
                repaint();
                frames++;
                deltaF--;
            }

            // THREAD SLEEP WILL DRIFT!
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                fpsUpdate = frames;
                System.out.println("FPS: " + frames + " TICKS: " + ticks);
                frames = 0;
                ticks = 0;
                lastTimer += 1000;
            }

        }
    }

    public void update() {
        if (utilTool.isPlayState()) {
            player.update();
            ui.update(fpsUpdate);

            for (Entity ent : npc) {
                if (ent != null) {
                    ent.update();
                }
            }

        }

        // if (utilTool.isPauseState()) {

        // }

        // if (utilTool.isDialogueState()) {
        // int collidingNPCIndex = this.collision.getCollidingNPCIndex();
        // Entity npc = this.npc[collidingNPCIndex];
        // npc.update();
        // }

        // if (utilTool.isMenuState()) {

        // }
    }

    // BUILT IN JAVA CLASS TO DRAW
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        tileHandler.draw(g2d);

        for (ObjectHandler entry : obj) {
            if (entry != null) {
                entry.draw(g2d);
            }
        }

        for (Entity ent : npc) {
            if (ent != null) {
                ent.draw(g2d);
            }
        }

        player.draw(g2d);
        ui.draw(g2d);

        g2d.dispose(); // FREE UP MEMORY AFTER DRAWING
    }

}