package com.surendra.java;

import java.util.*;

public class Scenarios {
   private final int lengthOfMatches = Main.matches.size();
   private final int lengthOfDeliveries = Main.deliveries.size();
   private final int indexOfPlayerOfTheMatch=13;
   private final int FIRST=0;
   private final int LAST=1;

    List<String> headingsIndexOfMatches= Arrays.asList(Main.matches.get(FIRST));
    List<String> headingsIndexOfDeliveries=Arrays.asList(Main.deliveries.get(FIRST));

    HashMap<String,Integer> getMatchesPlayedOrWonForAllYears(int n, int start, int end, HashMap<String,Integer> matchesWonOrPlayedPerTeam) {

        for (int i = start; i < end; i++) {
            String input = Main.matches.get(i)[n];
            if (!input.equals("")) {
                matchesWonOrPlayedPerTeam.put(input, matchesWonOrPlayedPerTeam.getOrDefault(input, 0) + 1);
            }
        }

        return matchesWonOrPlayedPerTeam;
    }


    int[] startAndEndMatchID(List<String[]> matches, int n, String year){
        int[] matchIdForGivenYear = new int[2];
        boolean checkForYear =false;
        for (int i = 1; i < lengthOfMatches - 1; i++) {
            if (matches.get(i)[n].equals(year) && !matches.get(i - 1)[n].equals(year)) {
                matchIdForGivenYear[FIRST] = i;
                checkForYear=true;
            } else if (matches.get(i)[n].equals(year) && !matches.get(i + 1)[n].equals(year)) {
                matchIdForGivenYear[LAST] = i;
                break;
            }
        }

        try {
            if (!checkForYear) {
                throw new YearNotFoundException();
            }
        }catch(YearNotFoundException e)
        {
            e.printStackTrace();
           System.exit(0);
        }
        if (matchIdForGivenYear[LAST] == 0) {
            matchIdForGivenYear[LAST] = lengthOfMatches - 1;
        }


        return matchIdForGivenYear;

    }

    int[] getStartAndEndIndicesOfDeliveriesForMatchIds(int[] matchIdForGivenYear)
    {
        int start = searchIndexForMatchId(Main.deliveries, matchIdForGivenYear[FIRST], 0);
        String str = Integer.toString(matchIdForGivenYear[FIRST]);

        while (Main.deliveries.get(start)[headingsIndexOfDeliveries.indexOf("match_id")].equals(str))
            start--;

        matchIdForGivenYear[FIRST] = ++start;

        int end = searchIndexForMatchId(Main.deliveries, matchIdForGivenYear[LAST], 0);
        String en = Integer.toString(matchIdForGivenYear[LAST]);

        if (Main.deliveries.get(lengthOfDeliveries - 1)[headingsIndexOfDeliveries.indexOf("match_id")].equals(en)) {
            matchIdForGivenYear[LAST] = lengthOfDeliveries - 1;
            return matchIdForGivenYear;
        }
        while (Main.deliveries.get(end)[headingsIndexOfDeliveries.indexOf("match_id")].equals(en))
            end++;

        matchIdForGivenYear[LAST] = --end;

        return matchIdForGivenYear;
    }

    int searchIndexForMatchId(List<String[]> data, int target, int n) {

        int left = 1, right = lengthOfDeliveries - 1;
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

    void printExtraRunsPerTeamInYear(int[] matchIdForGivenYear, int n) {
        HashMap<String, Integer> solution = new HashMap<>();
        for (int i = matchIdForGivenYear[FIRST]; i < matchIdForGivenYear[LAST]; i++) {

            String input = Main.deliveries.get(i)[n];
            solution.put(input, solution.getOrDefault(input, 0) + Integer.parseInt(Main.deliveries.get(i)[headingsIndexOfDeliveries.indexOf("extra_runs")]));
        }

        for (Map.Entry<String, Integer> entry : solution.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println();
    }

    void printEconomyRateForBowlers(int[] matchIdForGivenYear)
    {
        HashMap<String, Integer> runs = new HashMap<>();
        HashMap<String, Double> balls = new HashMap<>();
        getBallsThrownAndRunsGivenByBowler(matchIdForGivenYear,runs,balls);
        HashMap<String, Double> solution = new HashMap<>();

       for (Map.Entry<String, Integer> entry : runs.entrySet()) {
            String bowler=entry.getKey();
           solution.put(bowler,runs.get(bowler)/(balls.get(bowler)/6));
        }


        for (Map.Entry<String, Double> entry :  sortBowlersByEconomyRate(solution).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
    void getBallsThrownAndRunsGivenByBowler(int[] matchIdForGivenYear, HashMap<String, Integer> runsGivenByEveryBowler, HashMap<String, Double> ballsThrownByEveryBowler) {

        for (int i = matchIdForGivenYear[FIRST]; i <= matchIdForGivenYear[LAST]; i++) {

            String input = Main.deliveries.get(i)[headingsIndexOfDeliveries.indexOf("bowler")];
            int concededRuns = Integer.parseInt(Main.deliveries.get(i)[headingsIndexOfDeliveries.indexOf("wide_runs")]) + Integer.parseInt(Main.deliveries.get(i)[headingsIndexOfDeliveries.indexOf("noball_runs")])
                    + Integer.parseInt(Main.deliveries.get(i)[headingsIndexOfDeliveries.indexOf("batsman_runs")]);

            if(Main.deliveries.get(i)[10].equals("0") && Main.deliveries.get(i)[13].equals("0"))
                ballsThrownByEveryBowler.put(input,ballsThrownByEveryBowler.getOrDefault(input,(double)0)+1);
            runsGivenByEveryBowler.put(input,runsGivenByEveryBowler.getOrDefault(input,0)+concededRuns);
        }
    }

    HashMap<String, Double> sortBowlersByEconomyRate(HashMap<String, Double> economyRate)
    {

        List<Map.Entry<String, Double> > list =
                new LinkedList<>(economyRate.entrySet());

        Collections.sort(list, new Comparator<>() {
            public int compare(Map.Entry<String, Double> economyRateOne,
                               Map.Entry<String, Double> economyRateTwo) {
                return (economyRateOne.getValue()).compareTo(economyRateTwo.getValue());
            }
        });

        HashMap<String, Double> sortedEconomyRateForEveryBowler = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            sortedEconomyRateForEveryBowler.put(aa.getKey(), aa.getValue());
        }
        return sortedEconomyRateForEveryBowler;
    }

   void getTopPlayerOfTheMatchInGivenYear(String year)
    {
        HashMap<String,Integer> solution=new HashMap<>();
        int matchId[]=startAndEndMatchID(Main.matches,headingsIndexOfMatches.indexOf("season"),year);

        matchId[FIRST]=matchId[FIRST]==1?1:matchId[FIRST]-1;
        getMatchesPlayedOrWonForAllYears(indexOfPlayerOfTheMatch,matchId[FIRST],matchId[LAST],solution);
        System.out.println(getPlayerWithMostTitlesInData(solution));
    }

    String getPlayerWithMostTitlesInData(HashMap<String,Integer> hashMap)
    {
        int maximumTitles=Integer.MIN_VALUE;
        String player="";
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            int value=entry.getValue();
            String key=entry.getKey();
            if(maximumTitles<value) {
                maximumTitles =value;
                player=key;
            }
        }
        return player;
    }
}



