package com.redpill.reminders.realm;

import android.content.Context;

import com.redpill.reminders.alarm.AlarmScheduler;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.util.Utility;

import java.util.Calendar;
import java.util.Random;

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
        reminder.setRemindAt(generateReminderTime(reminder));
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

    private long generateReminderTime(Reminder reminder) {
        int frequency = reminder.getFrequency();

        Calendar time = Calendar.getInstance();

        Random random = new Random();
        int randHours = 7+random.nextInt(15); //Between 7am and 9pm
        int randMin = random.nextInt(60);

        time.set(Calendar.HOUR, randHours);
        time.set(Calendar.MINUTE, randMin);

        if (Reminder.FREQUENCY_HIGH == frequency) {
            time.add(Calendar.DATE, 1);
        } else if (Reminder.FREQUENCY_MEDIUM == frequency) {
            int randDays = 6 + random.nextInt(3); //Between 6 and 8 days
            time.add(Calendar.DATE, randDays);
        } else if (Reminder.FREQUENCY_LOW == frequency) {
            int randDays = 25 + random.nextInt(11); //Between 25 and 35 days
            time.add(Calendar.DATE, randDays);
        } else {
            time.add(Calendar.DATE, 1);
        }

        return time.getTimeInMillis();
    }
}
