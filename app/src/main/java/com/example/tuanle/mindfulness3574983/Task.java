package com.example.tuanle.mindfulness3574983;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    int duration;

    String type;

    boolean status;

    Date dateCreated;

    public Task(int duration, String type, boolean status, Date dateCreated) {
        this.duration = duration;
        this.type = type;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
