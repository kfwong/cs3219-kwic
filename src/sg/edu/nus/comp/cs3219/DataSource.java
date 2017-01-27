package sg.edu.nus.comp.cs3219;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kfwong on 1/27/17.
 */
public class DataSource {

    private List<String> data;

    public DataSource(){
        this.data = new ArrayList<>();
    }

    protected void read(){
        Scanner sc = new Scanner(System.in);

        // Ctrl + D
        while(sc.hasNext()){
            data.add(sc.nextLine());
        }
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = new ArrayList<>(data);
    }
}
