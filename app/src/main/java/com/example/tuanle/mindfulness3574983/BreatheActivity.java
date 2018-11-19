package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.Observer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class BreatheActivity extends AppCompatActivity {
    Chronometer stopWatch;
    Button startButton;
    Button endButton;
    TextView today;
    TextView thisWeek;
    TextView thisMonth;
    SessionRepository sessionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathe);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        stopWatch = findViewById(R.id.stopWatch);
        startButton = findViewById(R.id.start);
        endButton = findViewById(R.id.end);
        today = findViewById(R.id.today);
        thisWeek = findViewById(R.id.thisWeek);
        thisMonth = findViewById(R.id.thisMonth);
        endButton.setEnabled(false);
        sessionRepository = new SessionRepository(getApplicationContext());
        sessionRepository.getAllSessions().observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                updateDWMTexts(sessions, Calendar.getInstance());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionRepository.getAllSessions().observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                updateDWMTexts(sessions, Calendar.getInstance());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long sessionDuration = SystemClock.elapsedRealtime() - stopWatch.getBase();
        sessionRepository.addSession(new Session(sessionDuration, Calendar.getInstance().getTime()));
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
        final Calendar calendar = Calendar.getInstance();
        sessionRepository.addSession(new Session(sessionDuration, calendar.getTime()));
        sessionRepository.getAllSessions().observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                updateDWMTexts(sessions, calendar);
            }
        });
        startButton.setEnabled(true);
        endButton.setEnabled(false);
    }

    private void updateDWMTexts(List<Session> sessions, Calendar calendar) {
        int dayCurrent = calendar.get(Calendar.DATE);
        int weekCurrent = calendar.get(Calendar.WEEK_OF_MONTH);
        int monthCurrent = calendar.get(Calendar.MONTH);
        int yearCurrent = calendar.get(Calendar.YEAR);
        long dayDuration = 0;
        long weekDuration = 0;
        long monthDuration = 0;
        for (Session session:
             sessions) {
            Calendar calendarFromSession = Calendar.getInstance();
            calendarFromSession.setTime(session.getDateAdded());
            long sessionDuration = session.getDuration();
            if (calendarFromSession.get(Calendar.YEAR) == yearCurrent) {
                if (calendarFromSession.get(Calendar.MONTH) == monthCurrent) {
                    monthDuration += sessionDuration;
                    if (calendarFromSession.get(Calendar.WEEK_OF_MONTH) == weekCurrent) {
                        weekDuration += sessionDuration;
                        if (calendarFromSession.get(Calendar.DATE) == dayCurrent) {
                            dayDuration += sessionDuration;
                        }
                    }
                }
            }
        }
        today.setText(buildDuration(dayDuration));
        thisWeek.setText(buildDuration(weekDuration));
        thisMonth.setText(buildDuration(monthDuration));
    }

    private String buildDuration(long duration) {
        long millis = duration % 1000;
        long second = (duration / 1000) % 60;
        long minute = (duration / (1000 * 60)) % 60;
        long hour = (duration / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
    }
}
