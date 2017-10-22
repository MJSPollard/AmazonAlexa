import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class will play the news for the user upon request
 * Powered by News API
 */
class News {
    private static int i=0;
    
    /**
     * Method to read out the news
     * @return boolean whether or not the news has been played 
     */
    static boolean playNews(){
        try {
            //Obtaining news API url with appropriate key
            URL url = new URL("https://newsapi.org/v1/articles?source=bbc-news"
                    + "&sortBy=top&apiKey=756f4949b8ba49558a3e3e8b3d420e63");
            
            //Set up connection between URL and application
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            
            
            //Set up buffered reader in order to interpret the JSON file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String news;
            
            //Read the news file into an empty string 'news'
            if((news=br.readLine()) !=null){
                news+=br.readLine();
            }
            
            else{
                //If the News API is down, a prerecorded message will play
                AudioOutput.playSound("noNews.wav");
            }
            
            //Check to see if the news request has gone through
            if (news.contains("\"status") && news.contains("\"ok")){
                
                //Find the title of each news story
                int index = news.indexOf("\"title");
                
                //Loop to only check top 3 news stories
                while (index>=0 && i<3){
                    //Get the index of each news title 
                    index=news.indexOf("\"title", index+i);
                    int endIndex = news.indexOf("\",", index);
                    String headline = news.substring(index+9, endIndex);
                    i+=1;
                    System.out.println(headline);

                    AudioOutput.playSound(TextToSpeech.convertStringToSpeech(headline));
                    //Make sure each news story doesn't play over another
                    Thread.sleep(85*headline.length());
                }
            
            }
        } catch (InterruptedException | NullPointerException | IOException ex) {
            return false;
        }
        return true;   
    }
}