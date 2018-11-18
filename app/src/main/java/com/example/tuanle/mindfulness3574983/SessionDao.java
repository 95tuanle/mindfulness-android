package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SessionDao {
    @Insert
    void addSession(Session session);

    @Query("SELECT * FROM session")
    LiveData<List<Session>> getAllSessions();
}
