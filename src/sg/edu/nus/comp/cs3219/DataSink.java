package sg.edu.nus.comp.cs3219;

import java.util.SortedSet;

/**
 * Created by kfwong on 1/27/17.
 */
public class DataSink implements Runnable{

    private Pipe<SortedSet<String>> inPipe;

    private SortedSet<String> temp;

    public DataSink(Pipe<SortedSet<String>> in){
        this.inPipe = in;
    }

    public synchronized void write(){

        if(!inPipe.buffer.isEmpty()) {
            if(temp!=null && temp.equals(inPipe.peek())){
                inPipe.pull();
            }else {

                System.out.println("======================");
                System.out.println();
                System.out.println("[Result]");
                System.out.println();
                temp = inPipe.pull();
                temp.forEach(System.out::println);

                // TODO: bad static dependency, for testing purpose only should be removed
                Pipeline.endTime = System.nanoTime();
                System.out.println();
                System.out.println("[Statistics]");
                System.out.println();
                System.out.println("StartTime: " + Pipeline.startTime);
                System.out.println("EndTime: " + Pipeline.endTime);
                System.out.println("Duration: " + ((Pipeline.endTime - Pipeline.startTime)/1000000) + " (ms)" );
                System.out.println();
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            write();
        }
    }
}
