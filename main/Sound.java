package main;

import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    public ArrayList<URL> sounds = new ArrayList<>();
    KeyInput keyInput;
    FloatControl fc;
    boolean volumeUp, volumeDown;
    public float currentVolume;

    long lastVolumeChangeTime = 0;
    long volumeChangeInterval = 100; // MILLISECONDS

    GamePanel gamePanel;
    Utility utilTool;

    public boolean paused = false;
    public int gameMusicIdx = 1;
    public int currentAudioDuration = 0;

    public int currentAudioSecond = 0;
    private long lastSecondUpdate = 0;

    public Sound(GamePanel gamePanel, KeyInput keyInput, Utility utilTool) {
        sounds.add(getClass().getResource("/sounds/MenuMusic.wav"));
        sounds.add(getClass().getResource("/sounds/Dysfunctional - Tech N9ne.wav"));
        sounds.add(getClass().getResource("/sounds/Judas - Lady Gaga (Sped Up).wav"));
        sounds.add(getClass().getResource("/sounds/Industry Baby - Lil Nas X & Jack Harlow (Sped Up).wav"));

        this.gamePanel = gamePanel;
        this.keyInput = keyInput;
        this.utilTool = utilTool;
    }

    public void setSound(int idx) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sounds.get(idx));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            currentAudioDuration = getAudioDuration(audioInputStream);
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

    public void playBackgroundMusic() {
        setSound(0);
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

    public int getAudioDuration(AudioInputStream AIS) {
        AudioFormat format = AIS.getFormat();
        long frames = AIS.getFrameLength();

        double durationInSeconds = (frames + 0.0) / format.getFrameRate();
        int typeCast = (int) durationInSeconds;
        return typeCast;
    }

    public void update() {
        if (clip != null && clip.isRunning()) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - lastSecondUpdate >= 1000) { 
                currentAudioSecond = (int) (clip.getMicrosecondPosition() / 1000000);
                lastSecondUpdate = currentTimeMillis;
            }

            if (currentAudioSecond > currentAudioDuration) {
                currentAudioSecond = 0;
                loop();
            }
        }

    }

}
