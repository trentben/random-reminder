package com.redpill.reminders.realm;

import com.redpill.reminders.model.Reminder;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReminderRealm {

    private final Realm mRealm;

    public ReminderRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    public RealmResults<Reminder> getReminders() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Reminder> results = realm.where(Reminder.class).findAllSorted("createdAt");
        realm.close();
        return results;
    }

    public void addReminder(Reminder reminder) {
        mRealm.executeTransaction(realm -> {
            realm.copyToRealm(reminder);
        });
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
            mRealm.executeTransaction(realm -> {
                reminder.deleteFromRealm();
            });

        }
    }
}
