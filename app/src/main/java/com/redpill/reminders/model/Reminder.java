package com.redpill.reminders.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class Reminder extends RealmObject {

    @Index
    private int id;
    private String title;
    private long remindAt;

    public Reminder() {
        id = UUID.randomUUID().hashCode();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getRemindAt() {
        return remindAt;
    }

    public void setRemindAt(long remindAt) {
        this.remindAt = remindAt;
    }

    public int getId() {
        return id;
    }
}
