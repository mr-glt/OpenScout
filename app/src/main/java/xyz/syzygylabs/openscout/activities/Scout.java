package xyz.syzygylabs.openscout.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.adapter.RecyclerAdapter;
import xyz.syzygylabs.openscout.api.ApiClient;
import xyz.syzygylabs.openscout.api.ApiInterface;
import xyz.syzygylabs.openscout.helpers.RecyclerItemClickListener;
import xyz.syzygylabs.openscout.objects.Robot;
import xyz.syzygylabs.openscout.objects.Team;
import xyz.syzygylabs.openscout.objects.TeamNoRobot;

public class Scout extends AppCompatActivity {

    ArrayList<Team> teams = new ArrayList<Team>();

    DatabaseReference mDatabase;
    RecyclerView recyclerView;
    ProgressWheel bar;

    int j = 0;
    String teamNumber, id, eventName;

    PullRefreshLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout);

        bar = (ProgressWheel) findViewById(R.id.progress_wheel);
        bar.setVisibility(View.VISIBLE);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView title = (TextView) findViewById(R.id.eventTitle);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        Intent intent = getIntent();
        id = intent.getStringExtra("event");
        eventName = intent.getStringExtra("eventName");
        Boolean showWelcome = intent.getBooleanExtra("showWelcome", false);

        if (showWelcome){
            title.setText("Welcome to the " + eventName);
        }
        else{
            title.setVisibility(View.GONE);
        }
        if(!eventName.equals("")){
            getSupportActionBar().setTitle(eventName);
        }

        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                teams.clear();
                getTeams();
            }
        });
        getTeams();
    }
    private void getTeams(){
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<TeamNoRobot>> callTeams = apiService.getTeams(id);
        callTeams.enqueue(new Callback<List<TeamNoRobot>>() {
            @Override
            public void onResponse(Call<List<TeamNoRobot>> call, Response<List<TeamNoRobot>> gotResponse) {
                get(gotResponse);

            }
            @Override
            public void onFailure(Call<List<TeamNoRobot>> call, Throwable t) {

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

    void get(final Response<List<TeamNoRobot>> response){

        bar.setInstantProgress((float)((double)j/(double)response.body().size()));

        mDatabase.child("teams").child(String.valueOf(response.body().get(j).getTeamNumber())).addListenerForSingleValueEvent(
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

                            teams.add(new Team(response.body().get(j).getNickname(), response.body().get(j).getName(),
                                    response.body().get(j).getTeamNumber()+"", response.body().get(j).getLocation(),
                                    response.body().get(j).getLocality(),response.body().get(j).getRegion(),
                                    response.body().get(j).getCountryName(),response.body().get(j).getKey(),
                                    response.body().get(j).getMotto(),response.body().get(j).getWebsite(),
                                    response.body().get(j).getRookieYear()+"", new Robot((robotName != null) ? robotName : "NA", (robotType != null) ? robotType : "NA", (robotDriveTrain != null) ? robotDriveTrain : "NA",
                                    (robotNumberOfMotors != 0) ? robotNumberOfMotors : 0, (robotComments != null) ? robotComments : "NA", (robotHasVision != null) ? robotHasVision : false,
                                    (robotProgramingEnvironment != null) ? robotProgramingEnvironment : "NA", (isTeamScouted != null) ? isTeamScouted : false, (robotSpeed != null) ? robotSpeed : "NA",
                                    (robotFPS != null) ? robotFPS : "NA", (robotAccuracy != null) ? robotAccuracy : "NA",(robotClimbTime != null) ? robotClimbTime : "NA",
                                    (robotHopper != null) ? robotHopper : "NA", (robotCanGear != null) ? robotCanGear : false, (robotCanShoot != null) ? robotCanShoot : false,
                                    (robotCanClimb != null) ? robotCanClimb : false,(robotDefense != null) ? robotDefense : false)));
                        }
                        else{
                            teams.add(new Team(response.body().get(j).getNickname(), response.body().get(j).getName(),
                                    response.body().get(j).getTeamNumber()+"", response.body().get(j).getLocation(),
                                    response.body().get(j).getLocality(),response.body().get(j).getRegion(),
                                    response.body().get(j).getCountryName(),response.body().get(j).getKey(),
                                    response.body().get(j).getMotto(),response.body().get(j).getWebsite(),
                                    response.body().get(j).getRookieYear()+"", new Robot("NA", "NA", "NA", 0, "NA", false, "NA", false, "NA", "NA", "NA", "NA", "NA", false,false,false,false)));
                        }

                        if (j+1>=response.body().size()){
                            j=0;
                            Collections.sort(teams, new Comparator<Team>() {
                                @Override
                                public int compare(Team teams, Team t1) {
                                    return Integer.valueOf(Integer.parseInt(teams.getTeamNumber())).compareTo(Integer.parseInt(t1.getTeamNumber()));
                                }
                            });
                            Collections.sort(teams, new Comparator<Team>() {
                                @Override
                                public int compare(Team teams, Team t1) {
                                    return Boolean.compare(teams.getRobot().getIsScouted(),t1.getRobot().getIsScouted());

                                }
                            });
                            bar.setVisibility(View.GONE);
                            recyclerView.setAdapter(new RecyclerAdapter(teams));
                            RecyclerItemClickListener rvListener = new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    Intent myIntent = new Intent(getBaseContext(), TeamPage.class);
                                    myIntent.putExtra("teamNum", teams.get(position).getTeamNumber());
                                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getBaseContext().startActivity(myIntent);
                                }
                            });
                            recyclerView.addOnItemTouchListener(rvListener);
                            layout.setRefreshing(false);
                        }else{
                            j++;
                            get(response);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(findViewById(android.R.id.content), "Check Firebase permissions", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }
        );
    }
}
