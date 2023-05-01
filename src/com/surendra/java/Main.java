package com.surendra.java;

import com.opencsv.CSVReader;

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
        Main main=new Main();

        List<Matches> matches=main.parseMatches(new ArrayList<>());
        List<Deliveries> deliveries=main.parseDeliveries(new ArrayList<>());

        final int lengthOfMatches=matches.size();
        final int lengthOfDeliveries=deliveries.size();

        HashMap<Integer,Integer> numberOfMatchesPlayedPerYear=new HashMap<>();
        main.getNumberOfMatchesPlayedPerYear(lengthOfMatches,matches,numberOfMatchesPlayedPerYear);

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

        main.getConcededRunsPerTeamForGivenIndices(deliveries,startAndEndIndicesOfDeliveriesForMatchIds);
        HashMap<String,Float> economyRatePerBowler =main.getEconomyRatePerBowler(deliveries,startAndEndIndicesOfDeliveriesForMatchIds,new HashMap<>());
        main.sortBowlersByEconomyRate(economyRatePerBowler);
        main.getTopPlayerOfTheMatchInGivenYear(matches,year,lengthOfMatches);
        main.getTopBatsManInGivenYearPerVenue(deliveries,startAndEndIndicesOfDeliveriesForMatchIds,startAndEndMatchIdsOfGivenYear,matches);
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
        System.out.println("Number of matches played per year of all the years in IPL:");
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
        System.out.println("Number of matches won of all teams over all the years of IPL:");
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

        int[] matchIndicesForGivenYearInDeliveries=new int[TWO];
        while (start>-1 && deliveries.get(start).getMatch_id()==matchIdForGivenYear[ZERO])
            start--;
        matchIndicesForGivenYearInDeliveries[ZERO] = ++start;

        int end = searchIndexForMatchId(deliveries, matchIdForGivenYear[ONE], lengthOfDeliveries);

        while (end<lengthOfDeliveries && deliveries.get(end).getMatch_id()==matchIdForGivenYear[ONE])
            end++;
        matchIndicesForGivenYearInDeliveries[ONE] = end;

        return matchIndicesForGivenYearInDeliveries;
    }

    void getConcededRunsPerTeamForGivenIndices(List<Deliveries> deliveries, int[] startAndEndIndicesOfDeliveriesForMatchIds) {
        HashMap<String, Integer> concededRunsPerTeamForGivenIndices = new HashMap<>();
        for (int i = startAndEndIndicesOfDeliveriesForMatchIds[ZERO]; i < startAndEndIndicesOfDeliveriesForMatchIds[ONE]; i++) {

            String input = deliveries.get(i).getBowling_team();
            concededRunsPerTeamForGivenIndices.put(input, concededRunsPerTeamForGivenIndices.getOrDefault(input, ZERO) + deliveries.get(i).getExtra_runs());
        }
        System.out.println("For the given year get the extra runs conceded per team:");
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
        System.out.println("For the given year get the top economical bowlers:");

        int count=0;
        for (Map.Entry<String,Float> entry : sortedEconomyRateForEveryBowler.entrySet()) {
            System.out.println(entry.getKey() + "         :      " + entry.getValue());
            count++;
            if(count==5)
                break;
        }
        System.out.println();
    }

    void getTopPlayerOfTheMatchInGivenYear(List<Matches> matches, int year,int lengthOfMatches)
    {
        HashMap<String,Integer> numberOfTitlesPerPlayer=new HashMap<>();
        int startAndEndMatchIdsOfGivenYear[]=getStartAndEndMatchIdsOfGivenYear(matches,year,lengthOfMatches);

        for (int i = startAndEndMatchIdsOfGivenYear[ZERO]-1; i < startAndEndMatchIdsOfGivenYear[ONE]-1; i++) {
            String player=matches.get(i).getPlayer_of_match();
            numberOfTitlesPerPlayer.put(player, numberOfTitlesPerPlayer.getOrDefault(player, 0) + 1);
        }
        System.out.println("Player with most titles with player of the match in given year:");
        System.out.println(getPlayerWithMostTitles(numberOfTitlesPerPlayer));
        System.out.println();
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

    void getTopBatsManInGivenYearPerVenue(List<Deliveries> deliveries, int[] indicesOfDeliveriesForGivenYear, int[] matchIdsOfMatchesForGivenYear, List<Matches> matches)
    {

        HashMap<HashMap<String,String>,Integer> playersWithMaxScoreInGivenYear=new HashMap<>();

        int j=indicesOfDeliveriesForGivenYear[ZERO]+1;
        for(int i=matchIdsOfMatchesForGivenYear[ZERO];i<=matchIdsOfMatchesForGivenYear[ONE];i+=1)
        {
           for(;j<indicesOfDeliveriesForGivenYear[1] && deliveries.get(j).getMatch_id()==i;j++) {
               HashMap<String, String> cityAndPlayer = new HashMap<>();
               cityAndPlayer.put(matches.get(i-1).getVenue(), deliveries.get(j).getBatsman());

               int batsman_Runs = (int) deliveries.get(j).getBatsman_runs();

               if (playersWithMaxScoreInGivenYear.containsKey(cityAndPlayer)) {
                   batsman_Runs += playersWithMaxScoreInGivenYear.get(cityAndPlayer);
                   playersWithMaxScoreInGivenYear.put(cityAndPlayer, batsman_Runs);
               } else {
                   playersWithMaxScoreInGivenYear.put(cityAndPlayer, batsman_Runs);
               }
           }
        }
        System.out.println("Batsman with most runs in every stadium for given year:");
      getPlayersWithMaxRunsPerStadium(playersWithMaxScoreInGivenYear);
    }

    void getPlayersWithMaxRunsPerStadium(HashMap<HashMap<String,String>,Integer> playersWithMaxScoreInGivenYear)
    {
        HashMap<String,HashMap<String,Integer>> playersWithMaxRunsPerStadium=new HashMap<>();

        for (Map.Entry<HashMap<String, String>,Integer> entry : playersWithMaxScoreInGivenYear.entrySet()) {
           for(Map.Entry<String,String> secondEntry: entry.getKey().entrySet())
           {
               String venue=secondEntry.getKey();
               String batsMan=secondEntry.getValue();
               Integer maxRuns=entry.getValue();
               HashMap<String,Integer> batsManAndHisRuns=new HashMap<>();

               if(playersWithMaxRunsPerStadium.containsKey(venue)) {
                 for(Map.Entry<String,Integer> thirdEntry :playersWithMaxRunsPerStadium.get(venue).entrySet())
                 {
                     Integer runs=thirdEntry.getValue();
                     if(runs<maxRuns)
                     {
                         batsManAndHisRuns.put(batsMan,maxRuns);
                         playersWithMaxRunsPerStadium.put(venue,batsManAndHisRuns);
                     }
                 }
               }else{
                   batsManAndHisRuns.put(batsMan,maxRuns);
                   playersWithMaxRunsPerStadium.put(venue,batsManAndHisRuns);
               }
           }
        }
        printData(playersWithMaxRunsPerStadium);
    }
}
