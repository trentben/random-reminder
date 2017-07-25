package com.redpill.reminders.ui.reminderlist;

import android.content.Context;

import com.redpill.reminders.model.data.Reminder;
import com.redpill.reminders.service.realm.ReminderManager;

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
        mRealm.enableReminder(reminder, enable);
    }

    public void onDestroy() {
        mRealm.close();
        mRealm = null;
    }
}
