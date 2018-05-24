import com.mapr.db.MapRDB;
import com.mapr.db.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DeleteMain {
    String filePath;
    List<String> ids;
    Table table;

    public DeleteMain(String filePath,String tablePath) {
        this.filePath = filePath;
        table = MapRDB.getTable(tablePath);
        ids = new ArrayList<>();
    }

    private void readFileIntoList() {
        Scanner s = null;
        try {
            s = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (s.hasNext()){
            ids.add(s.next().trim());
        }
        s.close();
    }

    private void doDelete() {
        Date start =new Date();
        System.out.println("starting actual delete at "+start.toString());
        ids.forEach(i -> {
                    table.delete(i);
                }
        );
        table.flush();
        Date end =new Date();
        System.out.println("ending actual delete at "+end.toString());


    }

    public void delete() {
        readFileIntoList();
        doDelete();
        System.out.println("deleted "+ filePath);
    }

    public static void main(String[] args) {
        DeleteMain main = new DeleteMain(args[0],args[1]);
        main.delete();
    }
}
