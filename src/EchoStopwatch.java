/**
 * Methods written below allow the user to start and stop a stopwatch upon request
 */
public class EchoStopwatch {
    static long pastTime = -1;

    /**
     * Method starts a stopwatch
     */
    static void startStopwatch() {
        pastTime = System.currentTimeMillis();
    }

    /**
     * Method stops the stopwatch
     *
     * @return true if successfully stopped
     */
    static boolean stopStopwatch() {
        if (pastTime == -1) {
            System.out.println("STOPWATCH :: There was no timer started, exiting!");
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - pastTime;

        // Calculating the elapsed amount of time since the stopwatch was started.
        long days = timeDifference / 86400000;
        long hours = timeDifference / 3600000;
        long minutes = timeDifference / 60000;
        long seconds = timeDifference / 1000;

        // Building a string to output.
        String response = "";
        if (days > 0) {
            response += days + " days, ";
        }
        if (hours > 0) {
            response += days + " hours, ";
        }
        if (minutes > 0) {
            response += minutes + " minutes, ";
        }
        if (seconds > 0) {
            response += seconds + " seconds, ";
        }
        System.out.println("STOPWATCH :: The resulting time was: " + response);

        // Play the sound and revert to the initial state
        AudioOutput.playSound(TextToSpeech.convertStringToSpeech(response));
        pastTime = -1;
        return true;
    }
}
