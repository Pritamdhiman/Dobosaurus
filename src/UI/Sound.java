package UI;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Sound {

    public static void playSound(String soundFile) {
        try {
            // Load the sound file as a URL or file path
            URL url = Sound.class.getResource("/" + soundFile);
            if (url == null) {
                url = new File(soundFile).toURI().toURL(); // If resource not found, try file system
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();

            // Get the audio format of the file
            AudioFormat format = audioIn.getFormat();
            System.out.println("Audio Format: " + format.toString());

            // Check if the format is supported, and if not, convert to a supported format
            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                AudioFormat newFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        format.getSampleRate(),
                        16,
                        format.getChannels(),
                        format.getChannels() * 2,
                        format.getSampleRate(),
                        false);
                audioIn = AudioSystem.getAudioInputStream(newFormat, audioIn);
            }

            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
