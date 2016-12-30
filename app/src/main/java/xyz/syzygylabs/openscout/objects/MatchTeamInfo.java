package xyz.syzygylabs.openscout.objects;

/**
 * Created by Charlie on 12/28/2016.
 */

public class MatchTeamInfo {
    TeamNoRobot team;
    Robot robot;
    TeamStat stat;
    public  MatchTeamInfo(TeamNoRobot team, TeamStat stat, Robot robot){
        this.team=team;
        this.stat=stat;
        this.robot=robot;
    }
    public TeamNoRobot getTeam(){
        return team;
    }
    public TeamStat getStat(){
        return stat;
    }
    public Robot getRobot(){
        return robot;
    }
}
