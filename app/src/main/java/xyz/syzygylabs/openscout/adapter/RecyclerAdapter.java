package xyz.syzygylabs.openscout.adapter;

/**
 * Created by Charlie on 12/12/2016.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.objects.Team;

/**
 * Created by Charlie on 8/23/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<Team> teams;
    ViewGroup parents;
    public RecyclerAdapter(ArrayList<Team>teams) {
        this.teams = teams;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parents = parent;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.teamNumberTV.setText("Team " + teams.get(position).getTeamNumber());
        holder.teamNicknameTV.setText("'" + teams.get(position).getTeamNickname() + "'");
        holder.teamLocationTV.setText(teams.get(position).getTeamLocation());

        if(!teams.get(position).getRobot().getRobotDrivetrain().equals("NA")){
            holder.teamDriveTrainTypeTV.setVisibility(View.GONE);
        }
        else{
            holder.teamDriveTrainTypeTV.setVisibility(View.VISIBLE);
        }
        if(!teams.get(position).getRobot().getRobotProgramingEnvironment().equals("NA")){
            holder.teamProgramingTV.setVisibility(View.GONE);
        }else{
            holder.teamProgramingTV.setVisibility(View.VISIBLE);
        }
        if(!teams.get(position).getRobot().getSpeed().equals("NA")){
            holder.teamSpeedTV.setVisibility(View.GONE);
        }else{
            holder.teamSpeedTV.setVisibility(View.VISIBLE);
        }
        if(!teams.get(position).getRobot().getRobotType().equals("NA")){
            holder.teamRobotTypeTV.setVisibility(View.GONE);
        }else{
            holder.teamRobotTypeTV.setVisibility(View.VISIBLE);
        }
        if(teams.get(position).getRobot().getRobotNumberOfMotors()!=0){
            holder.teamRobotMotorsTV.setVisibility(View.GONE);
        }else{
            holder.teamRobotMotorsTV.setVisibility(View.VISIBLE);
        }
        if(!teams.get(position).getRobot().getIsScouted()){
            holder.teamNumberTV.setTextColor(Color.RED);
            holder.teamLabelTV.setText("Data Still Needed:");
        }
        else{
            holder.teamNumberTV.setTextColor(Color.BLACK);
            holder.teamLabelTV.setText("Team is Scouted.");
        }


    }
    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamNumberTV;
        TextView teamNicknameTV;
        TextView teamRobotTypeTV;
        TextView teamLocationTV;
        TextView teamDriveTrainTypeTV;
        TextView teamVisionTV;
        TextView teamProgramingTV;
        TextView teamSpeedTV;
        TextView teamLabelTV;
        TextView teamRobotMotorsTV;
        public ViewHolder(View itemView) {
            super(itemView);
            teamNumberTV = (TextView) itemView.findViewById(R.id.teamNumber);
            teamNicknameTV = (TextView) itemView.findViewById(R.id.teamNickname);
            teamRobotTypeTV = (TextView) itemView.findViewById(R.id.teamRobotType);
            teamLocationTV = (TextView) itemView.findViewById(R.id.teamLocation);
            teamDriveTrainTypeTV = (TextView) itemView.findViewById(R.id.teamRobotDriveTrainType);
            teamVisionTV = (TextView) itemView.findViewById(R.id.teamRobotVision);
            teamSpeedTV = (TextView) itemView.findViewById(R.id.teamRobotSpeed);
            teamProgramingTV = (TextView) itemView.findViewById(R.id.teamRobotPrograming);
            teamLabelTV = (TextView) itemView.findViewById(R.id.neededLabel);
            teamRobotMotorsTV = (TextView) itemView.findViewById(R.id.teamRobotMotors);

        }
    }
}