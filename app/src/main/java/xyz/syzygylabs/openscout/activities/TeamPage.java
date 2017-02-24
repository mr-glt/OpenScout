package xyz.syzygylabs.openscout.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.adapter.EventStatsRecycler;
import xyz.syzygylabs.openscout.api.ApiClient;
import xyz.syzygylabs.openscout.api.ApiInterface;
import xyz.syzygylabs.openscout.objects.Robot;
import xyz.syzygylabs.openscout.objects.TeamNoRobot;
import xyz.syzygylabs.openscout.objects.TeamStat;
import xyz.syzygylabs.openscout.objects.teamevents.TeamEvents;

public class TeamPage extends AppCompatActivity {
    ArrayList<String> teamEvents = new ArrayList<String>();
    ArrayList<String> eventNames = new ArrayList<String>();
    ArrayList<String> teamEventsThis = new ArrayList<String>();
    ArrayList<String> eventNamesThis = new ArrayList<String>();
    ArrayList<TeamStat> statsThisYear = new ArrayList<TeamStat>();
    ArrayList<TeamStat> statsLastYear = new ArrayList<TeamStat>();
    RecyclerView recyclerView;
    RecyclerView recyclerViewThis;
    String teamNumber;
    String teamRegion = null;
    String website = null;
    int rookieYear;
    boolean inEdit=false;
    String location = null;
    String motto = null;
    String nickname = null;
    DatabaseReference mDatabase;
    TextView type, driveTrain, codeType,speed,motors,comments, fps, accuracy, climbTime, hopper, fpsLabel, accuracyLabel, climbSpeedLabel;
    EditText typeET, driveTrainET, codeTypeET, speedET, motorsET, commentsET, fpsET, accuracyET, climbTimeET, hopperET;
    CheckBox gearsCB, shootCB, climbCB, defendCB, visionCB, isScouted;
    Button takePicBtn;
    FloatingActionButton fab;
    ImageView imageView;
    StorageReference storageReference;
    private static final int GALLERY_INTENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);
        Intent intent = getIntent();
        teamNumber = intent.getStringExtra("teamNum");

        load();

        storageReference = FirebaseStorage.getInstance().getReference();
        takePicBtn = (Button) findViewById(R.id.picture);
        imageView = (ImageView) findViewById(R.id.imageView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        isScouted = (CheckBox) findViewById(R.id.isScoutedCB);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inEdit){
                    if(!commentsET.getText().toString().equals("")) mDatabase.child("teams").child(teamNumber).child("robotComments").setValue(commentsET.getText().toString());
                    if(!driveTrainET.getText().toString().equals("")) mDatabase.child("teams").child(teamNumber).child("robotDrivetrain").setValue(driveTrainET.getText().toString());
                    if(!codeTypeET.getText().toString().equals("")) mDatabase.child("teams").child(teamNumber).child("robotProgramingEnvironment").setValue(codeTypeET.getText().toString());
                    if(!typeET.getText().toString().equals("")) mDatabase.child("teams").child(teamNumber).child("robotType").setValue(typeET.getText().toString());
                    if(!speedET.getText().toString().equals("")) mDatabase.child("teams").child(teamNumber).child("speed").setValue(speedET.getText().toString());
                    if(!motorsET.getText().toString().equals(""))mDatabase.child("teams").child(teamNumber).child("robotNumberOfMotors").setValue(Integer.parseInt(motorsET.getText().toString()));

                    if(!fps.getText().toString().equals(""))mDatabase.child("teams").child(teamNumber).child("fps").setValue(fpsET.getText().toString());
                    if(!accuracyET.getText().toString().equals(""))mDatabase.child("teams").child(teamNumber).child("accuracy").setValue(accuracyET.getText().toString());
                    if(!climbTimeET.getText().toString().equals(""))mDatabase.child("teams").child(teamNumber).child("climbTime").setValue(climbTimeET.getText().toString());
                    if(!hopperET.getText().toString().equals(""))mDatabase.child("teams").child(teamNumber).child("hopper").setValue(hopperET.getText().toString());
                    if(!shootCB.isChecked()) mDatabase.child("teams").child(teamNumber).child("fps").setValue("NA");
                    if(!shootCB.isChecked()) mDatabase.child("teams").child(teamNumber).child("accuracy").setValue("NA");
                    if(!climbCB.isChecked()) mDatabase.child("teams").child(teamNumber).child("climbTime").setValue("NA");


                    mDatabase.child("teams").child(teamNumber).child("robotHasVision").setValue(visionCB.isChecked());

                    mDatabase.child("teams").child(teamNumber).child("gear").setValue(gearsCB.isChecked());
                    mDatabase.child("teams").child(teamNumber).child("shoot").setValue(shootCB.isChecked());
                    mDatabase.child("teams").child(teamNumber).child("climb").setValue(climbCB.isChecked());
                    mDatabase.child("teams").child(teamNumber).child("defend").setValue(defendCB.isChecked());
                    gearsCB.setEnabled(false);
                    shootCB.setEnabled(false);
                    climbCB.setEnabled(false);
                    defendCB.setEnabled(false);

                    statsThisYear.clear();
                    statsLastYear.clear();
                    teamEvents.clear();
                    teamEventsThis.clear();

                    if(isScouted.isChecked()){
                        mDatabase.child("teams").child(teamNumber).child("isScouted").setValue(true);
                    }
                    else{
                        mDatabase.child("teams").child(teamNumber).child("isScouted").setValue(false);
                    }

                    isScouted.setVisibility(View.GONE);

                    inEdit=false;
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_mode_edit_white_24dp));

                    load();
                }
                else{
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_publish_white_24dp));
                    if(!type.getText().toString().equals("Need Data")){
                        typeET.setText(type.getText());
                        type.setVisibility(View.GONE);
                        typeET.setVisibility(View.VISIBLE);
                    }
                    else{
                        type.setVisibility(View.GONE);
                        typeET.setVisibility(View.VISIBLE);
                    }
                    if(!driveTrain.getText().toString().equals("Need Data")){
                        driveTrainET.setText(driveTrain.getText());
                        driveTrain.setVisibility(View.GONE);
                        driveTrainET.setVisibility(View.VISIBLE);
                    }
                    else{
                        driveTrain.setVisibility(View.GONE);
                        driveTrainET.setVisibility(View.VISIBLE);
                    }
                    if(!codeType.getText().toString().equals("Need Data")){
                        codeTypeET.setText(codeType.getText());
                        codeType.setVisibility(View.GONE);
                        codeTypeET.setVisibility(View.VISIBLE);
                    }
                    else{
                        codeType.setVisibility(View.GONE);
                        codeTypeET.setVisibility(View.VISIBLE);
                    }
                    if(!speed.getText().toString().equals("Need Data")){
                        speedET.setText(speed.getText());
                        speed.setVisibility(View.GONE);
                        speedET.setVisibility(View.VISIBLE);
                    }
                    else{
                        speed.setVisibility(View.GONE);
                        speedET.setVisibility(View.VISIBLE);
                    }
                    if(!motors.getText().toString().equals("Need Data")){
                        motorsET.setText(motors.getText());
                        motors.setVisibility(View.GONE);
                        motorsET.setVisibility(View.VISIBLE);
                    }
                    else{
                        motors.setVisibility(View.GONE);
                        motorsET.setVisibility(View.VISIBLE);
                    }

                    if(!fps.getText().toString().equals("Need Data")){
                        fpsET.setText(fps.getText());
                        fps.setVisibility(View.GONE);
                        fpsET.setVisibility(View.VISIBLE);
                    }
                    else{
                        fps.setVisibility(View.GONE);
                        fpsET.setVisibility(View.VISIBLE);
                    }
                    if(!hopper.getText().toString().equals("Need Data")){
                        hopperET.setText(hopper.getText());
                        hopper.setVisibility(View.GONE);
                        hopperET.setVisibility(View.VISIBLE);
                    }
                    else{
                        hopper.setVisibility(View.GONE);
                        hopperET.setVisibility(View.VISIBLE);
                    }
                    if(!accuracy.getText().toString().equals("Need Data")){
                        accuracyET.setText(accuracy.getText());
                        accuracy.setVisibility(View.GONE);
                        accuracyET.setVisibility(View.VISIBLE);
                    }
                    else{
                        accuracy.setVisibility(View.GONE);
                        accuracyET.setVisibility(View.VISIBLE);
                    }
                    if(!climbTime.getText().toString().equals("Need Data")){
                        climbTimeET.setText(climbTime.getText());
                        climbTime.setVisibility(View.GONE);
                        climbTimeET.setVisibility(View.VISIBLE);
                    }
                    else{
                        climbTime.setVisibility(View.GONE);
                        climbTimeET.setVisibility(View.VISIBLE);
                    }
                    gearsCB.setEnabled(true);
                    shootCB.setEnabled(true);
                    climbCB.setEnabled(true);
                    defendCB.setEnabled(true);
                    visionCB.setEnabled(true);
                    if(!comments.getText().toString().equals("Need Data")){
                        commentsET.setText(comments.getText());
                        comments.setVisibility(View.GONE);
                        commentsET.setVisibility(View.VISIBLE);
                    }
                    else{
                        comments.setVisibility(View.GONE);
                        commentsET.setVisibility(View.VISIBLE);
                    }
                    if(!shootCB.isChecked()){
                        fpsET.setVisibility(View.GONE);
                        accuracyET.setVisibility(View.GONE);
                        fpsLabel.setVisibility(View.GONE);
                        accuracyLabel.setVisibility(View.GONE);
                    }
                    if(!climbCB.isChecked()){
                        climbTime.setVisibility(View.GONE);
                        climbSpeedLabel.setVisibility(View.GONE);
                    }
                    isScouted.setVisibility(View.VISIBLE);
                    inEdit=true;
                    shootCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                               if (isChecked){
                                   fpsET.setVisibility(View.VISIBLE);
                                   accuracyET.setVisibility(View.VISIBLE);
                                   fpsLabel.setVisibility(View.VISIBLE);
                                   accuracyLabel.setVisibility(View.VISIBLE);
                               }
                               else{
                                   fpsET.setVisibility(View.GONE);
                                   accuracyET.setVisibility(View.GONE);
                                   fpsLabel.setVisibility(View.GONE);
                                   accuracyLabel.setVisibility(View.GONE);
                               }
                           }
                       }
                    );
                    climbCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                           @Override
                           public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                               if (isChecked){
                                   climbTimeET.setVisibility(View.VISIBLE);
                                   climbSpeedLabel.setVisibility(View.VISIBLE);
                               }
                               else{
                                   climbTimeET.setVisibility(View.GONE);
                                   climbSpeedLabel.setVisibility(View.GONE);
                               }
                           }
                       }
                    );
                }
            }
        });
        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            Uri uri = data.getData();
            StorageReference filepath = storageReference.child("teams").child("6027");

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    void showError(){
        Snackbar.make(findViewById(android.R.id.content), "Failed to get data. Please check network.", Snackbar.LENGTH_LONG)
                .show();
    }
    void load(){
        TextView teamTV = (TextView) findViewById(R.id.teamNum);
        teamTV.setText(teamNumber);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        type = (TextView) findViewById(R.id.RobotType);
        typeET = (EditText) findViewById(R.id.typeET);

        driveTrain = (TextView) findViewById(R.id.drivetrain);
        driveTrainET = (EditText) findViewById(R.id.drivetrainET);

        codeType = (TextView) findViewById(R.id.programing);
        codeTypeET = (EditText) findViewById(R.id.programingET);

        speed = (TextView) findViewById(R.id.speed);
        speedET = (EditText) findViewById(R.id.speedET);

        motors = (TextView) findViewById(R.id.motors);
        motorsET = (EditText) findViewById(R.id.motorsET);

        visionCB = (CheckBox) findViewById(R.id.visionCB);

        comments = (TextView) findViewById(R.id.comments);
        commentsET = (EditText) findViewById(R.id.commentsET);

        gearsCB = (CheckBox) findViewById(R.id.gearsCB);

        shootCB = (CheckBox) findViewById(R.id.shootCB);

        fpsLabel = (TextView) findViewById(R.id.shootSpeedLabel);
        fps = (TextView) findViewById(R.id.shootSpeed);
        fpsET = (EditText) findViewById(R.id.shootSpeedET);
        accuracyLabel = (TextView) findViewById(R.id.accuracyLabel);
        accuracy = (TextView) findViewById(R.id.accuracy);
        accuracyET = (EditText) findViewById(R.id.accuracyET);

        climbCB = (CheckBox) findViewById(R.id.climbCB);

        climbSpeedLabel = (TextView) findViewById(R.id.climbSpeedLabel);
        climbTime = (TextView) findViewById(R.id.climbTime);
        climbTimeET = (EditText) findViewById(R.id.climbET);

        defendCB = (CheckBox) findViewById(R.id.defendCB);

        hopper = (TextView) findViewById(R.id.hopper);
        hopperET = (EditText) findViewById(R.id.hopperET);

        mDatabase.child("teams").child(teamNumber).addListenerForSingleValueEvent(
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
                            String robotFPS = robot.getFps();
                            String robotAccuracy = robot.getAccuracy();
                            String robotClimbTime = robot.getClimbTime();
                            String robotHopper = robot.getHopper();
                            Boolean isTeamScouted = robot.getIsScouted();
                            Boolean robotHasVision = robot.getRobotHasVision();
                            Boolean robotCanGear = robot.getGear();
                            Boolean robotCanClimb = robot.getClimb();
                            Boolean robotCanShoot = robot.getShoot();
                            Boolean robotDefense = robot.getDefend();
                            String robotSpeed = robot.getSpeed();
                            teamNumber = dataSnapshot.getKey();
                            int robotNumberOfMotors = robot.getRobotNumberOfMotors();
                            if(isTeamScouted==null||!isTeamScouted){
                                isScouted.setChecked(false);
                            }else{
                                isScouted.setChecked(true);
                            }
                            if(robotType==null || robotType.equals("")){
                                type.setText("Need Data");
                                type.setVisibility(View.VISIBLE);
                                typeET.setVisibility(View.GONE);
                            }else{
                                type.setText(robotType);
                                type.setVisibility(View.VISIBLE);
                                typeET.setVisibility(View.GONE);
                            }
                            if(robotDriveTrain==null || robotDriveTrain.equals("")){
                                driveTrain.setText("Need Data");
                                driveTrain.setVisibility(View.VISIBLE);
                                driveTrainET.setVisibility(View.GONE);
                            }else{
                                driveTrain.setText(robotDriveTrain);
                                driveTrain.setVisibility(View.VISIBLE);
                                driveTrainET.setVisibility(View.GONE);
                            }
                            if(robotProgramingEnvironment==null || robotProgramingEnvironment.equals("")){
                                codeType.setText("Need Data");
                                codeType.setVisibility(View.VISIBLE);
                                codeTypeET.setVisibility(View.GONE);
                            }else{
                                codeType.setText(robotProgramingEnvironment);
                                codeType.setVisibility(View.VISIBLE);
                                codeTypeET.setVisibility(View.GONE);
                            }
                            if(robotComments==null || robotComments.equals("")){
                                comments.setText("Need Data");
                                comments.setVisibility(View.VISIBLE);
                                commentsET.setVisibility(View.GONE);
                            }else{
                                comments.setText(robotComments);
                                comments.setVisibility(View.VISIBLE);
                                commentsET.setVisibility(View.GONE);
                            }
                            if(robotSpeed==null || robotSpeed.equals("")){
                                speed.setText("Need Data");
                                speed.setVisibility(View.VISIBLE);
                                speedET.setVisibility(View.GONE);
                            }else{
                                speed.setText(robotSpeed);
                                speed.setVisibility(View.VISIBLE);
                                speedET.setVisibility(View.GONE);
                            }
                            if(robotNumberOfMotors==0){
                                motors.setText("Need Data");
                                motors.setVisibility(View.VISIBLE);
                                motorsET.setVisibility(View.GONE);
                            }else{
                                motors.setText(String.valueOf(robotNumberOfMotors));
                                motors.setVisibility(View.VISIBLE);
                                motorsET.setVisibility(View.GONE);
                            }
                            if(robotHasVision!=null){
                                visionCB.setEnabled(false);
                                visionCB.setChecked(robotHasVision);
                            }else{
                                visionCB.setVisibility(View.GONE);
                            }
                            if(robotAccuracy==null || robotAccuracy.equals("")){
                                accuracy.setText("Need Data");
                                accuracy.setVisibility(View.VISIBLE);
                                accuracyET.setVisibility(View.GONE);
                            }else{
                                accuracy.setText(robotAccuracy);
                                accuracy.setVisibility(View.VISIBLE);
                                accuracyET.setVisibility(View.GONE);
                            }
                            if(robotClimbTime==null || robotClimbTime.equals("")){
                                climbTime.setText("Need Data");
                                climbTime.setVisibility(View.VISIBLE);
                                climbTimeET.setVisibility(View.GONE);
                            }else{
                                climbTime.setText(robotClimbTime);
                                climbTime.setVisibility(View.VISIBLE);
                                climbTimeET.setVisibility(View.GONE);
                            }
                            if(robotFPS==null || robotFPS.equals("")){
                                fps.setText("Need Data");
                                fps.setVisibility(View.VISIBLE);
                                fpsET.setVisibility(View.GONE);
                            }else{
                                fps.setText(robotFPS);
                                fps.setVisibility(View.VISIBLE);
                                fpsET.setVisibility(View.GONE);
                            }
                            if(robotHopper==null || robotHopper.equals("")){
                                hopper.setText("Need Data");
                                hopper.setVisibility(View.VISIBLE);
                                hopperET.setVisibility(View.GONE);
                            }else{
                                hopper.setText(robotHopper);
                                hopper.setVisibility(View.VISIBLE);
                                hopperET.setVisibility(View.GONE);
                            }
                            if(robotCanClimb!=null){
                                climbCB.setEnabled(false);
                                climbCB.setChecked(robotCanClimb);
                                if(!robotCanClimb){
                                    climbSpeedLabel.setVisibility(View.GONE);
                                    climbTime.setVisibility(View.GONE);
                                }
                            }else{
                                climbCB.setVisibility(View.GONE);
                            }
                            if(robotCanGear!=null){
                                gearsCB.setEnabled(false);
                                gearsCB.setChecked(robotCanGear);
                            }else{
                                gearsCB.setVisibility(View.GONE);
                            }
                            if(robotCanShoot!=null){
                                shootCB.setEnabled(false);
                                shootCB.setChecked(robotCanShoot);
                                if(!robotCanShoot){
                                    fps.setVisibility(View.GONE);
                                    accuracy.setVisibility(View.GONE);
                                    fpsLabel.setVisibility(View.GONE);
                                    accuracyLabel.setVisibility(View.GONE);
                                }
                            }else{
                                shootCB.setVisibility(View.GONE);
                            }
                            if(robotDefense!=null){
                                defendCB.setEnabled(false);
                                defendCB.setChecked(robotDefense);
                            }else{
                                defendCB.setVisibility(View.GONE);
                            }
                        }
                        else{
                            isScouted.setChecked(false);
                            isScouted.setVisibility(View.GONE);
                            type.setText("Need Data");
                            type.setVisibility(View.VISIBLE);
                            typeET.setVisibility(View.GONE);
                            driveTrain.setText("Need Data");
                            driveTrain.setVisibility(View.VISIBLE);
                            driveTrainET.setVisibility(View.GONE);
                            codeType.setText("Need Data");
                            codeType.setVisibility(View.VISIBLE);
                            codeTypeET.setVisibility(View.GONE);
                            comments.setText("Need Data");
                            comments.setVisibility(View.VISIBLE);
                            commentsET.setVisibility(View.GONE);
                            speed.setText("Need Data");
                            speed.setVisibility(View.VISIBLE);
                            speedET.setVisibility(View.GONE);
                            motors.setText("Need Data");
                            motors.setVisibility(View.VISIBLE);
                            motorsET.setVisibility(View.GONE);
                            visionCB.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(findViewById(android.R.id.content), "Check Firebase permissions", Snackbar.LENGTH_LONG)
                                .show();
                    }
                });

        Call<List<TeamEvents>> call = apiService.getEvents("frc" + teamNumber, "2016");
        call.enqueue(new Callback<List<TeamEvents>>() {
            @Override
            public void onResponse(Call<List<TeamEvents>> call, Response<List<TeamEvents>> response) {
                for(int i=0; i<response.body().size(); i++){
                    teamEvents.add(response.body().get(i).getName());
                    eventNames.add(response.body().get(i).getKey());
                    Call<JsonElement> eventData = apiService.getEventStats(eventNames.get(i));
                    eventData.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            JsonObject oprs = (JsonObject) response.body().getAsJsonObject().get("oprs");
                            JsonObject ccwms = (JsonObject) response.body().getAsJsonObject().get("ccwms");
                            JsonObject dprs = (JsonObject) response.body().getAsJsonObject().get("dprs");
                            String callURL = call.request().url().encodedPath();
                            int start = callURL.indexOf("event/")+6;
                            int end = callURL.indexOf("/stats");
                            String callEventKey = callURL.substring(start,end);

                            try {
                                statsLastYear.add(new TeamStat(teamEvents.get(eventNames.indexOf(callEventKey)), oprs.get(teamNumber).toString().substring(0,4),
                                        ccwms.get(teamNumber).toString().substring(0,4), dprs.get(teamNumber).toString().substring(0,4)));
                            }catch (Exception e){
                                statsLastYear.add(new TeamStat(teamEvents.get(eventNames.indexOf(callEventKey)), "NA", "NA", "NA"));
                            }
                            if(!(statsLastYear.size()<teamEventsThis.size())){
                                recyclerView = (RecyclerView) findViewById(R.id.recycler);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                recyclerView.setAdapter(new EventStatsRecycler(statsLastYear));
                                recyclerView.setNestedScrollingEnabled(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            showError();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<TeamEvents>> call, Throwable t) {
                showError();
            }
        });
        Call<List<TeamEvents>> call2017 = apiService.getEvents("frc" + teamNumber, "2017");
        call2017.enqueue(new Callback<List<TeamEvents>>() {
            @Override
            public void onResponse(Call<List<TeamEvents>> call, Response<List<TeamEvents>> response) {
                for(int i=0; i<response.body().size(); i++){
                    teamEventsThis.add(response.body().get(i).getName());
                    eventNamesThis.add(response.body().get(i).getKey());
                    Call<JsonElement> eventDataThis = apiService.getEventStats(eventNamesThis.get(i));
                    eventDataThis.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            JsonObject oprs = (JsonObject) response.body().getAsJsonObject().get("oprs");
                            JsonObject ccwms = (JsonObject) response.body().getAsJsonObject().get("ccwms");
                            JsonObject dprs = (JsonObject) response.body().getAsJsonObject().get("dprs");
                            String callURL = call.request().url().encodedPath();
                            int start = callURL.indexOf("event/")+6;
                            int end = callURL.indexOf("/stats");
                            String callEventKey = callURL.substring(start,end);
                            try {
                                statsThisYear.add(new TeamStat(teamEventsThis.get(eventNamesThis.indexOf(callEventKey)), oprs.get(teamNumber).toString().substring(0,4),
                                        ccwms.get(teamNumber).toString().substring(0,4), dprs.get(teamNumber).toString().substring(0,4)));
                            }catch (Exception e){
                                statsThisYear.add(new TeamStat(teamEventsThis.get(eventNamesThis.indexOf(callEventKey)), "NA", "NA", "NA"));
                            }
                            if(!(statsThisYear.size()<teamEventsThis.size())){
                                recyclerViewThis = (RecyclerView) findViewById(R.id.recyclerThisYear);
                                recyclerViewThis.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                recyclerViewThis.setAdapter(new EventStatsRecycler(statsThisYear));
                                recyclerViewThis.setNestedScrollingEnabled(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            showError();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<TeamEvents>> call, Throwable t) {
                showError();
            }
        });
        Call<TeamNoRobot> teamCall = apiService.getTeam("frc"+teamNumber);
        teamCall.enqueue(new Callback<TeamNoRobot>() {
            @Override
            public void onResponse(Call<TeamNoRobot> call, Response<TeamNoRobot> response) {
                teamRegion = response.body().getRegion();
                website = response.body().getWebsite();
                location = response.body().getLocation();
                motto = response.body().getMotto();
                nickname = response.body().getNickname();
                rookieYear = response.body().getRookieYear();
                TextView nickTV = (TextView) findViewById(R.id.teamNickname);
                TextView locationTV = (TextView) findViewById(R.id.location);
                TextView rookieTV = (TextView) findViewById(R.id.rookie);
                nickTV.setText(nickname);
                locationTV.setText(location);
                rookieTV.setText(String.valueOf(rookieYear));
                Button btn = (Button) findViewById(R.id.btn_website);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
                        startActivity(browserIntent);
                    }
                });
            }

            @Override
            public void onFailure(Call<TeamNoRobot> call, Throwable t) {
                showError();
            }
        });
    }
}
