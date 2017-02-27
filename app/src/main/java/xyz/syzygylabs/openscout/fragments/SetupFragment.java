package xyz.syzygylabs.openscout.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.adapter.EventsRecycler;
import xyz.syzygylabs.openscout.api.ApiClient;
import xyz.syzygylabs.openscout.api.ApiInterface;
import xyz.syzygylabs.openscout.objects.Event;
import xyz.syzygylabs.openscout.objects.teamevents.TeamEvents;

/**
 * Created by Charlie on 12/19/2016.
 */

public class SetupFragment extends SlideFragment {
    private EditText teamNumber;
    private RecyclerView recyclerView;
    private Button save;
    Boolean forward = false;
    View root;
    TextView teamSetup;
    TextView teamUp;
    SharedPreferences prefs;
    ProgressBar pv;
    ArrayList<Event> events = new ArrayList<Event>();
    public SetupFragment() {
        // Required empty public constructor
    }

    public static SetupFragment newInstance() {
        return new SetupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_intro_setup, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        teamSetup = (TextView) root.findViewById(R.id.teamSetup);
        teamUp = (TextView) root.findViewById(R.id.upEvents);
        teamNumber = (EditText) root.findViewById(R.id.teamnumber);
        save = (Button) root.findViewById(R.id.save);
        pv = (ProgressBar) root.findViewById(R.id.pv);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!teamNumber.getText().toString().equals("")){
                    pv.setVisibility(View.VISIBLE);

                    final ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<List<TeamEvents>> fetchEvents = apiService.getEvents("frc" + teamNumber.getText().toString(), "2017");
                    fetchEvents.enqueue(new Callback<List<TeamEvents>>() {
                        @Override
                        public void onResponse(Call<List<TeamEvents>> call, Response<List<TeamEvents>> response) {
                            for(int i=0; i<response.body().size(); i++){
                                pv.setProgress(i/response.body().size() * 100);
                                events.add(new Event(response.body().get(i).getName(), response.body().get(i).getKey(),
                                        response.body().get(i).getStartDate(),response.body().get(i).getEndDate(),
                                        response.body().get(i).getLocation(),response.body().get(i).getWeek()+""));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setAdapter(new EventsRecycler(events));
                            pv.setVisibility(View.GONE);
                            forward = true;
                        }

                        @Override
                        public void onFailure(Call<List<TeamEvents>> call, Throwable t) {

                        }
                    });
                    save.setVisibility(View.GONE);
                    teamNumber.setVisibility(View.GONE);
                    teamSetup.setVisibility(View.GONE);
                    teamUp.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("teamNumber", teamNumber.getText().toString());
                    editor.putBoolean("showIntro", false);
                    editor.apply();
                }
                else{
                    Toast.makeText(getContext(), "Make sure all fields are filled.", Toast.LENGTH_LONG).show();
                }

                //loginHandler.postDelayed(loginRunnable, 2000);
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        //loginHandler.removeCallbacks(loginRunnable);
        super.onDestroy();
    }

    @Override
    public boolean canGoForward() {
        return forward;
    }
}
