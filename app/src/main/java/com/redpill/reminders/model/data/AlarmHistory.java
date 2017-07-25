package com.redpill.reminders.model.data;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class AlarmHistory extends RealmObject{

    @PrimaryKey
    @Index
    private long alarmDate;

    public long getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(long alarmDate) {
        this.alarmDate = alarmDate;
    }
}
