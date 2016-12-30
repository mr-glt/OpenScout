package xyz.syzygylabs.openscout.objects.teamevents;

import java.util.List;

/**
 * Created by Charlie on 12/23/2016.
 */

public class Alliance {

    private List<String> declines = null;
    private Object backup;
    private String name;
    private List<String> picks = null;

    public List<String> getDeclines() {
        return declines;
    }

    public Object getBackup() {
        return backup;
    }


    public String getName() {
        return name;
    }

    public List<String> getPicks() {
        return picks;
    }

}
