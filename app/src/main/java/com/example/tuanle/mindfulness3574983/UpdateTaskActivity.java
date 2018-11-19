package com.example.tuanle.mindfulness3574983;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Objects;

public class UpdateTaskActivity extends AppCompatActivity {
    boolean taskStatus;
    Task taskToBeUpdated;
    TextView taskName;
    RadioButton done;
    RadioButton notDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        taskName = findViewById(R.id.taskName);
        done = findViewById(R.id.done);
        notDone = findViewById(R.id.notDone);
        taskToBeUpdated = (Task) getIntent().getExtras().get("task");
        if (taskToBeUpdated.getType().equals("mb")) {
            taskName.setText("Mindfulness breathing for " + taskToBeUpdated.duration + " minutes");
            //TODO: fix minutes vs minute on display if have time
        } else if (taskToBeUpdated.getType().equals("br")) {
            taskName.setText("Bedtime retrospection for " + taskToBeUpdated.duration + " minutes");
        }

        if (taskToBeUpdated.isStatus()) {
            done.toggle();
        } else {
            notDone.toggle();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void onStatusUpdateClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.done:
                if (checked) {
                    taskStatus = true;
                }
                break;
            case R.id.notDone:
                if (checked) {
                    taskStatus = false;
                }
                break;
        }
    }

    public void onUpdateTaskClick(View view) {
        TaskRepository taskRepository = new TaskRepository(getApplicationContext());
        taskToBeUpdated.setStatus(taskStatus);
        taskRepository.updateTask(taskToBeUpdated);
        finish();
    }
}
