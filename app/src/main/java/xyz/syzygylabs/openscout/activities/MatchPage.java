package xyz.syzygylabs.openscout.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.adapter.MatchPageRecycler;
import xyz.syzygylabs.openscout.api.ApiClient;
import xyz.syzygylabs.openscout.api.ApiInterface;
import xyz.syzygylabs.openscout.objects.MatchTeamInfo;
import xyz.syzygylabs.openscout.objects.Robot;
import xyz.syzygylabs.openscout.objects.TeamNoRobot;
import xyz.syzygylabs.openscout.objects.TeamStat;
import xyz.syzygylabs.openscout.objects.match.Match;

public class MatchPage extends AppCompatActivity {
    String matchKey;
    ArrayList<TeamNoRobot> teamInfoRed = new ArrayList<TeamNoRobot>();
    ArrayList<TeamNoRobot> teamInfoBlue = new ArrayList<TeamNoRobot>();
    ArrayList<TeamStat> statsRed = new ArrayList<TeamStat>();
    ArrayList<TeamStat> statsBlue = new ArrayList<TeamStat>();
    ArrayList<Robot> robotsRed = new ArrayList<Robot>();
    ArrayList<Robot> robotsBlue = new ArrayList<Robot>();
    ArrayList<MatchTeamInfo> matchTeamInfosRed = new ArrayList<MatchTeamInfo>();
    ArrayList<MatchTeamInfo> matchTeamInfosBlue = new ArrayList<MatchTeamInfo>();
    List<String> redTeams;
    List<String> blueTeams;
    ApiInterface apiService;
    RecyclerView recyclerView;
    RecyclerView recyclerViewBlue;
    TextView matchName;
    TextView matchLevel;
    TextView red1;
    TextView red2;
    TextView red3;
    TextView red4;
    TextView blue1;
    TextView blue2;
    TextView blue3;
    TextView blue4;
    TextView blueScoreTV;
    TextView redScoreTV;
    ProgressWheel wheelRed;
    ProgressWheel wheelBlue;
    int val=0;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_page);
        Intent intent = getIntent();
        matchKey = intent.getStringExtra("key");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        matchName = (TextView) findViewById(R.id.matchName);
        matchLevel = (TextView) findViewById(R.id.matchLevel);
        red1 = (TextView) findViewById(R.id.red1);
        red2 = (TextView) findViewById(R.id.red2);
        red3 = (TextView) findViewById(R.id.red3);
        red4 = (TextView) findViewById(R.id.red4);
        blue1 = (TextView) findViewById(R.id.blue1);
        blue2 = (TextView) findViewById(R.id.blue2);
        blue3 = (TextView) findViewById(R.id.blue3);
        blue4 = (TextView) findViewById(R.id.blue4);
        blueScoreTV = (TextView) findViewById(R.id.blueScore);
        redScoreTV = (TextView) findViewById(R.id.redScore);
        wheelRed = (ProgressWheel) findViewById(R.id.progress_red);
        wheelBlue = (ProgressWheel) findViewById(R.id.progress_blue);
        Call<Match> call = apiService.getMatchStats(matchKey);
        //TODO update for 2017
        call.enqueue(new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                redTeams = response.body().getAlliances().getRed().getTeams();
                blueTeams = response.body().getAlliances().getBlue().getTeams();
                matchName.setText("#" + response.body().getMatchNumber());
                if (response.body().getCompLevel().equals("qf")){
                    matchLevel.setText("Quarter Finals");
                }
                if (response.body().getCompLevel().equals("qm")){
                    matchLevel.setText("Qualification");
                }
                if (response.body().getCompLevel().equals("sm")){
                    matchLevel.setText("Semi Finals");
                }
                if (response.body().getCompLevel().equals("f")){
                    matchLevel.setText("Finals");
                }
                redTeams = response.body().getAlliances().getRed().getTeams();
                red1.setText(redTeams.get(0).toString().substring(3));
                red2.setText(redTeams.get(1).toString().substring(3));
                red3.setText(redTeams.get(2).toString().substring(3));
                if (redTeams.size()>3){
                    red4.setText(redTeams.get(3).toString().substring(3));
                }
                blueTeams = response.body().getAlliances().getBlue().getTeams();
                blue1.setText(blueTeams.get(0).toString().substring(3));
                blue2.setText(blueTeams.get(1).toString().substring(3));
                blue3.setText(blueTeams.get(2).toString().substring(3));
                if (blueTeams.size()>3){
                    blue4.setText(blueTeams.get(3).toString().substring(3));
                }
                if(response.body().getScoreBreakdown().getBlue().getTotalPoints() != 0){
                    blueScoreTV.setText(response.body().getScoreBreakdown().getBlue().getTotalPoints()+"");
                    blueScoreTV.setVisibility(View.VISIBLE);

                }
                if(response.body().getScoreBreakdown().getRed().getTotalPoints() != 0){
                    redScoreTV.setText(response.body().getScoreBreakdown().getRed().getTotalPoints()+"");
                    redScoreTV.setVisibility(View.VISIBLE);
                }
                int blueScore = response.body().getScoreBreakdown().getBlue().getTotalPoints();
                int redScore = response.body().getScoreBreakdown().getRed().getTotalPoints();
                if(blueScore>redScore){
                    blueScoreTV.setTypeface(Typeface.DEFAULT_BOLD);
                    redScoreTV.setTypeface(Typeface.DEFAULT);
                }
                if(redScore>blueScore){
                    redScoreTV.setTypeface(Typeface.DEFAULT_BOLD);
                    blueScoreTV.setTypeface(Typeface.DEFAULT);
                }
                if(redScore==blueScore){
                    blueScoreTV.setTypeface(Typeface.DEFAULT);
                    redScoreTV.setTypeface(Typeface.DEFAULT);
                }
                get();
            }
            @Override
            public void onFailure(Call<Match> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(getBaseContext(), Login.class);
                startActivity(loginIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void get(){
        /*Team info calls*/
        Call<TeamNoRobot> teamInfoCallRed = apiService.getTeam(redTeams.get(val));
        teamInfoCallRed.enqueue(new Callback<TeamNoRobot>() {
            @Override
            public void onResponse(Call<TeamNoRobot> call, Response<TeamNoRobot> response) {
                teamInfoRed.add(response.body());
                    /*Stats calls*/
                int end = matchKey.indexOf("_");
                String eventKey = matchKey.substring(0,end);
                Call<JsonElement> eventData = apiService.getEventStats(eventKey);
                eventData.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JsonObject oprs_ = (JsonObject) response.body().getAsJsonObject().get("oprs");
                            JsonObject ccwms_ = (JsonObject) response.body().getAsJsonObject().get("ccwms");
                            JsonObject dprs_ = (JsonObject) response.body().getAsJsonObject().get("dprs");
                            statsRed.add(new TeamStat("NA", oprs_.get(redTeams.get(val).substring(3)).toString().substring(0,4),
                                    ccwms_.get(redTeams.get(val).substring(3)).toString().substring(0,4), dprs_.get(redTeams.get(val).substring(3)).toString().substring(0,4)));
                            mDatabase.child("teams").child(redTeams.get(val).substring(3)).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Robot robot = dataSnapshot.getValue(Robot.class);
                                            if (dataSnapshot.exists()) {
                                                String robotType = robot.getRobotType();
                                                String robotDriveTrain = robot.getRobotDrivetrain();
                                                String robotName = robot.getRobotName();
                                                String robotComments = robot.getRobotComments();
                                                String robotProgramingEnvironment = robot.getRobotProgramingEnvironment();
                                                Boolean isTeamScouted = robot.getIsScouted();
                                                Boolean robotHasVision = robot.getRobotHasVision();
                                                String robotSpeed = robot.getSpeed();
                                                int robotNumberOfMotors = robot.getRobotNumberOfMotors();

                                                robotsRed.add(new Robot((robotName != null) ? robotName : "NA", (robotType != null) ? robotType : "NA", (robotDriveTrain != null) ? robotDriveTrain : "NA",
                                                        (robotNumberOfMotors != 0) ? robotNumberOfMotors : 0, (robotComments != null) ? robotComments : "NA", (robotHasVision != null) ? robotHasVision : false,
                                                        (robotProgramingEnvironment != null) ? robotProgramingEnvironment : "NA", (isTeamScouted != null) ? isTeamScouted : false, (robotSpeed != null) ? robotSpeed : "NA"));
                                            } else {
                                                robotsRed.add(new Robot("NA", "NA", "NA", 0, "NA", false, "NA", false, "NA"));
                                            }
                                            matchTeamInfosRed.add(new MatchTeamInfo(teamInfoRed.get(val), statsRed.get(val), robotsRed.get(val)));
                                            if(val+1>=redTeams.size()){
                                                val=0;
                                                getBlue();
                                            }else{
                                                val++;
                                                get();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Snackbar.make(findViewById(android.R.id.content), "Check Firebase permissions", Snackbar.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Snackbar.make(findViewById(android.R.id.content), "Data is unavailable for match " + matchKey, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<TeamNoRobot> call, Throwable t) {

            }
        });
    }
    void getBlue(){
        Call<TeamNoRobot> teamInfoCallBlue = apiService.getTeam(blueTeams.get(val));
        teamInfoCallBlue.enqueue(new Callback<TeamNoRobot>() {
            @Override
            public void onResponse(Call<TeamNoRobot> call, Response<TeamNoRobot> response) {
                teamInfoBlue.add(response.body());
                    /*Stats calls*/
                int end = matchKey.indexOf("_");
                String eventKey = matchKey.substring(0,end);
                Call<JsonElement> eventData = apiService.getEventStats(eventKey);
                eventData.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JsonObject oprs_ = (JsonObject) response.body().getAsJsonObject().get("oprs");
                            JsonObject ccwms_ = (JsonObject) response.body().getAsJsonObject().get("ccwms");
                            JsonObject dprs_ = (JsonObject) response.body().getAsJsonObject().get("dprs");
                            statsBlue.add(new TeamStat("NA", oprs_.get(blueTeams.get(val).substring(3)).toString().substring(0,4),
                                    ccwms_.get(blueTeams.get(val).substring(3)).toString().substring(0,4), dprs_.get(blueTeams.get(val).substring(3)).toString().substring(0,4)));
                            mDatabase.child("teams").child(blueTeams.get(val)).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Robot robot = dataSnapshot.getValue(Robot.class);
                                            if (dataSnapshot.exists()) {
                                                String robotType = robot.getRobotType();
                                                String robotDriveTrain = robot.getRobotDrivetrain();
                                                String robotName = robot.getRobotName();
                                                String robotComments = robot.getRobotComments();
                                                String robotProgramingEnvironment = robot.getRobotProgramingEnvironment();
                                                Boolean isTeamScouted = robot.getIsScouted();
                                                Boolean robotHasVision = robot.getRobotHasVision();
                                                String robotSpeed = robot.getSpeed();
                                                int robotNumberOfMotors = robot.getRobotNumberOfMotors();

                                                robotsBlue.add(new Robot((robotName != null) ? robotName : "NA", (robotType != null) ? robotType : "NA", (robotDriveTrain != null) ? robotDriveTrain : "NA",
                                                        (robotNumberOfMotors != 0) ? robotNumberOfMotors : 0, (robotComments != null) ? robotComments : "NA", (robotHasVision != null) ? robotHasVision : false,
                                                        (robotProgramingEnvironment != null) ? robotProgramingEnvironment : "NA", (isTeamScouted != null) ? isTeamScouted : false, (robotSpeed != null) ? robotSpeed : "NA"));
                                            } else {
                                                robotsBlue.add(new Robot("NA", "NA", "NA", 0, "NA", false, "NA", false, "NA"));
                                            }
                                            matchTeamInfosBlue.add(new MatchTeamInfo(teamInfoBlue.get(val), statsBlue.get(val), robotsBlue.get(val)));
                                            if(val+1>=blueTeams.size()){
                                                val=0;
                                                wheelBlue.setVisibility(View.GONE);
                                                wheelRed.setVisibility(View.GONE);
                                                recyclerView = (RecyclerView) findViewById(R.id.recycler);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                                recyclerView.setAdapter(new MatchPageRecycler(matchTeamInfosRed));
                                                //recyclerView.setNestedScrollingEnabled(false);
                                                recyclerViewBlue = (RecyclerView) findViewById(R.id.recyclerBlue);
                                                recyclerViewBlue.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                                recyclerViewBlue.setAdapter(new MatchPageRecycler(matchTeamInfosBlue));
                                                //recyclerViewBlue.setNestedScrollingEnabled(false);
                                            }else{
                                                val++;
                                                getBlue();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Snackbar.make(findViewById(android.R.id.content), "Check Firebase permissions", Snackbar.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Snackbar.make(findViewById(android.R.id.content), "Data is unavailable for match " + matchKey, Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
            }
            @Override
            public void onFailure(Call<TeamNoRobot> call, Throwable t) {

            }
        });
    }
}
