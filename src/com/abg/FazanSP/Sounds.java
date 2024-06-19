package com.abg.FazanSP;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Sounds {
    static Clip clip;
    static String filePath;
    String status;
    AudioInputStream audioInputStream;

    // constructor to initialize streams and clip
    public Sounds(String soundFile)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        filePath = soundFile;
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);
    }

    static void Sounds(String effectName) {
        try {
            // Close the previous clip if it's still open
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();}

            filePath = "src/com/abg/resources/" + effectName + ".wav";
            Sounds sound = new Sounds(filePath);
            for (double stop = System.nanoTime() + (double) TimeUnit.SECONDS.toNanos(1) / 100; stop > System.nanoTime(); ) {
                sound.play();
            }
        } catch (Exception ex) {
            System.out.println("Eroare sunet.");
            // ex.printStackTrace();
        }
    }

    // Method to play the audio
    public void play() {
        //start the clip
        clip.start();
        status = "play";
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
    }
}