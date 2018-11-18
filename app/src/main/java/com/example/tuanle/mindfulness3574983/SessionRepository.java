package com.example.tuanle.mindfulness3574983;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

class SessionRepository {
    private SessionDatabase sessionDatabase;

    SessionRepository(Context context) {
        sessionDatabase = Room.databaseBuilder(context, SessionDatabase.class, "sessions").allowMainThreadQueries().build();
    }

    void addSession(Session session) {
        sessionDatabase.sessionDao().addSession(session);
    }

    LiveData<List<Session>> getAllSessions() {
        return sessionDatabase.sessionDao().getAllSessions();
    }
}
