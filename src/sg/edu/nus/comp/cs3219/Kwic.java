package sg.edu.nus.comp.cs3219;

import java.util.SortedSet;

public class Kwic {

    public static void main(String[] args) {

        printHeader();

        new Kwic();
    }

    public Kwic(){
        Pipe<String> p1 = new Pipe<>();
        Pipe<String> p2 = new Pipe<>();
        Pipe<SortedSet<String>> p3 = new Pipe<>();

        DataSource dataSource = new DataSource(p1);
        Filter circularShift = new CircularShift(p1, p2);
        Filter alphabetizer = new Alphabetizer(p2, p3);
        DataSink dataSink = new DataSink(p3);

        Pipeline pipeline = new Pipeline();
        pipeline.generateFrom(dataSource)
                .transformBy(circularShift)
                .transformBy(alphabetizer)
                .outputInto(dataSink)
                .run();
    }

    private static void printHeader(){
        System.out.println(
                        "██╗  ██╗   ██╗    ██╗   ██╗    ██████╗\n" +
                        "██║ ██╔╝   ██║    ██║   ██║   ██╔════╝\n" +
                        "█████╔╝    ██║ █╗ ██║   ██║   ██║     \n" +
                        "██╔═██╗    ██║███╗██║   ██║   ██║     \n" +
                        "██║  ██╗██╗╚███╔███╔╝██╗██║██╗╚██████╗\n" +
                        "╚═╝  ╚═╝╚═╝ ╚══╝╚══╝ ╚═╝╚═╝╚═╝ ╚═════╝"
        );

        System.out.println("\nKeyword in Context v0.1\nPipe & Filter Version\nWritten by @kfwong (https://github.com/kfwong)\n");

        System.out.println("#########################################\r\nInput titles in each line.\t\t\t\r\nPress [ENTER] to input more lines.\nPress [CTRL+D] when you're done.\r\n#########################################");
    }
}
