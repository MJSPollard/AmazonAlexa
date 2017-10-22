
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

/**
 * Class to create a simulation of the Amazon Echo
 */
public class EchoGUI extends JFrame {

    JFrame frame = new JFrame();
    final PowerButton btnPOW = new PowerButton();
    final MuteButton btnMUTE = new MuteButton();
    final ListenButton btnLIST = new ListenButton();
    final SoundDetector detector;
    Thread detectorThread;
    boolean isPowered = false;
    boolean isPressed = false;
    boolean listPressed = false;
    ScheduledExecutorService executorService;
    private String currentColour;
    int flashCount = 0;


    /*
    * Power button - turns the echo on and off
     */
    class PowerButton extends JButton {

        PowerButton() {
            setBorder(null);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    //runs this if echo is turned off and turns it on
                    if (!isPowered) {
                        System.out.println("TURNING ON");
                        isPowered = true;
                        detectorThread = new Thread(detector);
                        detectorThread.start();
                        AudioOutput.playSoundWithoutListeners(getClass().getClassLoader().getResourceAsStream("newStartSound.wav"));
                        changeColor("Cyan");

                    } //runs this if echo is turned on and turns it off
                    else {
//                        try {
                        detector.disableMic();
                        if (executorService != null) {
                            executorService.shutdown();
                        }
                        flashCount = 0;
                        System.out.println("TURNING OFF");
                        isPowered = false;
                        AudioOutput.stopAudio();
                        changeColor("Off");
                        AudioOutput.playSoundWithoutListeners(getClass().getClassLoader().getResourceAsStream("newOffSound.wav"));
                    }
                }
            });
        }
    }


    /*
    * Mute Button - mutes/unmutes the mircophone
     */
    class MuteButton extends JButton {

        MuteButton() {
            setIcon(new ImageIcon("mute.png"));
            setBorder(null);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if (isPowered) {

                        //runs this when the gui is on and unmutes the microphone
                        if (isPressed) {
                            flashCount = 0;
                            System.out.println("Microphone activated");
                            AudioOutput.playSoundWithoutListeners(getClass().getClassLoader().getResourceAsStream("unmuted.wav"));
                            isPressed = false;
                            detector.enableMic();
                            detectorThread = new Thread(detector);
                            detectorThread.start();

                        } //runs this when the gui is on and mutes the microphone
                        else {
                            flashCount = 0;
                            isPressed = true;
                            AudioOutput.playSoundWithoutListeners(getClass().getClassLoader().getResourceAsStream("muted.wav"));
                            detector.disableMic();
                            try {
                                detectorThread.join();
                            } catch (InterruptedException e) {
                                System.out.println("InterruptedException - this should not have happened.");
                                e.printStackTrace();
                                System.exit(1);
                            } finally {
                                System.out.println("Disabled all microphone input.");
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Listen Button - this class handles the functions for turn off the timer
     * and stopwatch and also has a cool flashing effect when pressed
     */
    class ListenButton extends JButton {

        ListenButton() {
            setBorder(null);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {

                    if (isPowered) {
                        if (EchoTimer.isPlaying) {
                            EchoTimer.stopPlaying();
                            AudioOutput.stopAudio();
                        }
                        EchoStopwatch.stopStopwatch();
                        if (listPressed) {
                            System.out.println("Action button pressed");
                            AudioOutput.playSoundWithoutListeners(getClass().getClassLoader().getResourceAsStream("newListSound.wav"));
                            changeColor("Flash");
                            listPressed = false;
                        } else {
                            changeColor("Cyan");
                            listPressed = true;
                        }
                    }
                }
            });
        }
    }

    /**
     * methods that adds the buttons onto the content pane
     */
    void addButtons() {
        btnMUTE.setOpaque(false);
        btnMUTE.setContentAreaFilled(false);
        btnMUTE.setBorderPainted(false);
        btnMUTE.setBounds(301, 28, 30, 15);
        frame.add(btnMUTE);

        btnPOW.setOpaque(false);
        btnPOW.setContentAreaFilled(false);
        btnPOW.setBorderPainted(false);
        btnPOW.setBounds(350, 244, 30, 30);
        frame.add(btnPOW);

        btnLIST.setOpaque(false);
        btnLIST.setContentAreaFilled(false);
        btnLIST.setBorderPainted(false);
        btnLIST.setBounds(401, 28, 30, 15);
        frame.add(btnLIST);
    }

    /**
     * Method to change the color of the Echo's light
     * @param color the color to change it to - (Blue/Cyan/Off/Flash)
     */
    void changeColor(String color) {
        currentColour = color;
        if (executorService != null) {
            executorService.shutdown();
        }
        //uses an executor service to set the flash to every 1 second
        if (color.equals("Flash")) {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Flash();
                }
            }, 0, 1, TimeUnit.SECONDS);
        } else {
            frame.setContentPane(new JLabel(new ImageIcon(getClass().getResource("echo" + color + ".png"))));
            frame.setLayout(null);
            frame.pack();
            addButtons();
        }
    }

    /**
     * Method to have the lights atop the echo to flash
     */
    void Flash() {
        //every other second the gui switches between flash and not flash
        if (flashCount % 2 == 0) {
            frame.setContentPane(new JLabel(new ImageIcon(getClass().getResource("echoCyanFlash.png"))));
            frame.setLayout(null);
            frame.pack();
            addButtons();
            flashCount++;
        } else {
            frame.setContentPane(new JLabel(new ImageIcon(getClass().getResource("echoCyanFlash2.png"))));
            frame.setLayout(null);
            frame.pack();
            addButtons();
            flashCount++;
        }
    }

    /**
     * Constructor to set up the GUI
     * @param detector the SoundDetector to interact with when muting, etc.
     */
    EchoGUI(SoundDetector detector) {
        this.detector = detector;
        frame.setTitle("The Amazon Echo");
        changeColor("Off");
        frame.setLayout(null);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(738, 622);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Returns if the GUI is powered
     * @return true if it's powered
     */
    boolean isPowered() {
        return isPowered;
    }

    /**
     * Method sets if the GUI is powered
     * @param value the value to set for the powered
     */
    void setPowered(boolean value) {
        isPowered = value;
    }

    String getColour() {
        return currentColour;
    }
}
