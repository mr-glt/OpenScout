package xyz.syzygylabs.openscout.objects;

/**
 * Created by Charlie on 12/21/2016.
 */

public class Robot {
    private String robotName;
    private String robotType;
    private String robotDrivetrain;
    private int robotNumberOfMotors;
    private String robotComments;
    private Boolean robotHasVision;
    private String robotProgramingEnvironment;
    private Boolean isScouted;
    private String speed;
    private String fps;
    private String accuracy;
    private String climbTime;
    private String hopper;
    private Boolean gear;
    private Boolean shoot;
    private Boolean climb;
    private Boolean defend;
    public  Robot(){

    }
    public Robot(String robotName, String robotType, String robotDrivetrain, int robotNumberOfMotors, String robotComments,
                 Boolean robotHasVision, String robotProgramingEnvironment, Boolean isScouted, String speed, String fps, String accuracy,
                 String climbTime, String hopper, Boolean gear, Boolean shoot, Boolean climb, Boolean defend){
        this.robotName = robotName;
        this.robotType = robotType;
        this.robotDrivetrain = robotDrivetrain;
        this.robotNumberOfMotors = robotNumberOfMotors;
        this.robotComments = robotComments;
        this.robotHasVision = robotHasVision;
        this.robotProgramingEnvironment = robotProgramingEnvironment;
        this.isScouted = isScouted;
        this.speed=speed;
        this.fps=fps;
        this.accuracy=accuracy;
        this.climbTime=climbTime;
        this.hopper=hopper;
        this.gear=gear;
        this.shoot=shoot;
        this.climb=climb;
        this.defend=defend;
    }

    public String getRobotName(){
        return robotName;
    }
    public String getRobotType(){
        return robotType;
    }
    public String getRobotDrivetrain(){
        return robotDrivetrain;
    }
    public int getRobotNumberOfMotors(){
        return robotNumberOfMotors;
    }
    public String getRobotComments(){
        return robotComments;
    }
    public Boolean getRobotHasVision(){
        return robotHasVision;
    }
    public String getRobotProgramingEnvironment(){
        return robotProgramingEnvironment;
    }
    public Boolean getIsScouted(){
        return isScouted;
    }
    public String getSpeed(){
        return speed;
    }
    public String getFps(){
        return fps;
    }
    public String getAccuracy(){
        return accuracy;
    }
    public String getClimbTime(){
        return climbTime;
    }
    public String getHopper(){
        return hopper;
    }
    public Boolean getGear(){
        return gear;
    }
    public Boolean getShoot(){
        return shoot;
    }
    public Boolean getClimb(){
        return climb;
    }
    public Boolean getDefend(){
        return defend;
    }
}
