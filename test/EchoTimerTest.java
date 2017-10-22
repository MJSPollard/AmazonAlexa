import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for EchoTimer. Contains three tests:
 * 
 * 1. Tests that the startTimer method works as intended
 * 2. Tests that the shouldPlay method works as intended
 * 3. Tests that the stopPlaying method works as intended
 */
public class EchoTimerTest {
    
    public EchoTimerTest() {
    }

    /**
     * Test to check that the startTimer method works as intended
     */
    @Test
    public void testStartTimer() {
        EchoTimer et2 = new EchoTimer(); // Instance to test created
        
        // test an invalid timer input
        boolean return1 = et2.startTimer("Should return false");
        assertFalse("return1 should be false", return1);
        // test a timer input, in seconds
        boolean return2 = et2.startTimer("3 seconds");
        assertTrue("return2 should be true", return2);
        // test a timer input, in minutes
        boolean return3 = et2.startTimer("3 minutes");
        assertTrue("return3 should be true", return3);
        // test a timer input, in hours
        boolean return4 = et2.startTimer("3 hours");
        assertTrue("return4 should be true", return4);
        
    }

    /**
     * Test to check that the shouldPlay method works as intended
     */
    @Test
    public void testShouldPlay() {
        EchoTimer et1 = new EchoTimer(); // Instance to test created
        
        boolean shouldPlayBool = et1.shouldPlay;
        assertTrue("shouldPlay should be true", shouldPlayBool);
    }

    /**
     * Test to check that the stopPlaying method works as intended
     */
    @Test
    public void testStopPlaying() {
        EchoTimer et = new EchoTimer(); // Instance to test created
        
        boolean shouldPlayBool1 = et.shouldPlay;
        assertTrue("shouldPlay should have value true", shouldPlayBool1);

        et.stopPlaying();
        
        boolean shouldPlayBool2 = et.shouldPlay;
        assertFalse("shouldPlay should have value false", shouldPlayBool2);
    }   
}
