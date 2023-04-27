import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
public class Main {
    static List<String[]> fileMatches,fileDeliveries;
    public static void main(String[] args) {

        FileParse fileParse=new FileParse();

        try {
            fileMatches =fileParse.csvParse(new FileReader("/home/surendra/project_files/matches.csv"));
            fileDeliveries =fileParse.csvParse( new FileReader("/home/surendra/project_files/deliveries.csv"));
        } catch(FileNotFoundException f) {
            f.printStackTrace();
        }
    }
}