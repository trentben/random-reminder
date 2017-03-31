package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderRealm;

import io.realm.RealmResults;

public class ReminderListModel {

    ReminderRealm mRealm;

    public ReminderListModel() {
        mRealm = new ReminderRealm();
    }


    public void addReminder(Reminder reminder) {
        mRealm.addReminder(reminder);
    }

    public RealmResults<Reminder> getReminders() {
        return mRealm.getReminders();
    }
}
