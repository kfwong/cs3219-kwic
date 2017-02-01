package sg.edu.nus.comp.cs3219.kwic.main;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class Kwic {

    public static void main(String[] args) {

        printHeader(args);

        new Kwic(args);
    }

    public Kwic(String... stopWords){
        Pipe<String> p1 = new Pipe<>();
        Pipe<String> p2 = new Pipe<>();
        Pipe<SortedSet<String>> p3 = new Pipe<>();

        DataSource dataSource = new DataSource(p1);
        Filter circularShift = new CircularShift(p1, p2, stopWords);
        Filter alphabetizer = new Alphabetizer(p2, p3);
        DataSink dataSink = new DataSink(p3);

        Pipeline pipeline = new Pipeline();
        pipeline.generateFrom(dataSource)
                .transformBy(circularShift)
                .transformBy(alphabetizer)
                .outputInto(dataSink)
                .run();
    }

    private static void printHeader(String... stopWords){
        System.out.println(
                        "██╗  ██╗   ██╗    ██╗   ██╗    ██████╗\n" +
                        "██║ ██╔╝   ██║    ██║   ██║   ██╔════╝\n" +
                        "█████╔╝    ██║ █╗ ██║   ██║   ██║     \n" +
                        "██╔═██╗    ██║███╗██║   ██║   ██║     \n" +
                        "██║  ██╗██╗╚███╔███╔╝██╗██║██╗╚██████╗\n" +
                        "╚═╝  ╚═╝╚═╝ ╚══╝╚══╝ ╚═╝╚═╝╚═╝ ╚═════╝"
        );

        System.out.println("\nKeyword in Context v0.1\nPipe & Filter Version\nWritten by @kfwong (https://github.com/kfwong)\n");

        System.out.println("#########################################");

        System.out.println("Input titles in each line.");

        System.out.println("Press [ENTER] to input more lines.");

        System.out.println("Press [CTRL+D] when you're done.");

        System.out.println("Ignoring words: ["+ Arrays.stream(stopWords).collect(Collectors.joining(", "))+"]");

        System.out.println("#########################################");
    }
}
