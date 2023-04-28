package com.surendra.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {

    static List<String[]> matches, deliveries;

    public static void main(String[] args) {

        Main main=new Main();
        main.fillDataFromFiles();
        List<String> headingsIndexOfMatches= Arrays.asList(matches.get(0));
        List<String> headingsIndexOfDeliveries=Arrays.asList(deliveries.get(0));


       final int lenFileMatches= matches.size();

        Scenarios scenario=new Scenarios();

        printData(scenario.getMatchesPlayedOrWonForAllYears(headingsIndexOfMatches.indexOf("season"),1,lenFileMatches,new HashMap<>()));          //scenario 1
        printData(scenario.getMatchesPlayedOrWonForAllYears(headingsIndexOfMatches.indexOf("winner"),1,lenFileMatches,new HashMap<>()));         //scenario 2
        scenario.printExtraRunsPerTeamInYear(scenario.getStartAndEndIndicesOfDeliveriesForMatchIds(scenario.startAndEndMatchID(matches,headingsIndexOfMatches.indexOf("season"),"2016")),headingsIndexOfDeliveries.indexOf("bowling_team")); //scenario 3
        scenario.printEconomyRateForBowlers(scenario.getStartAndEndIndicesOfDeliveriesForMatchIds(scenario.startAndEndMatchID(matches,headingsIndexOfMatches.indexOf("season"),"2015")));                   //scenario 4
        scenario.getTopPlayerOfTheMatchInGivenYear("2016");                                            //scenario 5


    }
    void fillDataFromFiles()
    {
        FileParse fileParse=new FileParse();
        try {
            matches =fileParse.csvParse(new FileReader("/home/surendra/IdeaProjects/ipl_project/src/resources/matches.csv"));
            deliveries =fileParse.csvParse( new FileReader("/home/surendra/IdeaProjects/ipl_project/src/resources/deliveries.csv"));
        } catch(FileNotFoundException f) {
            f.printStackTrace();
        }
    }

    static void printData(HashMap<String,Integer> data)
    {
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();
    }


}