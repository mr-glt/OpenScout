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
    public  Robot(){

    }
    public Robot(String robotName, String robotType, String robotDrivetrain, int robotNumberOfMotors, String robotComments,
                 Boolean robotHasVision, String robotProgramingEnvironment, Boolean isScouted, String speed){
        this.robotName = robotName;
        this.robotType = robotType;
        this.robotDrivetrain = robotDrivetrain;
        this.robotNumberOfMotors = robotNumberOfMotors;
        this.robotComments = robotComments;
        this.robotHasVision = robotHasVision;
        this.robotProgramingEnvironment = robotProgramingEnvironment;
        this.isScouted = isScouted;
        this.speed=speed;
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
}
