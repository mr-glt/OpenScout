package xyz.syzygylabs.openscout.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.adapter.MatchRecycler;
import xyz.syzygylabs.openscout.api.ApiClient;
import xyz.syzygylabs.openscout.api.ApiInterface;
import xyz.syzygylabs.openscout.helpers.RecyclerItemClickListener;
import xyz.syzygylabs.openscout.objects.match.Match;

public class DriveTeam extends AppCompatActivity {
    String eventName;
    RecyclerView recyclerView;
    String teamNumber;
    String eventKey;
    SharedPreferences prefs;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_team);
        Intent intent = getIntent();
        eventName = intent.getStringExtra("eventName");
        eventKey = intent.getStringExtra("event");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        teamNumber = prefs.getString("teamNumber", "1");
        Boolean showWelcome = intent.getBooleanExtra("showWelcome", false);
        TextView title = (TextView) findViewById(R.id.eventTitle);
        if (showWelcome){
            title.setText("Welcome to the " + eventName);
        }
        else{
            title.setVisibility(View.GONE);
        }
        if(!eventName.equals("")){
            getSupportActionBar().setTitle(eventName);
        }
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<List<Match>> call = apiService.getMatches("frc" + teamNumber,"2016" + (eventKey.substring(4)));
        //TODO update for 2017
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, final Response<List<Match>> response) {
                if(response==null){
                    Snackbar.make(findViewById(android.R.id.content), "Data is incomplete for the " + eventName, Snackbar.LENGTH_LONG)
                            .show();
                }else{
                    recyclerView = (RecyclerView) findViewById(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    recyclerView.setAdapter(new MatchRecycler(response.body()));
                    RecyclerItemClickListener rvListener = new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            Intent myIntent = new Intent(getBaseContext(), MatchPage.class);
                            myIntent.putExtra("key", response.body().get(position).getKey());
                            getBaseContext().startActivity(myIntent);
                        }
                    });
                    recyclerView.addOnItemTouchListener(rvListener);
                }

                
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {

            }
        });
        Call<JsonElement> eventData = apiService.getEventStats("2016" + (eventKey.substring(4)));
        eventData.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    JsonObject oprs_ = (JsonObject) response.body().getAsJsonObject().get("oprs");
                    JsonObject ccwms_ = (JsonObject) response.body().getAsJsonObject().get("ccwms");
                    JsonObject dprs_ = (JsonObject) response.body().getAsJsonObject().get("dprs");

                    TextView oprs = (TextView) findViewById(R.id.oprs);
                    TextView ccwms = (TextView) findViewById(R.id.ccwms);
                    TextView dprs = (TextView) findViewById(R.id.dprs);
                    if(!oprs_.get(teamNumber).toString().substring(0,4).equals("")){
                        oprs.setText("OPRS: " + oprs_.get(teamNumber).toString().substring(0,4));
                    }
                    if(!ccwms_.get(teamNumber).toString().substring(0,4).equals("")){
                        ccwms.setText("CCWMS: " + ccwms_.get(teamNumber).toString().substring(0,4));
                    }
                    if(!dprs_.get(teamNumber).toString().substring(0,4).equals("")){
                        dprs.setText("DPRS: " + dprs_.get(teamNumber).toString().substring(0,4));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(findViewById(android.R.id.content), "Data is unavailable for the " + eventName, Snackbar.LENGTH_LONG)
                            .show();
                }
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

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
}
