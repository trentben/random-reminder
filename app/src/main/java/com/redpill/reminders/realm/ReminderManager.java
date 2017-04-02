package com.redpill.reminders.realm;

import android.content.Context;

import com.redpill.reminders.alarm.AlarmScheduler;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.util.Utility;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReminderManager {

    private Realm mRealm;
    private AlarmScheduler mScheduler;


    public ReminderManager(Context context) {
        mRealm = Realm.getDefaultInstance();
        mScheduler = new AlarmScheduler(context);
    }

    public RealmResults<Reminder> getReminders() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Reminder> results = realm.where(Reminder.class).findAllSorted("createdAt");
        realm.close();
        return results;
    }

    public Reminder getReminderById(int reminderId) {
        return mRealm.where(Reminder.class).equalTo("id", reminderId).findAll().first();
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void removeReminder(int id) {
        Reminder reminder = getReminderById(id);
        if (reminder != null) {
            mScheduler.removeAlarm(reminder);
            mRealm.executeTransaction(realm -> {
                reminder.deleteFromRealm();
            });

        }
    }


    public void updateReminderTime(Reminder reminder) {
        mRealm.executeTransaction(realm -> {
            _updateReminderTime(reminder);
        });

    }

    private void _updateReminderTime(Reminder reminder) {
        reminder.setRemindAt(Utility.getRandomTime());
        mScheduler.scheduleAlarm(reminder);
    }

    public Reminder createReminder(String title) {
        mRealm.beginTransaction();
        Reminder reminder = mRealm.createObject(Reminder.class);
        reminder.setTitle(title);
        _updateReminderTime(reminder);
        mRealm.commitTransaction();
        return reminder;
    }
}
