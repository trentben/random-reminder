package com.redpill.reminders.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Index;

public class Reminder extends RealmObject {

    public static final int FREQUENCY_LOW = 1;
    public static final int FREQUENCY_MEDIUM = 2;
    public static final int FREQUENCY_HIGH = 3;

    @Index
    private int id;
    private long createdAt;
    private String title;
    private int frequency;
    private long remindAt;
    private long lastReminder;

    public Reminder() {
        id = UUID.randomUUID().hashCode();
        createdAt = new Date().getTime();
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

    public long getCreatedAt() {
        return createdAt;
    }

    public long getLastReminder() {
        return lastReminder;
    }

    public void setLastReminder(long lastReminder) {
        this.lastReminder = lastReminder;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
