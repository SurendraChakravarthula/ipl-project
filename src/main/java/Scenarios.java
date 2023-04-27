import java.util.HashMap;
import java.util.Map;

public class Scenarios {
    int lenMatches = Main.fileMatches.size();

    void frequencyCount(int n) {
     HashMap<String,Integer> solution =new HashMap<>();
        for (int i = 1; i < lenMatches; i++) {
            String input = Main.fileMatches.get(i)[n];
            if (!input.equals("")) {
                solution.put(input, solution.getOrDefault(input, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : solution.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();
    }
}
