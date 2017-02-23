package xyz.syzygylabs.openscout.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.objects.match.Match;

/**
 * Created by Charlie on 8/23/2016.
 */
public class MatchRecycler extends RecyclerView.Adapter<MatchRecycler.ViewHolder> {
    List redTeams;
    List blueTeams;
    List<Match> matches;
    ViewGroup parents;
    public MatchRecycler(List<Match> matches) {
        this.matches = matches;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parents = parent;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.match_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.matchName.setText("#" + matches.get(position).getMatchNumber());

        if (matches.get(position).getCompLevel().equals("qf")){
            holder.matchLevel.setText("Quarter Finals");
        }
        if (matches.get(position).getCompLevel().equals("qm")){
            holder.matchLevel.setText("Qualification");
        }
        if (matches.get(position).getCompLevel().equals("sm")){
            holder.matchLevel.setText("Semi Finals");
        }
        if (matches.get(position).getCompLevel().equals("f")){
            holder.matchLevel.setText("Finals");
        }
        redTeams = matches.get(position).getAlliances().getRed().getTeams();
        holder.red1.setText(redTeams.get(0).toString().substring(3));
        holder.red2.setText(redTeams.get(1).toString().substring(3));
        holder.red3.setText(redTeams.get(2).toString().substring(3));
        if (redTeams.size()>3){
            holder.red4.setText(redTeams.get(3).toString().substring(3));
        }
        blueTeams = matches.get(position).getAlliances().getBlue().getTeams();
        holder.blue1.setText(blueTeams.get(0).toString().substring(3));
        holder.blue2.setText(blueTeams.get(1).toString().substring(3));
        holder.blue3.setText(blueTeams.get(2).toString().substring(3));
        if (blueTeams.size()>3){
            holder.blue4.setText(blueTeams.get(3).toString().substring(3));
        }
        try {
            if(matches.get(position).getScoreBreakdown().getBlue().getTotalPoints() != 0){
                holder.blueScore.setText(matches.get(position).getScoreBreakdown().getBlue().getTotalPoints()+"");
                holder.blueScore.setVisibility(View.VISIBLE);

            }
            if(matches.get(position).getScoreBreakdown().getRed().getTotalPoints() != 0){
                holder.redScore.setText(matches.get(position).getScoreBreakdown().getRed().getTotalPoints()+"");
                holder.redScore.setVisibility(View.VISIBLE);

            }
            int blueScore = matches.get(position).getScoreBreakdown().getBlue().getTotalPoints();
            int redScore = matches.get(position).getScoreBreakdown().getRed().getTotalPoints();
            if(blueScore>redScore){
                holder.blueScore.setTypeface(Typeface.DEFAULT_BOLD);
                holder.redScore.setTypeface(Typeface.DEFAULT);
            }
            if(redScore>blueScore){
                holder.redScore.setTypeface(Typeface.DEFAULT_BOLD);
                holder.blueScore.setTypeface(Typeface.DEFAULT);
            }
            if(redScore==blueScore){
                holder.blueScore.setTypeface(Typeface.DEFAULT);
                holder.redScore.setTypeface(Typeface.DEFAULT);
            }
        }catch (Exception ignored){

        }
    }

    public void remove(int position) {

    }

    public void add(int position) {

    }
    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        TextView blueScore;
        TextView redScore;
        public ViewHolder(View itemView) {
            super(itemView);
            matchName = (TextView) itemView.findViewById(R.id.matchName);
            matchLevel = (TextView) itemView.findViewById(R.id.matchLevel);
            red1 = (TextView) itemView.findViewById(R.id.red1);
            red2 = (TextView) itemView.findViewById(R.id.red2);
            red3 = (TextView) itemView.findViewById(R.id.red3);
            red4 = (TextView) itemView.findViewById(R.id.red4);
            blue1 = (TextView) itemView.findViewById(R.id.blue1);
            blue2 = (TextView) itemView.findViewById(R.id.blue2);
            blue3 = (TextView) itemView.findViewById(R.id.blue3);
            blue4 = (TextView) itemView.findViewById(R.id.blue4);
            blueScore = (TextView) itemView.findViewById(R.id.blueScore);
            redScore = (TextView) itemView.findViewById(R.id.redScore);



        }
    }
}