package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL sounds[] = new URL[30];
    KeyInput keyInput;
    FloatControl fc;
    boolean volumeUp, volumeDown;
    public float currentVolume;

    long lastVolumeChangeTime = 0;
    long volumeChangeInterval = 100; // MILLISECONDS

    GamePanel gamePanel;
    Utility utilTool;

    public boolean paused = false;

    public Sound(GamePanel gamePanel, KeyInput keyInput, Utility utilTool) {
        sounds[0] = getClass().getResource("/sounds/MenuMusic.wav");
        sounds[1] = getClass().getResource("/sounds/BackgroundMusic.wav");
        this.gamePanel = gamePanel;
        this.keyInput = keyInput;
        this.utilTool = utilTool;
    }

    public void setSound(int idx) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sounds[idx]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void playBackgroundMusic(int idx) {
        setSound(idx);
        play();
        loop();
    }

    public void togglePause() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); 
            paused = true;
        } else if (paused) {
            clip.start(); 
            paused = false;
        }

    }

}
