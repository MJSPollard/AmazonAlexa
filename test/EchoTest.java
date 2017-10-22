import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.event.ActionEvent;
import static org.junit.Assert.*;

/**
 * The test class for Echo. Contains two tests:
 * 
 * 1. Tests that the Echo constructor works as intended
 * 2. Tests that the actionPerformed method works as intended
 */
public class EchoTest {
    static Echo echo;

    @BeforeClass
    public static void setUp() {
        echo = new Echo();
    }

    @AfterClass
    public static void tearDown() {
        echo = null;
    }
    
    /**
     * Test to check that the Echo constructor works as intended
     */
    @Test
    public void testEcho() {
        // New echo instance shouldn't have null class variables
        assertNotNull(echo.detector);
        assertNotNull(echo.FILENAME);
        assertNotNull(echo.gui);
    }
    
    /**
     * Test to check that the actionPerformed method works as intended
     */
    @Test
    public void testActionPerformed() {
        ActionEvent event = new ActionEvent(this, 1, "soundDetected");
        echo.gui.setPowered(true);

        echo.FILENAME = getClass().getResource("timer_test.wav").getPath();
        System.out.println(echo.FILENAME);
        echo.actionPerformed(event);

        echo.FILENAME = getClass().getResource("news_test.wav").getPath();
        echo.actionPerformed(event);

        echo.FILENAME = getClass().getResource("start_stopwatch_test.wav").getPath();
        echo.actionPerformed(event);

        echo.FILENAME = getClass().getResource("stop_stopwatch_test.wav").getPath();
        echo.actionPerformed(event);
    }
}
