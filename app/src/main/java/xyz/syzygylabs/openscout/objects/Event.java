package xyz.syzygylabs.openscout.objects;

/**
 * Created by Charlie on 12/21/2016.
 */

public class Event {
    private String name;
    private String keys;
    private String start_date;
    private String end_date;
    private String location;
    private String week;

    public Event(String eventName, String keys, String start_date, String end_date, String location, String week){
        this.name = eventName;
        this.keys = keys;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
        this.week = week;
    }
    public String getEventName(){
        return name;
    }
    public String getEventKeys(){
        return keys;
    }
    public String getEventStartDate(){
        return start_date;
    }
    public String getEventEndDate(){
        return end_date;
    }
    public String getEventLocation(){
        return location;
    }
    public String getEventWeek(){
        return week;
    }


}
