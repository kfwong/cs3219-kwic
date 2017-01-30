package sg.edu.nus.comp.cs3219.kwic.main;

/**
 * Created by kfwong on 1/27/17.
 */
abstract public class Filter<S, T> implements Runnable {

    protected Pipe<S> inPipe;
    protected Pipe<T> outPipe;

    public Filter(Pipe<S> inPipe, Pipe<T> outPipe) {
        this.inPipe = inPipe;
        this.outPipe = outPipe;
    }

    abstract void filter();

    @Override
    public void run() {
        while(true) {
            filter();
        }

    }
}
