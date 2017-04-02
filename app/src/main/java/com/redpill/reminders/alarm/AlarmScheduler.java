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
        getAlarmManager().set(0, reminder.getRemindAt(), buildPendingIntent(reminder));
    }


    public void removeAlarm(Reminder reminder) {
        getAlarmManager().cancel(buildPendingIntent(reminder));
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    private PendingIntent buildPendingIntent(Reminder reminder) {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.REMINDER_ID, reminder.getId());
        return PendingIntent.getBroadcast(mContext, reminder.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
