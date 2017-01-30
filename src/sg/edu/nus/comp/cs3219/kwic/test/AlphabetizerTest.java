package sg.edu.nus.comp.cs3219.kwic.test;

import org.junit.Before;
import org.junit.Test;
import sg.edu.nus.comp.cs3219.kwic.main.Alphabetizer;

import java.util.SortedSet;

import static org.junit.Assert.*;

/**
 * Created by kfwong on 1/30/17.
 */
public class AlphabetizerTest {

    private PipeProxy<String> inPipe;
    private PipeProxy<SortedSet<String>> outPipe;

    private Alphabetizer alphabetizer;

    @Before
    public void setUp() throws Exception {
        this.inPipe = new PipeProxy<>();
        this.outPipe = new PipeProxy<>();

        this.alphabetizer = new Alphabetizer(inPipe, outPipe);
    }

    @Test
    public void listOfStringShouldSortLexicographically() {
        String[] original = {
                "Lorem ipsum dolor sit amet",
                "consectetur adipiscing elit",
                "Duis egestas sed justo ac dictum",
                "Suspendisse consequat diam volutpat vestibulum ornare",
                "Aliquam rutrum purus vitae scelerisque suscipit",
                "Nulla ultrices",
                "nunc auctor porta porttitor",
                "ante erat sodales leo",
                "sed bibendum lorem odio finibus mi",
                "Maecenas imperdiet tellus vel posuere maximus",
                "Integer vehicula",
                "lectus pellentesque venenatis semper",
                "lectus sem ultricies turpis",
                "sit amet euismod ligula quam eu est",
                "Sed ornare lacinia leo, a feugiat nibh gravida in",
                "Class aptent taciti sociosqu ad litora torquent per conubia nostra",
                "per inceptos himenaeos"
        };

        String[] expected = {
                "Aliquam rutrum purus vitae scelerisque suscipit",
                "Class aptent taciti sociosqu ad litora torquent per conubia nostra",
                "Duis egestas sed justo ac dictum",
                "Integer vehicula",
                "Lorem ipsum dolor sit amet",
                "Maecenas imperdiet tellus vel posuere maximus",
                "Nulla ultrices",
                "Sed ornare lacinia leo, a feugiat nibh gravida in",
                "Suspendisse consequat diam volutpat vestibulum ornare",
                "ante erat sodales leo",
                "consectetur adipiscing elit",
                "lectus pellentesque venenatis semper",
                "lectus sem ultricies turpis",
                "nunc auctor porta porttitor",
                "per inceptos himenaeos",
                "sed bibendum lorem odio finibus mi",
                "sit amet euismod ligula quam eu est"
        };

        for (int i = 0; i<original.length; i++){
            inPipe.push(original[i]);

            alphabetizer.filter();

            if(i == original.length-1) {
                String[] actual = outPipe.pull().toArray(new String[]{});

                assertArrayEquals("listOfStringShouldSortLexicographically", expected, actual);
            }else{
                outPipe.pull();
            }
        }
    }
}