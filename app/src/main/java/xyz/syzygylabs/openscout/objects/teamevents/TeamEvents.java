package xyz.syzygylabs.openscout.objects.teamevents;

import java.util.List;

/**
 * Created by Charlie on 12/23/2016.
 */

public class TeamEvents {
    private String key;
    private String website;
    private boolean official;
    private String end_date;
    private String name;
    private String short_name;
    private Object facebook_eid;
    private Object event_district_string;
    private String venue_address;
    private int event_district;
    private int week;
    private String location;
    private String event_code;
    private int year;
    private List<Webcast> webcast = null;
    private String timezone;
    private List<Alliance> alliances = null;
    private String event_type_string;
    private String start_date;
    private int event_type;

    public String getKey() {
        return key;
    }

    public String getWebsite() {
        return website;
    }

    public boolean isOfficial() {
        return official;
    }

    public String getEndDate() {
        return end_date;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return short_name;
    }


    public Object getFacebookEid() {
        return facebook_eid;
    }

    public Object getEventDistrictString() {
        return event_district_string;
    }


    public String getVenueAddress() {
        return venue_address;
    }

    public int getEventDistrict() {
        return event_district;
    }

    public int getWeek() {
        return week;
    }


    public String getLocation() {
        return location;
    }


    public String getEventCode() {
        return event_code;
    }

    public int getYear() {
        return year;
    }

    public List<Webcast> getWebcast() {
        return webcast;
    }

    public String getTimezone() {
        return timezone;
    }

    public List<Alliance> getAlliances() {
        return alliances;
    }

    public String getEventTypeString() {
        return event_type_string;
    }

    public String getStartDate() {
        return start_date;
    }

    public int getEventType() {
        return event_type;
    }

}
