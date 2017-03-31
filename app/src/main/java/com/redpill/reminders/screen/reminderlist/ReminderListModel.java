package com.redpill.reminders.screen.reminderlist;

import android.content.Context;

import com.redpill.reminders.alarm.AlarmScheduler;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderRealm;

import io.realm.RealmResults;

public class ReminderListModel {

    private Context mContext;
    private ReminderRealm mRealm;
    private AlarmScheduler mScheduler;

    public ReminderListModel(Context context) {
        mContext = context;
        mRealm = new ReminderRealm();
        mScheduler = new AlarmScheduler(mContext);
    }


    public void addReminder(Reminder reminder) {
        mRealm.addReminder(reminder);
        mScheduler.scheduleAlarm(reminder);
    }

    public RealmResults<Reminder> getReminders() {
        return mRealm.getReminders();
    }
}
