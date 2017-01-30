package sg.edu.nus.comp.cs3219.kwic.main;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by kfwong on 1/28/17.
 */
public class Alphabetizer extends Filter<String, SortedSet<String>> {

    private SortedSet<String> sortedSet = new TreeSet<>();

    public Alphabetizer(Pipe<String> in, Pipe<SortedSet<String>> out) {
        super(in, out);
    }

    @Override
    public synchronized void filter() {
        if(!inPipe.buffer.isEmpty()){
            String s = inPipe.pull();
            sortedSet.add(s);

            outPipe.push(new TreeSet<>(sortedSet));
        }

    }

}
