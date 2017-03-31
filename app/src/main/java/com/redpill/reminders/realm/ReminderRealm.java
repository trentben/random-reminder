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
        RealmResults<Reminder> results = realm.where(Reminder.class).findAll();
        realm.close();
        return results;
    }

    public void addReminder(Reminder reminder) {
        mRealm.executeTransaction(realm -> {
            realm.copyToRealm(reminder);
        });
    }
}
