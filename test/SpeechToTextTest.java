import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for SpeechToText. Contains three tests:
 * 
 * 1. Tests that the recognizeSpeech method works as intended
 * 2. Tests that the readData method works as intended
 * 3. Tests that the getTextFromAudio method works as intended
 */
public class SpeechToTextTest {
    
    public SpeechToTextTest() {
    }

    /**
    * Test to check that the recognizeSpeech method works as intended
     * @throws java.lang.Exception
    */
    @Test
    public void testRecognizeSpeech() throws Exception {  
        
        // Variables defined to test the method
        final String tokenTest = HTTPConnect.renewAccessToken();
        String nameTest = "christmas.wav";
        byte[] bodyTest = SpeechToText.readData(nameTest);
        
        String result = SpeechToText.recognizeSpeech(tokenTest, bodyTest);
        assertNotNull("JSON string should not be null", result);
    }

    /**
    * Test to check that the readData method works as intended
    */
    @Test
    public void testReadData() {
        
        // Variables defined to test the method
        String nameTest = "christmas.wav";
        
        byte[] output = SpeechToText.readData(nameTest);
        assertNotNull("byte array should not be null", output);
    }

    /**
    * Test to check that the getTextFromAudio method works as intended
    */
    @Test
    public void testGetTextFromAudio() {

        // Variables defined to test the method
        String filenameTest = "christmas.wav";
        
        String expResult = "When is Christmas?";
        String result = SpeechToText.getTextFromAudio(filenameTest);    
        assertEquals("The resulting string is not what was expected", expResult, result);   
    }
}
