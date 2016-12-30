package xyz.syzygylabs.openscout.objects;

/**
 * Created by colso on 12/21/2016.
 */

public class Team {
    private String teamName;//
    private String teamNumber;//
    private String teamLocation;//
    private String teamFirstYear;
    private String teamMotto;
    private String teamWebsite;
    private String teamLocality;//
    private String teamRegion;//
    private String teamNickname;//
    private String teamCountry;//
    private String teamKey;//
    private Robot robot;
    public Team(String teamNickname, String teamName, String teamNumber, String teamLocation, String teamLocality, String teamRegion
            , String teamCountry, String teamKey, String teamMotto, String teamWebsite, String teamFirstYear, Robot robot){
        this.teamNickname = teamNickname;
        this.teamName = teamName;
        this.teamNumber = teamNumber;
        this.teamLocation = teamLocation;
        this.teamLocality = teamLocality;
        this.teamRegion = teamRegion;
        this.teamCountry = teamCountry;
        this.teamKey = teamKey;
        this.teamMotto = teamMotto;
        this.teamKey = teamKey;
        this.teamWebsite = teamWebsite;
        this.teamFirstYear = teamFirstYear;
        this.robot = robot;
    }
    public String getTeamNickname(){
        return teamNickname;
    }
    public String getTeamName(){
        return teamName;
    }
    public String getTeamNumber(){
        return teamNumber;
    }
    public String getTeamLocation(){
        return teamLocation;
    }
    public String getTeamLocality(){
        return teamLocality;
    }
    public String getTeamRegion(){
        return teamRegion;
    }
    public String getTeamCountry(){
        return teamCountry;
    }
    public String getTeamKey(){
        return teamKey;
    }
    public String getTeamMotto(){
        return teamMotto;
    }
    public String getTeamWebsite(){
        return teamWebsite;
    }
    public String getTeamFirstYear(){
        return teamFirstYear;
    }
    public Robot getRobot(){
        return robot;
    }

}
