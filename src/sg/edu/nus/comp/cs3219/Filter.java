package sg.edu.nus.comp.cs3219;

/**
 * Created by kfwong on 1/27/17.
 */
public interface Filter<T> {
    T execute(T data);
}
