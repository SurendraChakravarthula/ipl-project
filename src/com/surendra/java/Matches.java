package com.surendra.java;

public class Matches {
    private int id;
    private int season;
    private String city;
    private String date;
    private String team1;
    private String team2;
    private String toss_winner;
    private String toss_decision;
    private String result;
    private int dl_applied;
    private String winner;
    private int win_by_runs;
    private int win_by_wickets;
    private String player_of_match;
    private String venue;
    private String umpire1;
    private String umpire2;
    private String umpire3;

    public Matches(int id, int season, String city, String date, String team1, String team2, String toss_winner, String toss_decision, String result, int dl_applied, String winner, int win_by_runs, int win_by_wickets, String player_of_match, String venue, String umpire1, String umpire2, String umpire3) {
        this.id = id;
        this.season = season;
        this.city = city;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.toss_winner = toss_winner;
        this.toss_decision = toss_decision;
        this.result = result;
        this.dl_applied = dl_applied;
        this.winner = winner;
        this.win_by_runs = win_by_runs;
        this.win_by_wickets = win_by_wickets;
        this.player_of_match = player_of_match;
        this.venue = venue;
        this.umpire1 = umpire1;
        this.umpire2 = umpire2;
        this.umpire3 = umpire3;
    }

    public void getId(int id) {
        this.id = id;
    }

    public int getSeason() {
        return season;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getToss_winner() {
        return toss_winner;
    }

    public String getToss_decision() {
        return toss_decision;
    }

    public String getResult() {
        return result;
    }

    public int getDl_applied() {
        return dl_applied;
    }

    public String getWinner() {
        return winner;
    }

    public String getUmpire2() {
        return umpire2;
    }

    public String getUmpire3() {
        return umpire3;
    }

    public int getWin_by_runs() {
        return win_by_runs;
    }

    public int getWin_by_wickets() {
        return win_by_wickets;
    }

    public String getPlayer_of_match() {
        return player_of_match;
    }

    public String getVenue() {
        return venue;
    }

    public String getUmpire1() {
        return umpire1;
    }
}
