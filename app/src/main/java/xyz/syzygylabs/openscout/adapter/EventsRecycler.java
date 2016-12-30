package xyz.syzygylabs.openscout.adapter;

/**
 * Created by Charlie on 12/12/2016.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.objects.Event;

/**
 * Created by Charlie on 8/23/2016.
 */
public class EventsRecycler extends RecyclerView.Adapter<EventsRecycler.ViewHolder> {
    ArrayList<String> eventNames;
    ArrayList<String> eventDate;
    ArrayList<String> eventLocation;
    ArrayList<String> eventWeek;
    ArrayList<Event> events;
    ViewGroup parents;
    public EventsRecycler(ArrayList<Event> events) {
        this.events=events;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parents = parent;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.eventNameTV.setText(events.get(position).getEventName());
        holder.eventDateTV.setText(events.get(position).getEventStartDate());
        holder.eventLocationTV.setText(events.get(position).getEventLocation());
        if(events.get(position).getEventWeek().equals("0")){
            holder.eventWeekTV.setText("Week: 0/NA");
        }else{
            holder.eventWeekTV.setText("Week "+events.get(position).getEventWeek());
        }

    }
    public void remove(int position) {

    }

    public void add(int position) {

    }
    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameTV;
        TextView eventDateTV;
        TextView eventLocationTV;
        TextView eventWeekTV;
        public ViewHolder(View itemView) {
            super(itemView);
            eventNameTV = (TextView) itemView.findViewById(R.id.event);
            eventDateTV = (TextView) itemView.findViewById(R.id.eventDate);
            eventLocationTV = (TextView) itemView.findViewById(R.id.eventLocation);
            eventWeekTV = (TextView) itemView.findViewById(R.id.eventWeek);

        }
    }
}