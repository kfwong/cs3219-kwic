package sg.edu.nus.comp.cs3219.kwic.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kfwong on 1/27/17.
 */
public class Pipeline {

    public static long startTime = 0;
    public static long endTime = 0;

    private DataSource dataSource;

    private List<Filter> filters;

    private DataSink dataSink;

    private ExecutorService executorService;

    public Pipeline(){
        this.filters = new ArrayList<>();
    }

    public Pipeline generateFrom(DataSource dataSource){
        this.dataSource = dataSource;
        return this;
    }

    public Pipeline transformBy(Filter filter){
        filters.add(filter);
        return this;
    }

    public Pipeline outputInto(DataSink dataSink){
        this.dataSink = dataSink;
        return this;
    }

    public void run(){
        executorService = Executors.newCachedThreadPool();

        executorService.execute(dataSource);
        filters.forEach(executorService::execute);
        executorService.execute(dataSink);
    }


}
