package com.surendra.java;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.time.StopWatch;

import java.io.FileReader;
import java.util.*;

public class Main {
    private final int ZERO =0;
    private final int ONE =1;
    private final int TWO =2;
    private final int THREE =3;
    private final int FOUR =4;
    private final int FIVE =5;
    private final int SIX =6;
    private final int SEVEN =7;
    private final int EIGHT =8;
    private final int NINE =9;
    private final int TEN =10;
    private final int ELEVEN =11;
    private final int TWELVE =12;
    private final int THIRTEEN =13;
    private final int FOURTEEN =14;
    private final int FIFTEEN =15;
    private final int SIXTEEN =16;
    private final int SEVENTEEN =17;
    private final int EIGHTEEN =18;
    private final int NINETEEN =19;
    private final int TWENTY =20;

    public static void main(String[] args) {
        StopWatch s=new StopWatch();
        s.start();

        Main main=new Main();

        //Parse CSV files and store them in list of objects
        List<Matches> matches=main.parseMatches(new ArrayList<>());
        List<Deliveries> deliveries=main.parseDeliveries(new ArrayList<>());

        final int lengthOfMatches=matches.size();
        final int lengthOfDeliveries=deliveries.size();

        //Scenario-1: Number of matches played per year of all the years in IPL.
        HashMap<Integer,Integer> numberOfMatchesPlayedPerYear=new HashMap<>();
        main.getNumberOfMatchesPlayedPerYear(lengthOfMatches,matches,numberOfMatchesPlayedPerYear);

        //Scenario-2: Number of matches won of all teams over all the years of IPL.
        main.getNumberOfMatchesWonPerTeam(lengthOfMatches,matches);

        int year=2015;
        try {
            if (!numberOfMatchesPlayedPerYear.containsKey(year))
                throw new YearNotFoundException();
        }catch(YearNotFoundException y)
        {
            y.printStackTrace();
            System.exit(0);
        }

       int startAndEndMatchIdsOfGivenYear[]=main.getStartAndEndMatchIdsOfGivenYear(matches,year,lengthOfMatches);
       int startAndEndIndicesOfDeliveriesForMatchIds[]= main.getStartAndEndIndicesOfDeliveriesForMatchIds(deliveries,startAndEndMatchIdsOfGivenYear,lengthOfDeliveries);

       //Scenario-3: For the year 2016 get the extra runs conceded per team.
        main.getConcededRunsPerTeamForGivenIndices(deliveries,startAndEndIndicesOfDeliveriesForMatchIds);

        //Scenario-3: For the year 2015 get the top economical bowlers.
        HashMap<String,Float> economyRatePerBowler =main.getEconomyRatePerBowler(deliveries,startAndEndIndicesOfDeliveriesForMatchIds,new HashMap<>());

        //Scenario-4: For the year 2015 get the top economical bowlers.
        main.sortBowlersByEconomyRate(economyRatePerBowler);

        //Scenario-5: Player who got the title player of the match most in a 2015
        main.getTopPlayerOfTheMatchInGivenYear(matches,year,lengthOfMatches);

        s.stop();
        System.out.println(s.getTime());

    }

    List<Matches> parseMatches(List<Matches> matches)
    {
        try {
            CSVReader csvReader = new CSVReader(new FileReader("/home/surendra/IdeaProjects/ipl_project/src/resources/matches.csv"));
            String[] line;
            line = csvReader.readNext();
            for (;(line = csvReader.readNext()) != null;) {
                Matches match=new Matches(Integer.parseInt(line[ZERO]),Integer.parseInt(line[ONE]),line[TWO],line[THREE],line[FOUR],line[FIVE],line[SIX],line[SEVEN],line[EIGHT],Integer.parseInt(line[NINE]),line[TEN],
                        Integer.parseInt(line[ELEVEN]),Integer.parseInt(line[TWELVE]),line[THIRTEEN],line[FOURTEEN],line[FIFTEEN],line[SIXTEEN],line[SEVENTEEN]);

                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return matches;
    }

    List<Deliveries> parseDeliveries(List<Deliveries> deliveries)
    {
        try {
            CSVReader csvReader = new CSVReader(new FileReader("/home/surendra/IdeaProjects/ipl_project/src/resources/deliveries.csv"));
            String[] line;
            line = csvReader.readNext();
            for (;(line = csvReader.readNext()) != null;) {
                 Deliveries delivery=new Deliveries(Integer.parseInt(line[ZERO]),Integer.parseInt(line[ONE]),line[TWO],line[THREE],Integer.parseInt(line[FOUR]),Integer.parseInt(line[FIVE]),line[SIX],line[SEVEN],line[EIGHT],Integer.parseInt(line[NINE]),Float.parseFloat(line[TEN]),
                         Integer.parseInt(line[ELEVEN]),Integer.parseInt(line[TWELVE]),Float.parseFloat(line[THIRTEEN]),Integer.parseInt(line[FOURTEEN]),Float.parseFloat(line[FIFTEEN]),Integer.parseInt(line[SIXTEEN]),Integer.parseInt(line[SEVENTEEN]),line[EIGHTEEN],line[NINETEEN],line[TWENTY]);

                 deliveries.add(delivery);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


                return deliveries;
    }

  HashMap<Integer, Integer> getNumberOfMatchesPlayedPerYear(int lengthOfMatches, List<Matches> matches, HashMap<Integer, Integer> numberOfMatchesPlayedPerYear)
    {
        for (int i = 0; i < lengthOfMatches; i++) {
            int year = matches.get(i).getSeason();
                numberOfMatchesPlayedPerYear.put(year, numberOfMatchesPlayedPerYear.getOrDefault(year, 0) + ONE);
        }
        printData(numberOfMatchesPlayedPerYear);
       return numberOfMatchesPlayedPerYear;
    }

    void getNumberOfMatchesWonPerTeam(int lengthOfMatches, List<Matches> matches)
    {
        HashMap<String,Integer> numberOfMatchesWonPerTeam=new HashMap<>();

        for (int i = ZERO; i < lengthOfMatches; i++) {
            String winner = matches.get(i).getWinner();
            if(!winner.equals(""))
            numberOfMatchesWonPerTeam.put(winner, numberOfMatchesWonPerTeam.getOrDefault(winner, 0) + 1);
        }
        printData(numberOfMatchesWonPerTeam);
    }

    int[] getStartAndEndMatchIdsOfGivenYear(List<Matches> matches, int year, int lengthOfMatches){
        int[] matchIdsForGivenYear = new int[TWO];

        for (int i = ZERO; i < lengthOfMatches - ONE; i++) {
            if (matches.get(i).getSeason()==year) {
                matchIdsForGivenYear[ZERO] = i+1;
                while(i<lengthOfMatches && matches.get(i).getSeason()==year)
                    i++;
                matchIdsForGivenYear[ONE]=i;
                break;
            }
        }
        return matchIdsForGivenYear;
    }

    int searchIndexForMatchId(List<Deliveries> deliveries, int indexOfMatchId,int lengthOfDeliveries) {

        int left = 1, right = lengthOfDeliveries - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int id = deliveries.get(mid).getMatch_id();
            if (indexOfMatchId == id) {
                return mid;
            } else if (indexOfMatchId < id) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    int[] getStartAndEndIndicesOfDeliveriesForMatchIds(List<Deliveries> deliveries,int[] matchIdForGivenYear,int lengthOfDeliveries)
    {
        int start = searchIndexForMatchId(deliveries, matchIdForGivenYear[ZERO], lengthOfDeliveries);

        while (start>-1 && deliveries.get(start).getMatch_id()==matchIdForGivenYear[ZERO])
            start--;
        matchIdForGivenYear[ZERO] = ++start;

        int end = searchIndexForMatchId(deliveries, matchIdForGivenYear[ONE], lengthOfDeliveries);

        while (end<lengthOfDeliveries && deliveries.get(end).getMatch_id()==matchIdForGivenYear[ONE])
            end++;
        matchIdForGivenYear[ONE] = end;

        return matchIdForGivenYear;
    }

    void getConcededRunsPerTeamForGivenIndices(List<Deliveries> deliveries, int[] startAndEndIndicesOfDeliveriesForMatchIds) {
        HashMap<String, Integer> concededRunsPerTeamForGivenIndices = new HashMap<>();
        for (int i = startAndEndIndicesOfDeliveriesForMatchIds[ZERO]; i < startAndEndIndicesOfDeliveriesForMatchIds[ONE]; i++) {

            String input = deliveries.get(i).getBowling_team();
            concededRunsPerTeamForGivenIndices.put(input, concededRunsPerTeamForGivenIndices.getOrDefault(input, ZERO) + deliveries.get(i).getExtra_runs());
        }

       printData(concededRunsPerTeamForGivenIndices);
        System.out.println();
    }

    HashMap<String,Float> getEconomyRatePerBowler(List<Deliveries> deliveries, int[] matchIdForGivenYear, HashMap<String,Float> economyRatePerBowler) {
        HashMap<String, Pair> runsGivenAndBallsThrownByEveryBowler=new HashMap<>();
        for (int i = matchIdForGivenYear[ZERO]; i < matchIdForGivenYear[ONE]; i++) {

            String bowler = deliveries.get(i).getBowler();
            float wide_Runs=deliveries.get(i).getWide_runs();
            float noball_runs=deliveries.get(i).getNoball_runs();
            float ballsThrown=ZERO;
            float runsGiven=wide_Runs+noball_runs+deliveries.get(i).getBatsman_runs();

            if(wide_Runs==ZERO && noball_runs==ZERO)
                ballsThrown=1;

            Pair pair=new Pair();

            if(runsGivenAndBallsThrownByEveryBowler.containsKey(bowler))
            {
                  pair.setBallsThrown(runsGivenAndBallsThrownByEveryBowler.get(bowler).getBallsThrown()+ballsThrown);
                  pair.setRunsGiven(runsGivenAndBallsThrownByEveryBowler.get(bowler).getRunsGiven()+runsGiven);
                  runsGivenAndBallsThrownByEveryBowler.put(bowler,pair);
            }else{
                pair.setRunsGiven(runsGiven);
                pair.setBallsThrown(ballsThrown);
                runsGivenAndBallsThrownByEveryBowler.put(bowler,pair);
            }
        }


        for (Map.Entry<String,Pair> entry : runsGivenAndBallsThrownByEveryBowler.entrySet()) {
            Pair pair=entry.getValue();
          economyRatePerBowler.put(entry.getKey(),pair.getRunsGiven()/(pair.getBallsThrown()/6));
        }
        return economyRatePerBowler;
    }

    void sortBowlersByEconomyRate(HashMap<String, Float> economyRate)
    {

        List<Map.Entry<String, Float> > list =
                new LinkedList<>(economyRate.entrySet());

        Collections.sort(list, new Comparator<>() {
            public int compare(Map.Entry<String, Float> economyRateOne,
                               Map.Entry<String, Float> economyRateTwo) {
                return (economyRateOne.getValue()).compareTo(economyRateTwo.getValue());
            }
        });

        HashMap<String,Float> sortedEconomyRateForEveryBowler = new LinkedHashMap<>();
        for (Map.Entry<String, Float> aa : list) {
            sortedEconomyRateForEveryBowler.put(aa.getKey(), aa.getValue());
        }

        printData(sortedEconomyRateForEveryBowler);

    }

    void getTopPlayerOfTheMatchInGivenYear(List<Matches> matches, int year,int lengthOfMatches)
    {
        HashMap<String,Integer> numberOfTitlesPerPlayer=new HashMap<>();
        int startAndEndMatchIdsOfGivenYear[]=getStartAndEndMatchIdsOfGivenYear(matches,year,lengthOfMatches);

        for (int i = startAndEndMatchIdsOfGivenYear[ZERO]-1; i < startAndEndMatchIdsOfGivenYear[ONE]-1; i++) {
            String player=matches.get(i).getPlayer_of_match();
            numberOfTitlesPerPlayer.put(player, numberOfTitlesPerPlayer.getOrDefault(player, 0) + 1);
        }

        System.out.println(getPlayerWithMostTitles(numberOfTitlesPerPlayer));
    }

    String getPlayerWithMostTitles(HashMap<String,Integer> hashMap)
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

    static <K,V> void printData(HashMap<K,V> data)
    {
        for (Map.Entry<K,V> entry : data.entrySet()) {
            System.out.println(entry.getKey() + "         :       " + entry.getValue());
        }
        System.out.println();
    }
}
