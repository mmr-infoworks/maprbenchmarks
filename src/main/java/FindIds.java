import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import org.ojai.Document;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindIds {
    String filePath;
    List<String> ids;
    Table table;
    int records;

    public FindIds(String filePath,String tablePath,String recNum) {
        this.filePath = filePath;
        table = MapRDB.getTable(tablePath);
        ids = new ArrayList<>();
        records = Integer.parseInt(recNum);
    }

    public void writeToFile() throws IOException {
        Iterator<Document> iterator = table.find().iterator();
        int s = records;
        while (s > 0) {
            ids.add(iterator.next().getId().getString());
            s--;
        }
        Path out = Paths.get(filePath);
        Files.write(out,ids, Charset.defaultCharset());



    }

    public static void main(String[] args) throws IOException {
        FindIds findIds = new FindIds(args[0],args[1],args[2]);
        findIds.writeToFile();
        System.out.println("done");
    }
}
