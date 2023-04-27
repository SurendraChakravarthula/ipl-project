import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

        int lenFileMatches=fileMatches.size();

        Scenarios sc=new Scenarios();

        print(sc.frequencyCount(1,1,lenFileMatches,new HashMap<String,Integer>()));          //scenario 1
        print(sc.frequencyCount(10,1,lenFileMatches,new HashMap<String,Integer>()));         //scenario 2

        sc.extraRunsPerTeamInYear(sc.startAndEndIndexDeliveries(sc.startAndEndMatchID(fileMatches,1,"2016")),3); //scenario 3

        sc.economyRate(sc.startAndEndIndexDeliveries(sc.startAndEndMatchID(fileMatches,1,"2015")));                   //scenario 4

        sc.topPlayerOfTheMatch("2019");

    }

    static void print(HashMap<String,Integer> solution)
    {
        for (Map.Entry<String, Integer> entry : solution.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();
    }
}