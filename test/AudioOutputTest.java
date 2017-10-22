import org.junit.Test;
import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

/**
 * The test class for AudioOutput. Contains eight tests:
 * 
 * 1. Tests that the playSound method works as intended, x4
 * 2. Tests that the playSoundWithoutListeners method works as intended, x2
 * 3. Tests that the stopAudio method works as intended
 * 4. Tests that the timerLooping method works as intended
 */
public class AudioOutputTest {
    
    public AudioOutputTest() {
    }
    
    /**
     * Test to check that the playSound method works as intended
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */   
    @Test
    public void testPlaySound() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException{
            
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        // playSound test with AudioInputStream
        URL url = this.getClass().getClassLoader()
                .getResource("audio_output_test.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                        
        ao.playSound(audioIn);
            
        // playSound test with InputStream
        InputStream inStream=this.getClass().getClassLoader()
                .getResourceAsStream("audio_output_test.wav");
        
        ao.playSound(inStream);  
        }
    
    /**
     * Test to check that the playSound method works as intended
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    @Test(expected=UnsupportedAudioFileException.class)
    public void testPlaySound2() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException{
            
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        URL url = this.getClass().getClassLoader()
                .getResource("audio_output_test.mp3");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            
        ao.playSound(audioIn);
    }
    
    /**
     * Test to check that the playSound method works as intended
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    @Test(expected=NullPointerException.class)
    public void testPlaySound3() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException{
            
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        URL url = this.getClass().getClassLoader()
                .getResource("not_a_file.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            
        ao.playSound(audioIn);
    }
    
    /**
     * Test to check that the playSound method works as intended
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    @Test //(expected=UnsupportedAudioFileException.class)
    public void testPlaySound4() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException{
            
        AudioOutput ao = new AudioOutput(); // Instance to test created
            
        InputStream inStream=this.getClass().getClassLoader()
                .getResourceAsStream("audio_output_test.wav");
        
        ao.playSound(inStream);
        
    }
    
    /**
     * Test to check that the playSoundWithoutListeners method works as intended
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    @Test
    public void testPlaySoundWithoutListeners() throws UnsupportedAudioFileException, 
            IOException, LineUnavailableException{
        
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        URL url = this.getClass().getClassLoader()
                .getResource("audio_output_test.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
          
        ao.playSoundWithoutListeners(audioIn);
            
        //playSound test with InputStream
        InputStream inStream=this.getClass().getClassLoader()
                .getResourceAsStream("audio_output_test.wav");
        
        ao.playSoundWithoutListeners(inStream);
    }
    
    /**
     * Test to check that the playSoundWithoutListeners method works as intended
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     * @throws javax.sound.sampled.LineUnavailableException
     */
    @Test(expected=UnsupportedAudioFileException.class)
    public void testPlaySoundWithoutListeners2() throws UnsupportedAudioFileException, 
            IOException, LineUnavailableException{
        
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        URL url = this.getClass().getClassLoader().getResource("audio_output_test.mp3");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            
        ao.playSoundWithoutListeners(audioIn);
        
    }
    
    /**
     * Test to check that the stopAudio method works as intended
     * @throws javax.sound.sampled.LineUnavailableException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     * @throws java.io.IOException
     */
    @Test
    public void testStopAudio() throws LineUnavailableException, UnsupportedAudioFileException,
            IOException{
       
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        //playSound test with AudioInputStream
        URL url = this.getClass().getClassLoader()
                .getResource("audio_output_test.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                        
        ao.playSound(audioIn);
        
        ao.stopAudio();
    }
    
    /**
     * Test to check that the playLooping method works as intended
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testPlayLooping() throws InterruptedException{
        
        AudioOutput ao = new AudioOutput(); // Instance to test created
        
        new Thread() {
            @Override
            public void run() {
                ao.playTimerLooping(getClass().getResourceAsStream("audio_output_test.wav"));
            }
        }.start();
        Thread.sleep(1000);
        
        ao.stopAudio();
    }
}