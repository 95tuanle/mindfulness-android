package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class TodoActivity extends AppCompatActivity {
    ListView taskList;
    TaskRepository taskRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        taskList = findViewById(R.id.taskList);
        taskRepository = new TaskRepository(getApplicationContext());
        taskRepository.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                updateTaskList(tasks);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        taskRepository.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                updateTaskList(tasks);
            }
        });
    }

    private void updateTaskList(final List<Task> tasks) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, tasks) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                Task task = tasks.get(position);
                if (task.getType().equals("mb")) {
                    text1.setText("Mindfulness breathing for " + task.duration + " minutes");
                    //TODO: fix minutes vs minute on display if have time
                } else if (task.getType().equals("br")) {
                    text1.setText("Bedtime retrospection for " + task.duration + " minutes");
                }
                if (task.status) {
                    text2.setText("Status: done");
                } else {
                    text2.setText("Status: new");
                }

                return view;
            }
        };
        taskList.setAdapter(arrayAdapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TodoActivity.this, BreatheActivity.class);
                startActivity(intent);
            }
        });

        //In order to edit the status, please long press to a table cell
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TodoActivity.this, UpdateTaskActivity.class);
                intent.putExtra("task", tasks.get(position));
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void createButton(View view) {
        Intent intent = new Intent(TodoActivity.this, CreateTaskActivity.class);
        startActivity(intent);
    }
}
