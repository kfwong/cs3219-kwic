package sg.edu.nus.comp.cs3219;

import java.util.List;

/**
 * Created by kfwong on 1/27/17.
 */
public class DataSink {
    public void output(List<String> data){
        data.stream().forEachOrdered(System.out::println);
    }
}
