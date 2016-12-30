package xyz.syzygylabs.openscout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.objects.TeamStat;

/**
 * Created by Charlie on 8/23/2016.
 */
public class EventStatsRecycler extends RecyclerView.Adapter<EventStatsRecycler.ViewHolder> {
    ArrayList<TeamStat> teamStats;
    TextView teamRobotTypeTV;
    ViewGroup parents;
    public EventStatsRecycler(ArrayList<TeamStat> teamStats) {
        this.teamStats = teamStats;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parents = parent;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_stats_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.eventNamesTV.setText(teamStats.get(position).getEvents());
        holder.eventDPRSTV.setText("DPRS: "+ teamStats.get(position).getDprs());
        holder.eventCCWMSTV.setText("CCWMS: "+ teamStats.get(position).getCcwms());
        holder.eventOPRSTV.setText("OPRS: "+ teamStats.get(position).getOprs());

    }

    public void remove(int position) {

    }

    public void add(int position) {

    }
    @Override
    public int getItemCount() {
        return teamStats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventDPRSTV;
        TextView eventCCWMSTV;
        TextView eventOPRSTV;
        TextView eventNamesTV;

        public ViewHolder(View itemView) {
            super(itemView);
            eventOPRSTV = (TextView) itemView.findViewById(R.id.oprs);
            eventCCWMSTV = (TextView) itemView.findViewById(R.id.ccwms);
            eventDPRSTV = (TextView) itemView.findViewById(R.id.dprs);
            eventNamesTV = (TextView) itemView.findViewById(R.id.event);

        }
    }
}