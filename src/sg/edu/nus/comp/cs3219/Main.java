package sg.edu.nus.comp.cs3219;

public class Main {

    public static void main(String[] args) {

        System.out.println(
                "██╗  ██╗   ██╗    ██╗   ██╗    ██████╗\n" +
                "██║ ██╔╝   ██║    ██║   ██║   ██╔════╝\n" +
                "█████╔╝    ██║ █╗ ██║   ██║   ██║     \n" +
                "██╔═██╗    ██║███╗██║   ██║   ██║     \n" +
                "██║  ██╗██╗╚███╔███╔╝██╗██║██╗╚██████╗\n" +
                "╚═╝  ╚═╝╚═╝ ╚══╝╚══╝ ╚═╝╚═╝╚═╝ ╚═════╝"
        );

        System.out.println("\nKeyword in Context v0.1\nPipe & Filter Version\nWritten by @kfwong (https://github.com/kfwong)\n");

        System.out.println("#########################################\r\n#\tEnter the titles in each line.\t\t#\r\n#\tPress CTRL+D when you're done.\t\t#\r\n#########################################");

        Pipe pipe = new Pipe();

        pipe.registerDataSource(new DataSource())
                .registerFilter(new CircularShift())
                .registerFilter(new Alphabetizer())
                .registerDataSink(new DataSink())
                .run();
    }
}
