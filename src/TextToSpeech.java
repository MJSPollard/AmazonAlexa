import java.io.*;

/**
 * Much of this code is based on David Wakeling's Workshops, modified to suit our purposes.
 */
class TextToSpeech {
    private final static String LANG   = "en-US";
    private final static String GENDER = "Female";
    private final static String OUTPUT = "out.wav";
    private final static String FORMAT = "riff-16khz-16bit-mono-pcm";

    /**
     * Method to convert string to an audio byte array of the spoken text
     * 
     * @param token an access token for the Microsoft Cognitive servers
     * @param text the text to translate
     * @param lang the language to translate the text in
     * @param gender the gender of the returned language
     * @param format the audio format preferred
     * @return the byte array containing audio information
     */
    static byte[] synthesizeSpeech( String token, String text
            , String lang,  String gender
            , String format ) throws IOException {
        final String method = "POST";
        final String url = "https://speech.platform.bing.com/synthesize";
        final byte[] body
                = ( "<speak version='1.0' xml:lang='en-GB'>"
                + "<voice xml:lang='" + lang   + "' "
                + "xml:gender='"      + gender + "' "
                + "name='Microsoft Server Speech Text to Speech Voice"
                + " (en-US, ZiraRUS)'>"
                + text
                + "</voice></speak>" ).getBytes();
        final String[][] headers
                = { { "Content-Type"             , "application/ssml+xml"        }
                , { "Content-Length"           , String.valueOf( body.length ) }
                , { "Authorization"            , "Bearer " + token             }
                , { "X-Microsoft-OutputFormat" , format                        }
        };
        return HTTPConnect.httpConnect( method, url, headers, body );
    }

    /**
     * Method to write an audio byte array to a wav file specified
     * 
     * @param buffer the audio byte array to use
     * @param name the filename in which to write to
     */
    static void writeData( byte[] buffer, String name ) {
        try {
            File file = new File( name );
            FileOutputStream fos  = new FileOutputStream( file );
            DataOutputStream dos  = new DataOutputStream( fos );
            dos.write( buffer );
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to convert a given string to an audio file
     *
     * @param text the string to convert
     * @return the audio file in which the converted text is stored
     */
    static String convertStringToSpeech(String text) {
        try {
            final String token = HTTPConnect.renewAccessToken();
            final byte[] speech = synthesizeSpeech(token, text, LANG, GENDER, FORMAT);
            writeData(speech, OUTPUT);
            AudioOutput.playSound(OUTPUT);
            return OUTPUT;
        } catch (IOException e) {
            AudioOutput.playSound(Echo.class.getResourceAsStream("cant_answer.wav"));
            System.out.println(e.getMessage());
            System.out.println("Continuing as normal..");
            return null;
        }
    }
}