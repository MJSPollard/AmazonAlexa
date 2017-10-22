import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for SoundDetector. Contains 9 tests:
 *
 * 1. Tests that the startRecording method works as intended !
 * 2. Tests that the enableMic method works as intended !
 * 3. Tests that the pauseForAnswer method works as intended
 * 4. Tests that the resumeAfterAnswer method works as intended
 * 5. Tests that the disableMic method works as intended
 */
public class SoundDetectorTest {
    
    public SoundDetectorTest() {
    }

    /**
    * Test to check that the startRecording method works as intended
    */
    @Test(expected=NullPointerException.class)
    public void testStartRecording() {
        SoundDetector sd = new SoundDetector(); // Instance to test created
        
        sd.startRecording();
    }
    
    /**
    * Test to check that the enableMic method works as intended
    */
    @Test(expected=NullPointerException.class)
    public void testEnableMic() {
        SoundDetector sd = new SoundDetector(); // Instance to test created
        
        sd.enableMic();
    }

    /**
    * Test to check that the pauseForAnswer method works as intended
    */
    @Test
    public void testPauseForAnswer() {
        SoundDetector sd = new SoundDetector(); // Instance to test created
        
        sd.pauseForAnswer(); 
        
        boolean bool1 = sd.canRecord;
        assertFalse("canRecord should now be set to false" , bool1);
    }

    /**
    * Test to check that the resumeAfterAnswer method works as intended
    */
    @Test
    public void testResumeAfterAnswer() {
        SoundDetector sd = new SoundDetector(); // Instance to test created
        
        sd.resumeAfterAnswer();
        
        boolean bool1 = sd.canRecord;
        assertTrue("canRecord should now be set to true" , bool1);
    }

    /**
    * Test to check that the disableMic method works as intended
    */
    @Test
    public void testDisableMic() {
        SoundDetector sd = new SoundDetector(); // Instance to test created
        
        sd.disableMic();
        
        boolean bool1 = sd.running;
        assertFalse("running should now be set to false" , bool1);
    }   
}