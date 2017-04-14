package com.redpill.reminders.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;

public class Reminder extends RealmObject {

    public static final int FREQUENCY_LOW = 1;
    public static final int FREQUENCY_MEDIUM = 2;
    public static final int FREQUENCY_HIGH = 3;

    public static final int TIME_ANY = 0;
    public static final int TIME_MORNING = 1;
    public static final int TIME_AFTERNOON = 2;
    public static final int TIME_EVENING = 3;

    @Index
    private int id;
    private long createdAt;
    private boolean isEnabled;
    private boolean isRepeat;
    private String title;
    private int frequency;
    private int alarmTimeOfDay;
    private long remindAt;
    private RealmList<AlarmHistory> reminderHistory;


    public Reminder() {
        id = UUID.randomUUID().hashCode();
        createdAt = new Date().getTime();
        isEnabled = true;
        isRepeat = true;
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getAlarmTimeOfDay() {
        return alarmTimeOfDay;
    }

    public void setAlarmTimeOfDay(int alarmTimeOfDay) {
        this.alarmTimeOfDay = alarmTimeOfDay;
    }

    public RealmList<AlarmHistory> getReminderHistory() {
        return reminderHistory;
    }

    public void setReminderHistory(RealmList<AlarmHistory> reminderHistory) {
        this.reminderHistory = reminderHistory;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean disabled) {
        isEnabled = disabled;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }
}
