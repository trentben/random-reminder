package com.redpill.reminders.service.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.redpill.reminders.model.data.Reminder;
import com.redpill.reminders.service.realm.ReminderManager;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmScheduler alarmScheduler = new AlarmScheduler(context);
        ReminderManager manager = new ReminderManager(context);

        List<Reminder> reminders = manager.getReminders();
        for (Reminder r : reminders) {
            alarmScheduler.scheduleAlarm(r);
        }

        Log.d("BootReceiver", "Alarms Restored");
    }
}
