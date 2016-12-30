package xyz.syzygylabs.openscout.api;

import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.syzygylabs.openscout.objects.Event;
import xyz.syzygylabs.openscout.objects.TeamNoRobot;
import xyz.syzygylabs.openscout.objects.match.Match;
import xyz.syzygylabs.openscout.objects.teamevents.TeamEvents;

/**
 * Created by Charlie on 12/22/2016.
 */

public interface ApiInterface {
    //URL Param for TBA API '?X-TBA-App-Id="frc6027:openscout:v01"'
    @GET("/api/v2/event/{event}?X-TBA-App-Id=frc6027:openscout:v01")
    Call<Event> getEvent(@Path("event") String event);
    @GET("/api/v2/team/{team}/event/{event}/matches?X-TBA-App-Id=frc6027:openscout:v01")
    Call<List<Match>> getMatches(@Path("team") String team, @Path("event") String event);
    @GET("/api/v2/team/{team}/{year}/events?X-TBA-App-Id=frc6027:openscout:v01")
    Call<List<TeamEvents>> getEvents(@Path("team") String team, @Path("year") String year);
    @GET("/api/v2/event/{event}/teams?X-TBA-App-Id=frc6027:openscout:v01")
    Call<List<TeamNoRobot>> getTeams(@Path("event") String event);
    @GET("/api/v2/team/{team}?X-TBA-App-Id=frc6027:openscout:v01")
    Call<TeamNoRobot> getTeam(@Path("team") String team);
    @GET("/api/v2/event/{event}/stats?X-TBA-App-Id=frc6027:openscout:v01")
    Call<JsonElement> getEventStats(@Path("event") String event);
    @GET("/api/v2/match/{match}?X-TBA-App-Id=frc6027:openscout:v01")
    Call<Match> getMatchStats(@Path("match") String match);
}
