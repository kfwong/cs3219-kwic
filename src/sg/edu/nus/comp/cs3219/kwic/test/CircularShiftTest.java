package sg.edu.nus.comp.cs3219.kwic.test;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs3219.kwic.main.CircularShift;

import static org.junit.Assert.*;

/**
 * Created by kfwong on 1/30/17.
 */
public class CircularShiftTest {

    private PipeProxy<String> inPipe;
    private PipeProxy<String> outPipe;

    private CircularShift circularShift;

    @Before
    public void setUp() throws Exception {
        this.inPipe = new PipeProxy<>();
        this.outPipe = new PipeProxy<>();

        this.circularShift = new CircularShift(inPipe, outPipe);
    }

    @Test
    public void oneTimeCircularShiftShouldProduceNextIndexes() throws Exception {

        String original = "The Day after Tomorrow";
        String expected = "Day after Tomorrow The";

        inPipe.push(original);

        circularShift.filter();

        String actual = outPipe.pull();

        assertEquals("oneTimeCircularShiftShouldProduceNextIndexes", expected, actual);
    }

    @Test
    public void loopedCircularShiftShouldProduceMultipleIndexes() throws Exception {

        String original = "Harry Potter and the Goblet of Fire";
        String[] expected = {
                "Harry Potter and the Goblet of Fire",
                "Potter and the Goblet of Fire Harry",
                "Goblet of Fire Harry Potter and the",
                "Fire Harry Potter and the Goblet of"
        };

        inPipe.push(original);

        for (int i = 0; i < expected.length; i++) {
            circularShift.filter();

            String actual = outPipe.pull();

            assertEquals("loopedCircularShiftShouldProduceMultipleIndexes", expected[i], actual);
        }

    }

}