import java.util.Timer;
import java.util.TimerTask;

/**
 * Class responsible for the timer. Static methods provide the functionality.
 */
public class EchoTimer {
    volatile static boolean shouldPlay = true;
    static boolean isPlaying = false;

    /**
     * Method starts a timer based on the string
     *
     * @param str the string containing seconds, minutes and hours to add to the timer
     * @return true if successful
     */
    static boolean startTimer(String str) {
        if (str == null || !str.contains("minutes") && !str.contains("hours") && !str.contains("seconds")) {
            return false;
        }
        //We start the time off at 0
        long time = 0;
        str = str.replaceAll("[^A-Za-z0-9 ]", "");
        String[] strings = str.split(" ");
        
        //Calculating how long the user wants the timer to run for
        for (int i = 1; i < strings.length; i++) {
            try {
                if (strings[i].equals("seconds") || strings[i].equals("second")) {
                    time += Integer.parseInt(strings[i - 1]) * 1000;
                }
                if (strings[i].equals("minutes") || strings[i].equals("minute")) {
                    time += Integer.parseInt(strings[i - 1]) * 60000;
                }
                if (strings[i].equals("hours") || strings[i].equals("hour")) {
                    time += Integer.parseInt(strings[i - 1]) * 3600000;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        // Checking the time will be a valid time
        if (time > 0) {
            Timer t = new Timer();
            System.out.println("TIMER SET FOR: " + time + " MILLISECONDS");
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    //Setting booleans to call in other functions
                    isPlaying = true;
                    shouldPlay = true;
                    AudioOutput.playTimerLooping(getClass().getResourceAsStream("alarm.wav"));
                    isPlaying = false;
                }
            }, time);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method returns true if the timer should continually loop
     *
     * @return true if it should play
     */
    static boolean shouldPlay() {
        return shouldPlay;
    }

    /**
     * Method stops the timer from playing
     */
    static void stopPlaying() {
        shouldPlay = false;
    }
}
