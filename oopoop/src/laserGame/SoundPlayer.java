package laserGame;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    
    private static Clip clip; // Store the Clip object globally
    
    /**
     * Plays the specified audio file.
     * @param filePath The path to the audio file.
     * @param volume The volume level (0.0 to 1.0).
     */
    public static void playSound(String filePath, float volume) {
        try {
            URL url = SoundPlayer.class.getResource(filePath);
            if (url == null) {
                throw new IOException("File not found: " + filePath);
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            // Adjust volume if FloatControl is supported
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);
            }
            
            clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Stops the currently playing audio.
     */
    public static void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
