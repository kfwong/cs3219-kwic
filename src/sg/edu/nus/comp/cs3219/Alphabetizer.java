package sg.edu.nus.comp.cs3219;

import java.util.Collections;
import java.util.List;

/**
 * Created by kfwong on 1/28/17.
 */
public class Alphabetizer implements Filter<List<String>>  {

    @Override
    public List<String> execute(List<String> data) {
        Collections.sort(data);
        return data;
    }
}
