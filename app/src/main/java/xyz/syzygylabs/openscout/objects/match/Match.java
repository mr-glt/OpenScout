package xyz.syzygylabs.openscout.objects.match;

import java.util.List;

/**
 * Created by Charlie on 12/22/2016.
 */

public class Match {
    private String comp_level;
    private int match_number;
    private List<Video> videos = null;
    private Object time_string;
    private int set_number;
    private String key;
    private int time;
    private ScoreBreakdown score_breakdown;
    private Alliances alliances;
    private String event_key;

    public String getCompLevel() {
        return comp_level;
    }

    public int getMatchNumber() {
        return match_number;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public Object getTimeString() {
        return time_string;
    }

    public int getSetNumber() {
        return set_number;
    }

    public String getKey() {
        return key;
    }


    public int getTime() {
        return time;
    }

    public ScoreBreakdown getScoreBreakdown() {
        return score_breakdown;
    }


    public Alliances getAlliances() {
        return alliances;
    }
    public String getEventKey() {
        return event_key;
    }

}
