package com.example.tuanle.mindfulness3574983;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Session.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class SessionDatabase extends RoomDatabase {
    public abstract SessionDao sessionDao();
}
