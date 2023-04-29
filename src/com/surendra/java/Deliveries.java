package com.surendra.java;

public class Deliveries {
     private int match_id;
    private int inning;
    private String batting_team;
    private String bowling_team;
    private int over;
    private int ball;
    private String batsman;
    private String non_striker;
    private String bowler;
    private int its_super_over;
    private float wide_runs;
    private int bye_runs;
    private int legbye_runs;
    private float noball_runs;
    private int penalty_runs;
    private float batsman_runs;
    private int extra_runs;
    private int total_runs;
    private String player_dismissed;
    private String dismissal_kind;
    private String fielder;

    public Deliveries(int match_id, int inning, String batting_team, String bowling_team, int over, int ball, String batsman, String non_striker, String bowler, int its_super_over, float wide_runs, int bye_runs,
                      int legbye_runs, float noball_runs, int penalty_runs, float batsman_runs, int extra_runs, int total_runs, String player_dismissed, String dismissal_kind, String fielder) {
        this.match_id = match_id;
        this.inning = inning;
        this.batting_team = batting_team;
        this.bowling_team = bowling_team;
        this.over = over;
        this.ball = ball;
        this.batsman = batsman;
        this.non_striker = non_striker;
        this.bowler = bowler;
        this.its_super_over = its_super_over;
        this.wide_runs = wide_runs;
        this.bye_runs = bye_runs;
        this.legbye_runs = legbye_runs;
        this.noball_runs = noball_runs;
        this.penalty_runs = penalty_runs;
        this.batsman_runs = batsman_runs;
        this.extra_runs = extra_runs;
        this.total_runs = total_runs;
        this.player_dismissed = player_dismissed;
        this.dismissal_kind = dismissal_kind;
        this.fielder = fielder;
    }

    public int getMatch_id() {
        return match_id;
    }

    public int getInning() {
        return inning;
    }

    public String getBatting_team() {
        return batting_team;
    }

    public String getBowling_team() {
        return bowling_team;
    }

    public int getOver() {
        return over;
    }

    public int getBall() {
        return ball;
    }

    public String getBatsman() {
        return batsman;
    }

    public String getNon_striker() {
        return non_striker;
    }

    public String getBowler() {
        return bowler;
    }

    public int getIts_super_over() {
        return its_super_over;
    }

    public float getWide_runs() {
        return wide_runs;
    }

    public int getBye_runs() {
        return bye_runs;
    }

    public int getLegbye_runs() {
        return legbye_runs;
    }

    public float getNoball_runs() {
        return noball_runs;
    }

    public int getPenalty_runs() {
        return penalty_runs;
    }

    public float getBatsman_runs() {
        return batsman_runs;
    }

    public int getExtra_runs() {
        return extra_runs;
    }

    public int getTotal_runs() {
        return total_runs;
    }

    public String getPlayer_dismissed() {
        return player_dismissed;
    }

    public String getDismissal_kind() {
        return dismissal_kind;
    }

    public String getFielder() {
        return fielder;
    }

}
