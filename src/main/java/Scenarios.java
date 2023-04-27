import java.util.*;

public class Scenarios {
    int lenMatches = Main.fileMatches.size();
    int lenDeliveries = Main.fileDeliveries.size();

    HashMap<String,Integer> frequencyCount(int n,int start,int end,HashMap<String,Integer> solution) {

        for (int i = start; i < end; i++) {
            String input = Main.fileMatches.get(i)[n];
            if (!input.equals("")) {
                solution.put(input, solution.getOrDefault(input, 0) + 1);
            }
        }

        return solution;
    }


    int[] startAndEndMatchID(List<String[]> data, int n, String year){
        int[] match = new int[2];
        boolean checkError =false;
        for (int i = 1; i < lenMatches - 1; i++) {
            if (data.get(i)[n].equals(year) && !data.get(i - 1)[n].equals(year)) {
                match[0] = i;
                checkError=true;
            } else if (data.get(i)[n].equals(year) && !data.get(i + 1)[n].equals(year)) {
                match[1] = i;
                break;
            }
        }

        try {
            if (!checkError) {

                throw new YearNotFoundException();
            }
        }catch(YearNotFoundException e)
        {
            e.printStackTrace();
            return new int[]{1,0};
        }
        if (match[1] == 0) {
            match[1] = lenMatches - 1;
        }


        return match;

    }

    int[] startAndEndIndexDeliveries(int[] match)
    {
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

    void economyRate(int[] match)
    {
        HashMap<String, Integer> runs = new HashMap<>();
        HashMap<String, Double> balls = new HashMap<>();
        fillMaps(match,runs,balls);
        HashMap<String, Double> solution = new HashMap<>();

       for (Map.Entry<String, Integer> entry : runs.entrySet()) {
            String bowler=entry.getKey();
           solution.put(bowler,runs.get(bowler)/(balls.get(bowler)/6));
        }


        for (Map.Entry<String, Double> entry :  sortByValue(solution).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
    void fillMaps(int[] match,HashMap<String, Integer> runs,HashMap<String, Double> balls) {

        for (int i = match[0]; i <= match[1]; i++) {

            String input = Main.fileDeliveries.get(i)[8];
            int concededRuns = Integer.parseInt(Main.fileDeliveries.get(i)[10]) + Integer.parseInt(Main.fileDeliveries.get(i)[13]) + Integer.parseInt(Main.fileDeliveries.get(i)[15]);
            if(Main.fileDeliveries.get(i)[10].equals("0") && Main.fileDeliveries.get(i)[13].equals("0"))
                balls.put(input,balls.getOrDefault(input,(double)0)+1);
            runs.put(input,runs.getOrDefault(input,0)+concededRuns);
        }
    }

    HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {

        List<Map.Entry<String, Double> > list =
                new LinkedList<>(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

   void topPlayerOfTheMatch(String year)
    {

        HashMap<String,Integer> solution=new HashMap<>();
        int matchId[]=startAndEndMatchID(Main.fileMatches,1,year);

        matchId[0]=matchId[0]==1?1:matchId[0]-1;
        frequencyCount(13,matchId[0],matchId[1],solution);
        System.out.println(maxInMap(solution));
    }

    String maxInMap(HashMap<String,Integer> hashMap)
    {
        int max=Integer.MIN_VALUE;
        String res="";
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            int value=entry.getValue();
            String key=entry.getKey();
            if(max<value) {
                max =value;
                res=key;
            }
        }
        return res;
    }
}



