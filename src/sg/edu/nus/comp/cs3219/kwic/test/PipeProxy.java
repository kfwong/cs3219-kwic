package sg.edu.nus.comp.cs3219.kwic.test;

import sg.edu.nus.comp.cs3219.kwic.main.Pipe;

/**
 * Created by kfwong on 1/30/17.
 */
class PipeProxy<T> extends Pipe<T> {

    @Override
    public void push(T data) {
        super.push(data);
    }

    @Override
    public T pull() {
        return super.pull();
    }
}
