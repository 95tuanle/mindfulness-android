package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void addTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

    @Update
    void updateTask(Task task);
}
