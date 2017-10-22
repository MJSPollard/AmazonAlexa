import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class for TextToSpeech. Contains three tests:
 * 
 * 1. Tests that the synthesizeSpeech method works as intended
 * 2. Tests that the writeData method works as intended
 * 3. Tests that the convertStringToSpeech method works as intended
 */
public class TextToSpeechTest {
    
    public TextToSpeechTest() {
    }

    /**
     * Test to check that the synthesizeSpeech method works as intended
     * @throws java.io.IOException
     */
    @Test
    public void testSynthesizeSpeech() throws IOException {
        TextToSpeech tts = new TextToSpeech(); // Instance to test created
        
        // Variables defined to test the instance
        String langTest   = "en-US";
        String genderTest = "Female";
        String formatTest = "riff-16khz-16bit-mono-pcm";
        String tokenTest = HTTPConnect.renewAccessToken();
        String textTest = "Test";
        
        byte[] resp = tts.synthesizeSpeech(tokenTest, textTest, langTest,
                genderTest, formatTest);
        assertNotNull("The byte array response can't be null", resp);
    }

    /**
     * Test to check that the writeData method works as intended 
     * @throws java.io.IOException
     */
    @Test
    public void testWriteData() throws IOException {
        TextToSpeech tts2 = new TextToSpeech(); // Instance to test created
        
        // Variables defined to test the instance
        String langTest2   = "en-US";
        String genderTest2 = "Female";
        String formatTest2 = "riff-16khz-16bit-mono-pcm";
        String tokenTest2 = HTTPConnect.renewAccessToken();
        String textTest2 = "Test";
        String outputTest = "outTest.wav";
      
        byte[] speechTest = tts2.synthesizeSpeech(tokenTest2, textTest2, 
                langTest2, genderTest2, formatTest2);
        
        tts2.writeData(speechTest, outputTest);
        
        File s = new File("outTest.wav");
        assertTrue(s.isFile());  // Check output file created
        
        s.delete(); //remove output test file
    }

    /**
     * Test to check that the convertStringToSpeech method works as intended
     */
    @Test
    public void testConvertStringToSpeech() {
        TextToSpeech tts3 = new TextToSpeech(); // Instance to test created
        
        String textTest3 = "What is apples plus bananas";
                
        tts3.convertStringToSpeech(textTest3);
        File s2 = new File("out.wav");
        assertTrue(s2.isFile());  // Check output file created
    }
}
