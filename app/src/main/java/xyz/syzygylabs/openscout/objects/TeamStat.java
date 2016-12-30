package xyz.syzygylabs.openscout.objects;

/**
 * Created by Charlie on 12/27/2016.
 */

public class TeamStat {
    private String events;
    private String oprs;
    private String ccwms;
    private String dprs;
    public TeamStat(String events, String oprs, String ccwms, String dprs){
        this.events=events;
        this.oprs=oprs;
        this.ccwms=ccwms;
        this.dprs=dprs;
    }
    public String getEvents(){
        return events;
    }
    public String getOprs(){
        return oprs;
    }
    public String getCcwms(){
        return ccwms;
    }
    public String getDprs(){
        return dprs;
    }
}
