package sg.edu.nus.comp.cs3219;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kfwong on 1/27/17.
 */
public class Pipe {
    private List<Filter<List<String>>> filters;
    private DataSource dataSource;
    private DataSink dataSink;

    public Pipe(){
        this.filters = new ArrayList<>();
    }

    public Pipe registerDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        return this;
    }

    public Pipe registerDataSink(DataSink dataSink) {
        this.dataSink = dataSink;

        return this;
    }

    public Pipe registerFilter(Filter filter) {
        this.filters.add(filter);

        return this;
    }

    public void run() {
        dataSource.read();

        filters.stream().forEachOrdered(f -> {
            dataSource.setData(f.execute(dataSource.getData()));
        });

        dataSink.output(dataSource.getData());
    }

}
