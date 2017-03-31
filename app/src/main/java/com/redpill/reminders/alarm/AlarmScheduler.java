package com.redpill.reminders.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.redpill.reminders.model.Reminder;

import java.util.Calendar;
import java.util.Random;

public class AlarmScheduler {

    private Context mContext;

    public AlarmScheduler(Context context) {
        mContext = context;
    }

    public void scheduleAlarm(Reminder reminder) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, reminder.getId(), new Intent(mContext, AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(0, reminder.getRemindAt(), pendingIntent);
    }


}
