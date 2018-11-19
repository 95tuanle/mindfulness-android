package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) > 4) {
            final TaskRepository taskRepository = new TaskRepository(getApplicationContext());
            taskRepository.getAllTasks().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    boolean willCreateMB = true;
                    boolean willCreateBR = true;
                    for (Task task:
                         tasks) {
                        Calendar taskCalendar = Calendar.getInstance();
                        taskCalendar.setTime(task.getDateCreated());
                        if (taskCalendar.get(Calendar.DAY_OF_YEAR) ==  calendar.get(Calendar.DAY_OF_YEAR) && taskCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                            if (task.getType().equals("mb") && task.getDuration() == 15) {
                                willCreateMB = false;
                            }
                            if (task.getType().equals("br") && task.getDuration() == 15) {
                                willCreateBR = false;
                            }
                        }
                    }
                    if (willCreateMB) {
                        taskRepository.addTask(new Task(15, "mb", false, Calendar.getInstance().getTime()));
                    }
                    if (willCreateBR) {
                        taskRepository.addTask(new Task(15, "br", false, Calendar.getInstance().getTime()));
                    }
                }
            });
        }
    }

    public void ToBreatheClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BreatheActivity.class);
        startActivity(intent);
    }

    public void ToTodoClick(View view) {
        Intent intent = new Intent(HomeActivity.this, TodoActivity.class);
        startActivity(intent);
    }
}
