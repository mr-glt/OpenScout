package xyz.syzygylabs.openscout.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import xyz.syzygylabs.openscout.R;
import xyz.syzygylabs.openscout.fragments.SetupFragment;

/**
 * Created by Charlie on 12/16/2016.
 */

public class Intro extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSkipEnabled(false);
        addSlide(new SimpleSlide.Builder()
                .title("Open Scout")
                .description("Open Source Scouting for FRC")
                .image(R.drawable.logo)
                .background(R.color.green)
                .backgroundDark(R.color.greenDark)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("How it Works")
                .description("Events for this season are automaticaly imported and arranged by date.")
                .image(R.drawable.blank_cards)
                .background(R.color.purple)
                .backgroundDark(R.color.purpleDark)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Roles")
                .image(R.drawable.demo_cards)
                .description("Scouts, drive team, and regular members are all presented with a different console.")
                .background(R.color.redAlt)
                .backgroundDark(R.color.redDarkAlt)
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .fragment(SetupFragment.newInstance())
                .build());
        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override
            public void onNavigationBlocked(int position, int direction) {
                Snackbar.make(findViewById(android.R.id.content), "Please Finish Setup First", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

    }
}