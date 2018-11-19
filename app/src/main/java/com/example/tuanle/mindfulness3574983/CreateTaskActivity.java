package com.example.tuanle.mindfulness3574983;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CreateTaskActivity extends AppCompatActivity {
    String taskType;
    EditText durationET;
    TaskRepository taskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        durationET = findViewById(R.id.minuteField);
        taskRepository = new TaskRepository(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void addTaskClick(View view) {
        String duration = durationET.getText().toString();
        Date date = Calendar.getInstance().getTime();
        if (duration.equals("") || taskType.equals("")) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            taskRepository.addTask(new Task(Integer.parseInt(duration), taskType, false, date));
            finish();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.mb:
                if (checked) {
                    taskType = "mb";
                }
                break;
            case R.id.br:
                if (checked) {
                    taskType = "br";
                }
                break;
        }
    }
}
