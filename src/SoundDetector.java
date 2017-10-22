import javax.sound.sampled.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class SoundDetector implements Runnable {
    private static final int     TIMER           = 5;     /* secs */
    private static final String  FILENAME        = "temp.wav";

    // For now = 0.1, will change later to dynamically adapt
    private static volatile float THRESHOLD;
    private static final int     SAMPLE_RATE     = 16000; /* MHz  */
    private static final int     SAMPLE_SIZE     = 16;    /* bits */
    private static final int     SAMPLE_CHANNELS = 1;     /* mono */
    private AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, SAMPLE_CHANNELS, true, true);
    private AudioInputStream ais;
    private SoundDetectionThread soundDetector;
    boolean canRecord = true;

    private ArrayList<ActionListener> listeners= new ArrayList<>();

    boolean running;
    private TargetDataLine line;


    /**
     * Method to calibrate the microphone's threshold to just above the background RMS level
     * 
     * @param detector the SoundDetectionThread used to check for incoming audio
     */
    private void calibrateMic(SoundDetectionThread detector) {
        // This is the lowest level possible
        THRESHOLD = -1f;
        while (detector.soundDetected()) {
            THRESHOLD += 1.05f;
        }
        // Adding a small amount above the background level as the activation level
        THRESHOLD += 0.011f;
        System.out.println("Calibrated the threshold as " + THRESHOLD);
    }

    /**
     * Run method of the thread, will listen for audio whilst in listening mode & record audio if it hears anything
     */
    @Override
    public void run() {
        running = true;
        try {
            // Set up a new line to the microphone
            final int bufferSize = format.getFrameSize() * SAMPLE_RATE;

            line = AudioSystem.getTargetDataLine(format);
            line.open(format, bufferSize);
            line.start();

            // Start a new Thread to continually monitor for sound
            ais = new AudioInputStream(line);
            soundDetector = new SoundDetectionThread(line, bufferSize);
            soundDetector.start();
            calibrateMic(soundDetector);
            System.out.println("Started silenceDetector");

            // Continually check for any sound detection
            while (running) {
                if (soundDetector.soundDetected() && canRecord) {
                    System.out.println("Detected Audio, starting recording..");
                    startRecording();
                }
            }
        } catch (LineUnavailableException e) {
            System.out.println("The TargetDataLine for the microphone was unavailable - are you sure it's plugged in?");
            e.printStackTrace();
            System.exit(1);
        } finally {
            // Close the line each time, so it can be accessed again by a new Thread when needed
            line.close();
        }
    }

    /**
     * Method to record a given amount of audio (TIMER) and store it to a file (FILENAME) as a wave file.
     * Will also stop recording before the timer if no sound has been detected.
     */
    void startRecording() {
        try {
            // Record the stream into a new file
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int bufferSize = SAMPLE_RATE * ais.getFormat().getFrameSize();
            byte buffer[] = new byte[bufferSize];

            int counter = TIMER;
            // Record whilst there is a sound detected and to a maximum of TIMER seconds
            while (counter > 0 && soundDetector.soundDetected()) {
                counter--;
                int n = ais.read(buffer, 0, buffer.length);
                if (n > 0) {
                    bos.write(buffer, 0, n);
                } else {
                    break;
                }
            }

            // Convert the recorded byte array and write it to a file
            // A file was used rather than storing the byte array in main memory so we can include further functionality
            // later on, such as replaying a clip.
            byte[] ba = bos.toByteArray();
            InputStream is = new ByteArrayInputStream(ba);
            AudioInputStream ais = new AudioInputStream(is, format, ba.length);
            File file = new File(FILENAME);
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);

            // Call all listeners and notify them that a new AudioFile has been recorded
            System.out.println("Recorded a new audio file, notifying listeners..");
            SoundRecordedEvent event = new SoundRecordedEvent(this, 1, "soundDetected");
            for (ActionListener listener : listeners) {
                listener.actionPerformed(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to register as an EventListener for any new sound recordings
     * 
     * @param listener the EventListener to register
     * @return true if successful
     */
    boolean registerRecordingListener(ActionListener listener) {
        return listeners.add(listener);
    }

    /**
     * Method to unregister as an EventListener for any new sound recordings
     * 
     * @param listener the EventListener to unregister
     * @return true if successful
     */
    boolean unregisterRecordingListener(ActionListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Method to re-enable the microphone after completely disabling it
     */
    void enableMic() {
        if (!soundDetector.isAlive()) {
            soundDetector = new SoundDetectionThread(line, ais.getFormat().getFrameSize() * SAMPLE_RATE);
        }
    }

    /**
     * Method to be called before entering answer mode to disable any recording (prevents it from recording itself)
     */
    void pauseForAnswer() {
        canRecord = false;
    }

    /**
     * Method to be called after completing an answer to re-allow for any audio recording
     */
    void resumeAfterAnswer() {
        canRecord = true;
    }

    /**
     * Method should be called to disable the microphone's recording
     */
    void disableMic() {
        running = false;
    }

    class SoundDetectionThread extends Thread {
        private TargetDataLine line;
        private int bufferSize;
        private float lastAmplitude;

        /**
         * Constructor to allow for the SoundDetectionThread to access the same audio input as the recording thread
         * 
         * @param line The TargetDataLine to record from
         * @param bufferSize The buffer size to read into
         */
        SoundDetectionThread(TargetDataLine line, int bufferSize) {
            this.line = line;
            this.bufferSize = bufferSize;
        }

        /**
         * Method to check if the microphone's audio input is above a threshold level
         * 
         * @return true if sound has been detected
         */
        boolean soundDetected() {
            return lastAmplitude > THRESHOLD;
        }

        /**
         * Method to continually read from the TargetDataLine and detect the RMS value of the input
         */
        @Override
        public void run() {
            while (running) {
                byte[] buf = new byte[bufferSize];
                float[] samples = new float[bufferSize / 2];
                while (running) {
                    int b = line.read(buf, 0, buf.length);
                    for (int i = 0, s = 0; i < b; ) {
                        int sample = 0;

                        // Converting the bytes to floats
                        sample |= buf[i++] << 8;
                        sample |= buf[i++] & 0xFF;

                        // Reducing it to the range -1 to +1
                        samples[s++] = sample / 32768f;
                    }

                    // Calculating the RMS amplitude
                    float rms = 0f;
                    float peak = 0f;
                    for (float sample : samples) {
                        float abs = Math.abs(sample);
                        if (abs > peak) {
                            peak = abs;
                        }
                        rms += sample * sample;
                    }
                    rms = (float) Math.sqrt(rms / samples.length);
                    lastAmplitude = rms;
                    try {
                        // Sleep temporarily to prevent the system overwhelming the OS
                        sleep(2);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted exception - this shouldn't have happened.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        }
    }
}