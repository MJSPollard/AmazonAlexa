import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for NewsTest. Contains one test:
 * 
 * 1. Tests that the playNews method works as intended
 */
public class NewsTest {
    
    public NewsTest() {
    }

    /**
     * Test to check that the playNews method works as intended
     */
    @Test
    public void testPlayNews() {
        News news1 = new News(); // Instance to test created

        boolean bool1 = news1.playNews();
        assertTrue("If news API working should return true", bool1);
    } 
}
