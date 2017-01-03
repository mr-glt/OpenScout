package xyz.syzygylabs.openscout.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.baoyz.widget.PullRefreshLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.adapter.EventsRecycler;
import xyz.syzygylabs.openscout.api.ApiClient;
import xyz.syzygylabs.openscout.api.ApiInterface;
import xyz.syzygylabs.openscout.helpers.RecyclerItemClickListener;
import xyz.syzygylabs.openscout.objects.Event;
import xyz.syzygylabs.openscout.objects.teamevents.TeamEvents;

public class MainActivity extends AppCompatActivity {
    //Lists
    private ArrayList<Boolean> todayEvent = new ArrayList<Boolean>();
    private ArrayList<Event> events = new ArrayList<Event>();

    //Objects
    private FirebaseAnalytics mFirebaseAnalytics;
    private SharedPreferences prefs;
    private RecyclerView recyclerView;
    PullRefreshLayout layout;
    //Strings
    private String teamNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intro
        Intent introIntent = new Intent(MainActivity.this, Intro.class);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showIntro = prefs.getBoolean("showIntro", true);
        teamNumber = prefs.getString("teamNumber", "1");
        if(showIntro){
            startActivity(introIntent);
        }
        layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                todayEvent.clear();
                events.clear();
                load();
            }
        });


        load(); //Load
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
    public void onResume(){
        super.onResume();
    }
    private void load(){
        teamNumber = prefs.getString("teamNumber", "1");
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<TeamEvents>> fetchEvents = apiService.getEvents("frc" + teamNumber, "2017");
        fetchEvents.enqueue(new Callback<List<TeamEvents>>() {
            @Override
            public void onResponse(Call<List<TeamEvents>> call, Response<List<TeamEvents>> response) {
                ProgressWheel wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
                wheel.setVisibility(View.GONE);
                for(int i=0; i<response.body().size(); i++){
                    String dtStart = response.body().get(i).getStartDate();
                    String dtEnd = response.body().get(i).getEndDate();
                    String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    //String todayDate = "2017-03-30"; //Test date
                    events.add(new Event(response.body().get(i).getName(), response.body().get(i).getKey(),
                            response.body().get(i).getStartDate(),response.body().get(i).getEndDate() ,
                            response.body().get(i).getLocation(),response.body().get(i).getWeek()+""));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startDate = format.parse(dtStart);
                        Date endDate = format.parse(dtEnd);
                        Date today = format.parse(todayDate);
                        todayEvent.add(startDate.compareTo(today) * today.compareTo(endDate) > 0);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                for(int i=0;i<todayEvent.size();i++){
                    if(todayEvent.get(i)){
                        String userType = prefs.getString("example_list", "scout");
                        if(userType.equals("scout") || userType.equals("team")){
                            Intent myIntent = new Intent(getBaseContext(), Scout.class);
                            myIntent.putExtra("event", events.get(i).getEventKeys());
                            myIntent.putExtra("eventName", events.get(i).getEventName());
                            myIntent.putExtra("showWelcome", false);
                            getBaseContext().startActivity(myIntent);
                        }
                        if(userType.equals("driver")){
                            Intent myIntent = new Intent(getBaseContext(), DriveTeam.class);
                            myIntent.putExtra("event", events.get(i).getEventKeys());
                            myIntent.putExtra("eventName", events.get(i).getEventName());
                            myIntent.putExtra("showWelcome", true);
                            getBaseContext().startActivity(myIntent);
                        }

                    }
                }

                recyclerView = (RecyclerView) findViewById(R.id.recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView.setAdapter(new EventsRecycler(events));
                RecyclerItemClickListener rvListener = new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String userType = prefs.getString("example_list", "scout");
                        if(userType.equals("scout") || userType.equals("team")){
                            Intent myIntent = new Intent(getBaseContext(), Scout.class);
                            myIntent.putExtra("event", events.get(position).getEventKeys());
                            myIntent.putExtra("eventName", events.get(position).getEventName());
                            myIntent.putExtra("showWelcome", false);
                            getBaseContext().startActivity(myIntent);
                        }
                        if(userType.equals("driver")){
                            Intent myIntent = new Intent(getBaseContext(), DriveTeam.class);
                            myIntent.putExtra("event", events.get(position).getEventKeys());
                            myIntent.putExtra("eventName", events.get(position).getEventName());
                            myIntent.putExtra("showWelcome", false);
                            getBaseContext().startActivity(myIntent);
                        }
                    }
                });
                recyclerView.addOnItemTouchListener(rvListener);
                layout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<TeamEvents>> call, Throwable t) {

            }
        });
    }

}
