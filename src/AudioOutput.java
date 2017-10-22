import javax.sound.sampled.*;
import java.io.*;

/**
 * Class responsible for any AudioOutput. Has static methods to play the files
 */
class AudioOutput {
    static Clip clip;
    static Clip clipNoListener;

    static {
        try {
            clip = AudioSystem.getClip();
            clipNoListener = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.out.println("There was an error getting the output audio line!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to play the sound from an AudioInputStream
     * @param ais the stream to play from
     */
    static void playSound(AudioInputStream ais) {
        try {
            // Close any already opened clip
            clip.close();
            clip.open(ais);
            clip.start();
        } catch (IOException | LineUnavailableException e) {
            System.out.println("There was an error getting the output audio line!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method used to play sound without notifying any LineListeners
     *
     * @param ais the AudioInputStream to play
     */
    static void playSoundWithoutListeners(AudioInputStream ais) {
        try {
            // Close any already opened clip
            clipNoListener.close();
            clipNoListener.open(ais);
            clipNoListener.start();
        } catch (IOException | LineUnavailableException e) {
            System.out.println("There was an error getting the output audio line!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to add LineListeners to be aware of any changes to AudioOutput
     *
     * @param listener the listener to add
     */
    static void addLineListener(LineListener listener) {
        clip.addLineListener(listener);
        clipNoListener.addLineListener(listener);
    }

    /**
     * Method to remove and LineListeners
     *
     * @param listener the listener to remove
     */
    static void removeLineListener(LineListener listener) {
        clip.removeLineListener(listener);
        clipNoListener.addLineListener(listener);
    }

    /**
     * Method used to instantly stop any audio output
     */
    static void stopAudio() {
        clip.close();
        clipNoListener.close();
    }

    /**
     * Method to play sound from an InputStream
     *
     * @param is the InputStream to play from
     */
    static void playSound(InputStream is) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
            playSound(ais);
        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("There was an error outputting the audio.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method plays sound without notifying any LineListeners
     *
     * @param is the input stream to play
     */
    static void playSoundWithoutListeners(InputStream is) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
            playSoundWithoutListeners(ais);
        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("There was an error outputting the audio.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to play sound from a filepath
     *
     * @param path the filepath to check for the audio in
     */
    static void playSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            playSound(ais);
        } catch (FileNotFoundException e) {
            System.out.println("The file given was not found.");
            e.printStackTrace();
            System.exit(1);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("The filetype found was not supported.");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("There was an IOException");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method plays the resource continually.  Linked to EchoTimer for whether it should play or not
     *
     * @param is the resource stream to play
     */
    static void playTimerLooping(InputStream is) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            while (EchoTimer.shouldPlay()) {
                Thread.sleep(10);
            }
            clip.stop();
        } catch (UnsupportedAudioFileException | LineUnavailableException | InterruptedException | IOException e) {
            System.out.println("There was an error outputting the audio.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}