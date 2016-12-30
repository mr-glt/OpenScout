package xyz.syzygylabs.openscout.adapter;

/**
 * Created by Charlie on 12/12/2016.
 */

import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.objects.MatchTeamInfo;

/**
 * Created by Charlie on 8/23/2016.
 */
public class MatchPageRecycler extends RecyclerView.Adapter<MatchPageRecycler.ViewHolder> {
    ArrayList<MatchTeamInfo> matchTeamInfos;
    boolean isOpen;
    ViewGroup parents;
    public MatchPageRecycler(ArrayList<MatchTeamInfo>matchTeamInfos) {
        this.matchTeamInfos = matchTeamInfos;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parents = parent;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.match_team_info_child, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.teamNumberTV.setText("Team " + matchTeamInfos.get(position).getTeam().getTeamNumber());
        holder.eventDPRSTV.setText("DPRS: "+ matchTeamInfos.get(position).getStat().getDprs());
        holder.eventCCWMSTV.setText("CCWMS: "+ matchTeamInfos.get(position).getStat().getCcwms());
        holder.eventOPRSTV.setText("OPRS: "+ matchTeamInfos.get(position).getStat().getOprs());
        holder.teamNicknameTV.setText("'" + matchTeamInfos.get(position).getTeam().getNickname() + "'");
        holder.teamLocationTV.setText(matchTeamInfos.get(position).getTeam().getLocation());
        if(matchTeamInfos.get(position).getRobot().getRobotType()==null){
            holder.type.setText("Need Data");
        }else{
            holder.type.setText(matchTeamInfos.get(position).getRobot().getRobotType());
        }
        if(matchTeamInfos.get(position).getRobot().getRobotDrivetrain()==null){
            holder.driveTrain.setText("Need Data");
        }else{
            holder.driveTrain.setText(matchTeamInfos.get(position).getRobot().getRobotDrivetrain());
        }
        if(matchTeamInfos.get(position).getRobot().getRobotProgramingEnvironment()==null){
            holder.codeType.setText("Need Data");
        }else{
            holder.codeType.setText(matchTeamInfos.get(position).getRobot().getRobotProgramingEnvironment());
        }
        if(matchTeamInfos.get(position).getRobot().getRobotComments()==null){
            holder.comments.setText("Need Data");
        }else{
            holder.comments.setText(matchTeamInfos.get(position).getRobot().getRobotComments());
        }
        if(matchTeamInfos.get(position).getRobot().getSpeed()==null){
            holder.speed.setText("Need Data");
        }else{
            holder.speed.setText(matchTeamInfos.get(position).getRobot().getSpeed());
        }
        if(matchTeamInfos.get(position).getRobot().getRobotNumberOfMotors()==0){
            holder.motors.setText("Need Data");
        }else{
            holder.motors.setText(String.valueOf(matchTeamInfos.get(position).getRobot().getRobotNumberOfMotors()));
        }
        if(matchTeamInfos.get(position).getRobot().getRobotHasVision()==null){
            holder.vision.setText("Need Data");
        }else{
            holder.vision.setText(matchTeamInfos.get(position).getRobot().getRobotHasVision()+"");
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(holder.cardView);
                if (isOpen){
                    holder.layout.setVisibility(View.GONE);
                    holder.moreInfoTV.setText("Show More Information");
                    isOpen=false;
                }else{
                    holder.layout.setVisibility(View.VISIBLE);
                    holder.moreInfoTV.setText("Show Less Information");
                    isOpen=true;
                }

            }
        });
    }
    public void remove(int position) {

    }

    public void add(int position) {

    }
    @Override
    public int getItemCount() {
        return matchTeamInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView moreInfoTV;
        CardView cardView;
        TextView eventDPRSTV;
        TextView eventCCWMSTV;
        TextView eventOPRSTV;
        TextView eventNamesTV;
        TextView type;
        TextView driveTrain;
        TextView codeType;
        TextView speed;
        TextView motors;
        TextView vision;
        TextView comments;
        TextView teamNumberTV;
        TextView teamNicknameTV;
        TextView teamLocationTV;
        public ViewHolder(View itemView) {
            super(itemView);
            teamNumberTV = (TextView) itemView.findViewById(R.id.teamNumber);
            layout = (LinearLayout) itemView.findViewById(R.id.layout2);
            moreInfoTV = (TextView) itemView.findViewById(R.id.moreInfo);
            cardView = (CardView) itemView.findViewById(R.id.card);
            eventOPRSTV = (TextView) itemView.findViewById(R.id.oprs);
            eventCCWMSTV = (TextView) itemView.findViewById(R.id.ccwms);
            eventDPRSTV = (TextView) itemView.findViewById(R.id.dprs);
            eventNamesTV = (TextView) itemView.findViewById(R.id.event);
            type = (TextView) itemView.findViewById(R.id.RobotType);
            driveTrain = (TextView) itemView.findViewById(R.id.drivetrain);
            codeType = (TextView) itemView.findViewById(R.id.programing);
            speed = (TextView) itemView.findViewById(R.id.speed);
            motors = (TextView) itemView.findViewById(R.id.motors);
            vision = (TextView) itemView.findViewById(R.id.vision);
            comments = (TextView) itemView.findViewById(R.id.comments);
            teamNicknameTV = (TextView) itemView.findViewById(R.id.teamNickname);
            teamLocationTV = (TextView) itemView.findViewById(R.id.teamLocation);
        }
    }
}