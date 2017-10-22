import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for SoundRecordedEvent. Contains one test:
 * 
 * 1. Tests that the SoundRecordedEvent constructor works as intended
 */
public class SoundRecordedEventTest {
    
    public SoundRecordedEventTest() {
    }

    /**
     * Test to check that the SoundRecordedEvent constructor works as intended
     */
    @Test
    public void testSomeMethod() {
        SoundDetector sd = new SoundDetector(); // SoundDetector instance to test created
        
        SoundRecordedEvent event1 = new SoundRecordedEvent(sd, 1, "soundDetected");
        assertNotNull("event1 should not be null", event1);
    }
}
