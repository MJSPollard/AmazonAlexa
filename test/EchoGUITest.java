import java.awt.AWTException;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EchoGUITest {

    private static EchoGUI testgui;
    private SoundDetector sounddetector;

    public EchoGUITest() {
        sounddetector = new SoundDetector();
        testgui = new EchoGUI(sounddetector);
        

    }

    /**
     * Clicks a button by initialising and dispatching the MOUSE_CLICKED event.
     *
     * @param button the button on the gui which will be clicked
     */
    public void click(JButton button) {
        int x;
        int y;
        x = button.getX();
        y = button.getY();
        MouseEvent me = new MouseEvent(button, MouseEvent.MOUSE_CLICKED, 0, 0, x, y, 1, false);
        button.dispatchEvent(me);
    }

    /**
     * Presses a button by initialising and dispatching the MOUSE_PRESSED event.
     *
     * @param button the button on the gui which will be pressed
     */
    public void press(JButton button) {
        int x;
        int y;
        x = button.getX();
        y = button.getY();
        MouseEvent me = new MouseEvent(button, MouseEvent.MOUSE_PRESSED, 0, 0, x, y, 1, false);
        button.dispatchEvent(me);
    }

    /**
     * Released a pressed button by initialising and dispatching the
     * MOUSE_RELEASE event.
     *
     * @param button the button on the gui which will be released
     */
    public void release(JButton button) {
        int x;
        int y;
        x = button.getX();
        y = button.getY();
        MouseEvent me = new MouseEvent(button, MouseEvent.MOUSE_RELEASED, 0, 0, x, y, 1, false);
        button.dispatchEvent(me);

    }

    /**
     * test for the PowerButton checks when the GUI is not on and the button is
     * clicked it turns on. Checks when the GUI is turned on the bar changes to
     * cyan and the threads have started.
     *
     * @throws InterruptedException
     */
    @Test
    public void testPowerButton() throws InterruptedException {

        String c;
        assertFalse(testgui.isPowered());
        click(testgui.btnPOW);
        assertTrue(testgui.isPowered());
        assertTrue(sounddetector.running);
        Thread.sleep(2000);
        boolean alive = testgui.detectorThread.isAlive();
        assertTrue(alive);
        c = testgui.getColour();
        assertEquals(c, "Cyan");

        click(testgui.btnPOW);

        assertFalse(testgui.isPowered());
        assertFalse(sounddetector.running);

    }

    /**
     * Test for the MuteButton# checks when the gui is on and the button is
     * clicked and when the button is clicked the microphone is disabled
     *
     * @throws InterruptedException
     */
    @Test
    public void testMuteButton() throws InterruptedException {

        testgui.isPowered = true;
        testgui.isPressed = false;
        boolean pressed = testgui.btnMUTE.isEnabled();

        press(testgui.btnMUTE);
        assertTrue(pressed);
        assertEquals(testgui.flashCount, 0);
        assertFalse(sounddetector.running);

        testgui.isPressed = true;
        press(testgui.btnMUTE);
        pressed = testgui.btnMUTE.isEnabled();
        assertEquals(testgui.flashCount, 0);

    }

    /**
     * Test of the ListenButton Checks that the timer and audio stop when
     * pressed, and the gui flashes.
     *
     */
    @Test
    public void testListenButton() {
        testgui.isPowered = true;
        String c;

        press(testgui.btnLIST);
        boolean pressed = testgui.btnLIST.isEnabled();
        assertTrue(pressed);
        c = testgui.getColour();
        //assertEquals(c, "Flash");

        release(testgui.btnLIST);
        c = testgui.getColour();
        assertEquals(c, "Off");

        press(testgui.btnLIST);

    }

    @After
    public void tearDown() {
        testgui = null;
        sounddetector = null;
    }

    /**
     * Test of addButtons() checks that buttons have been added and put in the
     * right location.
     */
    @Test
    public void testAddButtons() {
        System.out.println("addButtons");

        int x;
        int y;
        JButton m;
        JButton p;
        JButton l;
        m = testgui.btnMUTE;
        p = testgui.btnPOW;
        l = testgui.btnLIST;

        assertNotNull(m);
        assertNotNull(p);
        assertNotNull(l);

        x = m.getX();
        y = m.getY();
        assertEquals(x, 301);
        assertEquals(y, 28);

        x = p.getX();
        y = p.getY();
        assertEquals(x, 350);
        assertEquals(y, 244);

        x = l.getX();
        y = l.getY();
        assertEquals(x, 401);
        assertEquals(y, 28);

    }

    /**
     * Test of changeColor method, of class EchoGUI. makes sure that the colours
     * have changed by checking the current colour string
     */
    @Test
    public void testChangeColor() {
        System.out.println("changeColor");
        String off = "Off";
        String blue = "Blue";
        String cyan = "Cyan";
        String flash = "Flash";
        String c = null;

        //--------------------------------
        testgui.changeColor(off);
        c = testgui.getColour();
        assertEquals(c, "Off");

        //----------------------------------
        testgui.changeColor(blue);
        c = testgui.getColour();
        assertEquals(c, "Blue");

        //--------------------------------
        testgui.changeColor(cyan);
        c = testgui.getColour();
        assertEquals(c, "Cyan");

        //----------------------------------
        testgui.changeColor(flash);
        c = testgui.getColour();
        assertEquals(c, "Flash");

    }

    /**
     * Test of Flash method, of class EchoGUI. makes sure the flashcount
     * increments which flash is called.
     */
    @Test
    public void testFlash() {
        testgui.flashCount = 2;
        testgui.Flash();
        assertEquals(testgui.flashCount, 3);
        testgui.flashCount = 3;
        testgui.Flash();
        assertEquals(testgui.flashCount, 4);
    }

    /**
     * Test of the isPowered() method, if the gui is off the it isPowered() is
     * false. If the Gui is on isPowered() is true.
     */
    @Test
    public void testIsPowered() {
        assertFalse(testgui.isPowered());
        click(testgui.btnPOW);
        assertTrue(testgui.isPowered());
    }
}