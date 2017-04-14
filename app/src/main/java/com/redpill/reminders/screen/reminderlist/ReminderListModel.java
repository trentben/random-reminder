package com.redpill.reminders.screen.reminderlist;

import android.content.Context;

import com.redpill.reminders.alarm.AlarmScheduler;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderManager;

import io.realm.RealmResults;

public class ReminderListModel {

    private Context mContext;
    private ReminderManager mRealm;

    public ReminderListModel(Context context) {
        mContext = context;
        mRealm = new ReminderManager(mContext);
    }

    public RealmResults<Reminder> getReminders() {
        return mRealm.getReminders();
    }

    public void deleteReminder(Reminder reminder) {
        mRealm.removeReminder(reminder.getId());
    }

    public void updateReminderEnable(Reminder reminder, boolean enable) {
        mRealm.updateReminderEnabled(reminder, enable);
    }
}
