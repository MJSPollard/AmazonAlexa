import java.io.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for Computational. Contains three tests:
 * 
 * 1. Tests that the solve method works as intended
 * 2. Tests that the urlEncode method works as intended
 * 3. Tests that the getAnswer method works as intended
 */
public class ComputationalTest {
    
    public ComputationalTest() {
    }
  
    /**
     * Test to check that the solve method works as intended
     * @throws java.io.IOException
     */
    @Test
    public void testSolve() throws IOException{
        Computational c = new Computational(); // Instance to test created
        
        String resp = c.solve("What is the melting point of silver?");
        assertNotNull("resp shouldn't be null", resp);

        Computational c1 = new Computational(); // Instance to test created
        
        String resp1 = c1.solve("asdfghjkl;");
        if (resp1.contains("success=\'false\'")){
            assertNotNull("resp shouldn't be null", resp1);        
        }
    }
    
    /**
     * Test to check that the urlEncode method works as intended
     */
    @Test
    public void testUrlEncode(){
        Computational c2 = new Computational(); // Instance to test created
        // Variable defined to test the method
        String question = "What is the melting point of silver?";
        
        String resp2 = c2.urlEncode(question);
        assertEquals("What+is+the+melting+point+of+silver%3F", resp2);
    }
    
    /**
     * Test to check that the getAnswer method works as intended
     */
    @Test
    public void testGetAnswer(){
        Computational c3 = new Computational(); // Instance to test created
        // Variable defined to test the method
        String question = "What is the melting point of silver?";
        
        String resp3 = c3.getAnswer(question);
        assertEquals("961.78 C  (degrees Celsius)", resp3);       
    }
}
    

