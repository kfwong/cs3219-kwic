package sg.edu.nus.comp.cs3219;

import java.util.Scanner;

/**
 * Created by kfwong on 1/27/17.
 */
public class DataSource implements Runnable {

    private Scanner sc;

    protected Pipe<String> outPipe;

    public DataSource(Pipe<String> out) {
        this.outPipe = out;
        this.sc = new Scanner(System.in);
    }

    protected synchronized void read() {
        outPipe.push(sc.nextLine());

        // TODO: bad static dependency, for testing purpose only should be removed
        Pipeline.startTime = System.nanoTime();
    }

    @Override
    public void run() {
        while(sc.hasNext()){
            read();
        }

        System.out.println("Closing application...Bye!");

        System.exit(0);
    }
}
