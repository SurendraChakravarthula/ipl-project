package com.surendra.java;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;

public class Main {
    private final int START = 0;
    private final int END = 1;

    private final int ID = 0;
    private final int SEASON = 1;
    private final int CITY = 2;
    private final int DATE = 3;
    private final int TEAM1 = 4;
    private final int TEAM2 = 5;
    private final int TOSS_WINNER = 6;
    private final int TOSS_DECISION = 7;
    private final int RESULT = 8;
    private final int DL_APPLIED = 9;
    private final int WINNER = 10;
    private final int WIN_BY_RUNS = 11;
    private final int WIN_BY_WICKETS = 12;
    private final int PLAYER_OF_MATCH = 13;
    private final int VENUE = 14;
    private final int UMPIRE1 = 15;
    private final int UMPIRE2 = 16;
    private final int UMPIRE3 = 17;

    private final int MATCH_ID = 0;
    private final int INNING = 1;
    private final int BATTING_TEAM = 2;
    private final int BOWLING_TEAM = 3;
    private final int OVER = 4;
    private final int BALL = 5;
    private final int BATSMAN = 6;
    private final int NON_STRIKER = 7;
    private final int BOWLER = 8;
    private final int IS_SUPER_OVER = 9;
    private final int WIDE_RUNS = 10;
    private final int BYE_RUNS = 11;
    private final int LEGBYE_RUNS = 12;
    private final int NOBALL_RUNS = 13;
    private final int PENALTY_RUNS = 14;
    private final int BATSMAN_RUNS = 15;
    private final int EXTRA_RUNS = 16;
    private final int TOTAL_RUNS = 17;
    private final int PLAYER_DISMISSED = 18;
    private final int DISMISSAL_KIND = 19;
    private final int FIELDER = 20;

    public static void main(String[] args) {
        Main main = new Main();

        List<Matches> matches = main.parseMatches(new ArrayList<>());
        List<Deliveries> deliveries = main.parseDeliveries(new ArrayList<>());

        final int lengthOfMatches = matches.size();
        final int lengthOfDeliveries = deliveries.size();

        HashMap<Integer, Integer> numberOfMatchesPlayedPerYear = new HashMap<>();
        main.getNumberOfMatchesPlayedPerYear(matches, numberOfMatchesPlayedPerYear);

        main.getNumberOfMatchesWonPerTeam(matches);

        int year = 2016;
        int[] startAndEndMatchIdsOfYear2016 = main.getStartAndEndMatchIdsOfGivenYear(matches, year, lengthOfMatches);
        int[] startAndEndIndicesOfDeliveriesForMatchIdsOf2016 = main.getStartAndEndIndicesOfDeliveriesForMatchIds(deliveries, startAndEndMatchIdsOfYear2016, lengthOfDeliveries);
        main.getConcededRunsPerTeamForGivenIndices(deliveries, startAndEndIndicesOfDeliveriesForMatchIdsOf2016);

        year = 2015;
        int[] startAndEndMatchIdsOfYear2015 = main.getStartAndEndMatchIdsOfGivenYear(matches, year, lengthOfMatches);
        int[] startAndEndIndicesOfDeliveriesForMatchIdsOf2015 = main.getStartAndEndIndicesOfDeliveriesForMatchIds(deliveries, startAndEndMatchIdsOfYear2015, lengthOfDeliveries);
        HashMap<String, Float> economyRatePerBowler = main.getEconomyRatePerBowler(deliveries, startAndEndIndicesOfDeliveriesForMatchIdsOf2015, new HashMap<>());
        main.sortBowlersByEconomyRate(economyRatePerBowler);
        main.getTopPlayerOfTheMatchInGivenYear(matches, year, lengthOfMatches);
        main.getTopBatsManInGivenYearPerVenue(deliveries, startAndEndIndicesOfDeliveriesForMatchIdsOf2016, startAndEndMatchIdsOfYear2016, matches);
    }

    private List<Matches> parseMatches(List<Matches> matches) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/surendra/IdeaProjects/ipl_project/src/resources/matches.csv"));
            String line;
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(",", -1);
                Matches match = new Matches();

                match.setId(Integer.parseInt(lineData[ID]));
                match.setSeason(Integer.parseInt(lineData[SEASON]));
                match.setCity(lineData[CITY]);
                match.setDate(lineData[DATE]);
                match.setTeam1(lineData[TEAM1]);
                match.setTeam2(lineData[TEAM2]);
                match.setToss_winner(lineData[TOSS_WINNER]);
                match.setToss_decision(lineData[TOSS_DECISION]);
                match.setResult(lineData[RESULT]);
                match.setDl_applied(Integer.parseInt(lineData[DL_APPLIED]));
                match.setWinner(lineData[WINNER]);
                match.setWin_by_runs(Integer.parseInt(lineData[WIN_BY_RUNS]));
                match.setWin_by_wickets(Integer.parseInt(lineData[WIN_BY_WICKETS]));
                match.setPlayer_of_match(lineData[PLAYER_OF_MATCH]);
                match.setVenue(lineData[VENUE]);
                match.setUmpire1(lineData[UMPIRE1]);
                match.setUmpire2(lineData[UMPIRE2]);
                match.setUmpire3(lineData[UMPIRE3]);

                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }

    private List<Deliveries> parseDeliveries(List<Deliveries> deliveries) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/surendra/IdeaProjects/ipl_project/src/resources/deliveries.csv"));
            String line;
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(",", -1);
                Deliveries delivery = new Deliveries();

                delivery.setMatch_id(Integer.parseInt(lineData[MATCH_ID]));
                delivery.setInning(Integer.parseInt(lineData[INNING]));
                delivery.setBatting_team(lineData[BATTING_TEAM]);
                delivery.setBowling_team(lineData[BOWLING_TEAM]);
                delivery.setOver(Integer.parseInt(lineData[OVER]));
                delivery.setBall(Integer.parseInt(lineData[BALL]));
                delivery.setBatsman(lineData[BATSMAN]);
                delivery.setNon_striker(lineData[NON_STRIKER]);
                delivery.setBowler(lineData[BOWLER]);
                delivery.setIts_super_over(Integer.parseInt(lineData[IS_SUPER_OVER]));
                delivery.setWide_runs(Float.parseFloat(lineData[WIDE_RUNS]));
                delivery.setBye_runs(Integer.parseInt(lineData[BYE_RUNS]));
                delivery.setLegbye_runs(Integer.parseInt(lineData[LEGBYE_RUNS]));
                delivery.setNoball_runs(Float.parseFloat(lineData[NOBALL_RUNS]));
                delivery.setPenalty_runs(Integer.parseInt(lineData[PENALTY_RUNS]));
                delivery.setBatsman_runs(Float.parseFloat(lineData[BATSMAN_RUNS]));
                delivery.setExtra_runs(Integer.parseInt(lineData[EXTRA_RUNS]));
                delivery.setTotal_runs(Integer.parseInt(lineData[TOTAL_RUNS]));
                delivery.setPlayer_dismissed(lineData[PLAYER_DISMISSED]);
                delivery.setDismissal_kind(lineData[DISMISSAL_KIND]);
                delivery.setFielder(lineData[FIELDER]);

                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    private void getNumberOfMatchesPlayedPerYear(List<Matches> matches, HashMap<Integer, Integer> numberOfMatchesPlayedPerYear) {
        for (Matches match : matches) {
            int year = match.getSeason();
            numberOfMatchesPlayedPerYear.put(year, numberOfMatchesPlayedPerYear.getOrDefault(year, 0) + 1);
        }
        System.out.println("Number of matches played per year of all the years in IPL:");
        printData(numberOfMatchesPlayedPerYear);
    }

    private void getNumberOfMatchesWonPerTeam(List<Matches> matches) {
        HashMap<String, Integer> numberOfMatchesWonPerTeam = new HashMap<>();

        for (Matches match : matches) {
            String winner = match.getWinner();
            if (!winner.equals(""))
                numberOfMatchesWonPerTeam.put(winner, numberOfMatchesWonPerTeam.getOrDefault(winner, 0) + 1);
        }
        System.out.println("Number of matches won of all teams over all the years of IPL:");
        printData(numberOfMatchesWonPerTeam);
    }

    private int[] getStartAndEndMatchIdsOfGivenYear(List<Matches> matches, int year, int lengthOfMatches) {
        int[] matchIdsForGivenYear = new int[2];
        for (int i = 0; i < lengthOfMatches; i++) {
            if (matches.get(i).getSeason() == year) {
                matchIdsForGivenYear[START] = i + 1;
                while (i < lengthOfMatches && matches.get(i).getSeason() == year)
                    i++;
                matchIdsForGivenYear[END] = i;
                break;
            }
        }
        return matchIdsForGivenYear;
    }

    private int searchIndexForMatchId(List<Deliveries> deliveries, int indexOfMatchId, int lengthOfDeliveries) {

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

    private int[] getStartAndEndIndicesOfDeliveriesForMatchIds(List<Deliveries> deliveries, int[] matchIdForGivenYear, int lengthOfDeliveries) {
        int start = searchIndexForMatchId(deliveries, matchIdForGivenYear[START], lengthOfDeliveries);

        int[] matchIndicesForGivenYearInDeliveries = new int[2];
        while (start > -1 && deliveries.get(start).getMatch_id() == matchIdForGivenYear[START])
            start--;
        matchIndicesForGivenYearInDeliveries[START] = ++start;

        int end = searchIndexForMatchId(deliveries, matchIdForGivenYear[END], lengthOfDeliveries);

        while (end < lengthOfDeliveries && deliveries.get(end).getMatch_id() == matchIdForGivenYear[END])
            end++;
        matchIndicesForGivenYearInDeliveries[END] = end;

        return matchIndicesForGivenYearInDeliveries;
    }

    private void getConcededRunsPerTeamForGivenIndices(List<Deliveries> deliveries, int[] startAndEndIndicesOfDeliveriesForMatchIds) {
        HashMap<String, Integer> concededRunsPerTeamForGivenIndices = new HashMap<>();
        for (int i = startAndEndIndicesOfDeliveriesForMatchIds[START]; i < startAndEndIndicesOfDeliveriesForMatchIds[END]; i++) {

            String input = deliveries.get(i).getBowling_team();
            concededRunsPerTeamForGivenIndices.put(input, concededRunsPerTeamForGivenIndices.getOrDefault(input, 0) + deliveries.get(i).getExtra_runs());
        }
        System.out.println("Extra runs conceded per team in 2016:");
        printData(concededRunsPerTeamForGivenIndices);
        System.out.println();
    }

    private HashMap<String, Float> getEconomyRatePerBowler(List<Deliveries> deliveries, int[] matchIdForGivenYear, HashMap<String, Float> economyRatePerBowler) {
        HashMap<String, Pair> runsGivenAndBallsThrownByEveryBowler = new HashMap<>();
        for (int i = matchIdForGivenYear[START]; i < matchIdForGivenYear[END]; i++) {

            String bowler = deliveries.get(i).getBowler();
            float wide_Runs = deliveries.get(i).getWide_runs();
            float noball_runs = deliveries.get(i).getNoball_runs();
            float ballsThrown = 0;
            float runsGiven = wide_Runs + noball_runs + deliveries.get(i).getBatsman_runs();

            if (wide_Runs == 0 && noball_runs == 0)
                ballsThrown = 1;

            Pair pair = new Pair();

            if (runsGivenAndBallsThrownByEveryBowler.containsKey(bowler)) {
                pair.setBallsThrown(runsGivenAndBallsThrownByEveryBowler.get(bowler).getBallsThrown() + ballsThrown);
                pair.setRunsGiven(runsGivenAndBallsThrownByEveryBowler.get(bowler).getRunsGiven() + runsGiven);
                runsGivenAndBallsThrownByEveryBowler.put(bowler, pair);
            } else {
                pair.setRunsGiven(runsGiven);
                pair.setBallsThrown(ballsThrown);
                runsGivenAndBallsThrownByEveryBowler.put(bowler, pair);
            }
        }

        for (Map.Entry<String, Pair> entry : runsGivenAndBallsThrownByEveryBowler.entrySet()) {
            Pair pair = entry.getValue();
            economyRatePerBowler.put(entry.getKey(), pair.getRunsGiven() / (pair.getBallsThrown() / 6));
        }
        return economyRatePerBowler;
    }

    private void sortBowlersByEconomyRate(HashMap<String, Float> economyRate) {
        List<Map.Entry<String, Float>> sortEconomyRateForEveryBowler =
                new LinkedList<>(economyRate.entrySet());

        Collections.sort(sortEconomyRateForEveryBowler, new Comparator<>() {
            public int compare(Map.Entry<String, Float> economyRateOne,
                               Map.Entry<String, Float> economyRateTwo) {
                return (economyRateOne.getValue()).compareTo(economyRateTwo.getValue());
            }
        });

        HashMap<String, Float> sortedEconomyRateForEveryBowler = new LinkedHashMap<>();
        for (Map.Entry<String, Float> aa : sortEconomyRateForEveryBowler) {
            sortedEconomyRateForEveryBowler.put(aa.getKey(), aa.getValue());
        }
        System.out.println("Top economical bowlers in 2015:");

        int count = 0;
        for (Map.Entry<String, Float> entry : sortedEconomyRateForEveryBowler.entrySet()) {
            System.out.println(entry.getKey() + "         :      " + entry.getValue());
            count++;
            if (count == 5)
                break;
        }
        System.out.println();
    }

    private void getTopPlayerOfTheMatchInGivenYear(List<Matches> matches, int year, int lengthOfMatches) {
        HashMap<String, Integer> numberOfTitlesPerPlayer = new HashMap<>();
        int[] startAndEndMatchIdsOfGivenYear = getStartAndEndMatchIdsOfGivenYear(matches, year, lengthOfMatches);

        for (int i = startAndEndMatchIdsOfGivenYear[START] - 1; i < startAndEndMatchIdsOfGivenYear[END] - 1; i++) {
            String player = matches.get(i).getPlayer_of_match();
            numberOfTitlesPerPlayer.put(player, numberOfTitlesPerPlayer.getOrDefault(player, 0) + 1);
        }
        System.out.println("Player with most titles with player of the match in given year:");
        System.out.println(getPlayerWithMostTitles(numberOfTitlesPerPlayer));
        System.out.println();
    }

    private String getPlayerWithMostTitles(HashMap<String, Integer> hashMap) {
        int maximumTitles = Integer.MIN_VALUE;
        String player = "";

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            int value = entry.getValue();
            String key = entry.getKey();
            if (maximumTitles < value) {
                maximumTitles = value;
                player = key;
            }
        }
        return player;
    }

    private static <K, V> void printData(HashMap<K, V> data) {
        for (Map.Entry<K, V> entry : data.entrySet()) {
            System.out.println(entry.getKey() + "         :       " + entry.getValue());
        }
        System.out.println();
    }

    private void getTopBatsManInGivenYearPerVenue(List<Deliveries> deliveries, int[] indicesOfDeliveriesForGivenYear, int[] matchIdsOfMatchesForGivenYear, List<Matches> matches) {
        HashMap<HashMap<String, String>, Integer> playersWithMaxScoreInGivenYear = new HashMap<>();

        int j = indicesOfDeliveriesForGivenYear[START] + 1;
        for (int i = matchIdsOfMatchesForGivenYear[START]; i <= matchIdsOfMatchesForGivenYear[END]; i += 1) {
            for (; j < indicesOfDeliveriesForGivenYear[END] && deliveries.get(j).getMatch_id() == i; j++) {
                HashMap<String, String> cityAndPlayer = new HashMap<>();
                cityAndPlayer.put(matches.get(i - 1).getVenue(), deliveries.get(j).getBatsman());

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

    private void getPlayersWithMaxRunsPerStadium(HashMap<HashMap<String, String>, Integer> playersWithMaxScoreInGivenYear) {
        HashMap<String, HashMap<String, Integer>> playersWithMaxRunsPerStadium = new HashMap<>();

        for (Map.Entry<HashMap<String, String>, Integer> venueWithPLayerAndRuns : playersWithMaxScoreInGivenYear.entrySet()) {
            for (Map.Entry<String, String> venueAndPlayer : venueWithPLayerAndRuns.getKey().entrySet()) {
                String venue = venueAndPlayer.getKey();
                String batsMan = venueAndPlayer.getValue();
                Integer maxRuns = venueWithPLayerAndRuns.getValue();
                HashMap<String, Integer> batsManAndHisRuns = new HashMap<>();

                if (playersWithMaxRunsPerStadium.containsKey(venue)) {
                    for (Map.Entry<String, Integer> playerAndRuns : playersWithMaxRunsPerStadium.get(venue).entrySet()) {
                        Integer runs = playerAndRuns.getValue();
                        if (runs < maxRuns) {
                            batsManAndHisRuns.put(batsMan, maxRuns);
                            playersWithMaxRunsPerStadium.put(venue, batsManAndHisRuns);
                        }
                    }
                } else {
                    batsManAndHisRuns.put(batsMan, maxRuns);
                    playersWithMaxRunsPerStadium.put(venue, batsManAndHisRuns);
                }
            }
        }
        printData(playersWithMaxRunsPerStadium);
    }
}
