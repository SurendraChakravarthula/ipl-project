import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenarios {
    int lenMatches = Main.fileMatches.size();
    int lenDeliveries = Main.fileDeliveries.size();

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


    int[] startAndEndIndex(List<String[]> data, int n, String year) {
        int[] match = new int[2];
        for (int i = 1; i < lenMatches - 1; i++) {
            if (data.get(i)[n].equals(year) && !data.get(i - 1)[n].equals(year)) {
                match[0] = i;
            } else if (data.get(i)[n].equals(year) && !data.get(i + 1)[n].equals(year)) {
                match[1] = i;
                break;
            }
        }

        if (match[1] == 0) {
            match[1] = lenMatches - 1;
        }


        int start = binarySearch(Main.fileDeliveries, match[0], 0);
        String str = Integer.toString(match[0]);

        while (Main.fileDeliveries.get(start)[0].equals(str))
            start--;

        match[0] = ++start;

        int end = binarySearch(Main.fileDeliveries, match[1], 0);
        String en = Integer.toString(match[1]);

        if (Main.fileDeliveries.get(lenDeliveries - 1)[0].equals(en)) {
            match[1] = lenDeliveries - 1;
            return match;
        }
        while (Main.fileDeliveries.get(end)[0].equals(en))
            end++;

        match[1] = --end;
        return match;

    }

    int binarySearch(List<String[]> data, int target, int n) {

        int left = 1, right = lenDeliveries - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int id = Integer.parseInt(data.get(mid)[n]);
            if (target == id) {
                return mid;
            } else if (target < id) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    void extraRunsPerTeamInYear(int[] match, int n) {
        HashMap<String, Integer> solution = new HashMap<>();
        for (int i = match[0]; i < match[1]; i++) {

            String input = Main.fileDeliveries.get(i)[n];
            solution.put(input, solution.getOrDefault(input, 0) + Integer.parseInt(Main.fileDeliveries.get(i)[16]));
        }

        for (Map.Entry<String, Integer> entry : solution.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println();
    }
}
