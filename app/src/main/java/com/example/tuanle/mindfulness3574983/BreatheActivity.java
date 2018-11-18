package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.Observer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BreatheActivity extends AppCompatActivity {
    Chronometer stopWatch;
    Button startButton;
    Button endButton;
    TextView today;
    TextView thisWeek;
    TextView thisMonth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathe);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        stopWatch = findViewById(R.id.stopWatch);
        startButton = findViewById(R.id.start);
        endButton = findViewById(R.id.end);
        endButton.setEnabled(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void onStartClick(View view) {
        stopWatch.setBase(SystemClock.elapsedRealtime());
        stopWatch.start();


        startButton.setEnabled(false);
        endButton.setEnabled(true);
    }

    public void onEndClick(View view) {
        long sessionDuration = SystemClock.elapsedRealtime() - stopWatch.getBase();
        stopWatch.stop();
        stopWatch.setBase(SystemClock.elapsedRealtime());
        SessionRepository sessionRepository = new SessionRepository(getApplicationContext());
        sessionRepository.addSession(new Session(sessionDuration, Calendar.getInstance().getTime()));
        sessionRepository.getAllSessions().observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                updateDWMTexts(sessions);
            }
        });
        startButton.setEnabled(true);
        endButton.setEnabled(false);
    }

    private void updateDWMTexts(List<Session> sessions) {

    }
}
