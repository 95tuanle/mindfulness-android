package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class TaskRepository {
    private TaskDatabase taskDatabase;

    TaskRepository(Context context) {
        taskDatabase = Room.databaseBuilder(context, TaskDatabase.class, "tasks").allowMainThreadQueries().build();
    }

    void addTask(Task task) {
        taskDatabase.taskDao().addTask(task);
    }

    LiveData<List<Task>> getAllTasks() {
        return taskDatabase.taskDao().getAllTasks();
    }

    void updateTask(final Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                taskDatabase.taskDao().updateTask(task);
                return null;
            }
        }.execute();
    }
}
