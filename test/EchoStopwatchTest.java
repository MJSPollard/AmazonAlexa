import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for EchoStopwatch. Contains two tests:
 * 
 * 1. Tests that the startStopwatch method works as intended
 * 2. Tests that the stopStopwatch method works as intended
 */
public class EchoStopwatchTest {
    
    public EchoStopwatchTest() {
    }

    /**
     * Test to check that the startStopwatch method works as intended
     */
    @Test
    public void testStartStopwatch() {
        EchoStopwatch esw = new EchoStopwatch(); // Instance to test created
        
        long before = esw.pastTime;
        esw.startStopwatch();
        long after = esw.pastTime;
        
        assertTrue("pastTime should change value after stopwatch started", before != after);
    }

    /**
     * Test to check that the stopStopwatch method works as intended
     */
    @Test
    public void testStopStopwatch() {
        EchoStopwatch esw1 = new EchoStopwatch(); // Instance to test created
        
        // test stopping the stopwatch before it has been started
        esw1.pastTime = -1;        
        boolean bool1 = esw1.stopStopwatch();
        assertFalse("bool1 should be false as pastTime == -1", bool1);
        
        // test stopping the stopwatch after it has been started
        esw1.startStopwatch();
        boolean bool2 = esw1.stopStopwatch();
        assertTrue("bool2 should be true as pasteTime != -1", bool2);
    }
    
}
