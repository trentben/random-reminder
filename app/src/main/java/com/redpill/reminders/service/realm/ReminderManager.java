package com.redpill.reminders.service.realm;

import android.content.Context;

import com.redpill.reminders.service.alarm.AlarmScheduler;
import com.redpill.reminders.model.data.AlarmHistory;
import com.redpill.reminders.model.data.Reminder;

import java.util.Calendar;
import java.util.Date;
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
        return mRealm.where(Reminder.class).findAllSorted("createdAt");
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


    public void scheduleReminderAlarm(Reminder reminder) {
        mRealm.executeTransaction(realm -> {
            reminder.setRemindAt(generateReminderTime(reminder));
            mScheduler.scheduleAlarm(reminder);
        });

    }

    public void enableReminder(Reminder reminder, boolean isEnabled) {
        getRealm().executeTransaction(realm -> reminder.setEnabled(isEnabled));
        if (isEnabled) {
            scheduleReminderAlarm(reminder);
        } else {
            mScheduler.removeAlarm(reminder);
        }
    }

    public Reminder createReminder(String title) {
        mRealm.beginTransaction();
        Reminder reminder = mRealm.createObject(Reminder.class);
        reminder.setTitle(title);
        mRealm.commitTransaction();

        scheduleReminderAlarm(reminder);

        return reminder;
    }

    public void recordAlarmHistory(Reminder reminder, long alarmTime) {
        getRealm().executeTransaction(realm -> {
            AlarmHistory alarmHistory = realm.createObject(AlarmHistory.class, alarmTime);
            reminder.getReminderHistory().add(alarmHistory);
        });
    }

    private long generateReminderTime(Reminder reminder) {
        int frequency = reminder.getFrequency();

        Calendar time = Calendar.getInstance();

        Random random = new Random();

        int randHours, randMin;
        switch (reminder.getAlarmTimeOfDay()) {
            case Reminder.TIME_MORNING:
                randHours = 6+random.nextInt(6); //Between 7am and 11pm
                randMin = random.nextInt(60);
                break;
            case Reminder.TIME_AFTERNOON:
                randHours = 12+random.nextInt(6); //Between 12pm and 5pm
                randMin = random.nextInt(60);
                break;
            case Reminder.TIME_EVENING:
                randHours = 17+random.nextInt(5); //Between 5pm and 9pm
                randMin = random.nextInt(60);
                break;
            default:
                randHours = 7+random.nextInt(15); //Between 7am and 9pm
                randMin = random.nextInt(60);
                break;
        }

        time.set(Calendar.HOUR_OF_DAY, randHours);
        time.set(Calendar.MINUTE, randMin);

        if (Reminder.FREQUENCY_HIGH == frequency) {
            time.add(Calendar.DATE, 1);
        } else if (Reminder.FREQUENCY_MEDIUM == frequency) {
            int randDays = 5 + random.nextInt(5); //Between 6 and 9 days
            time.add(Calendar.DATE, randDays);
        } else if (Reminder.FREQUENCY_LOW == frequency) {
            int randDays = 22 + random.nextInt(16); //Between 22 and 37 days
            time.add(Calendar.DATE, randDays);
        } else {
            time.add(Calendar.DATE, 1);
        }

//        return new Date().getTime() + 10000;
        return time.getTimeInMillis();
    }

    public void close() {
        mRealm.close();
    }
}
