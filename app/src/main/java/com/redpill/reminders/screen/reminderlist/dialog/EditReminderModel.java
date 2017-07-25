package com.redpill.reminders.screen.reminderlist.dialog;

import android.content.Context;

import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderManager;

public class EditReminderModel {

    private ReminderManager mManager;
    private Reminder mReminder;

    public EditReminderModel(Context context) {
        mManager = new ReminderManager(context);
    }

    public void createNewReminder(String title, int selectedFrequency, int timeOfDay, boolean isRepeat) {
        mReminder = mManager.createReminder(title);
        mManager.getRealm().executeTransaction(realm -> {
            mReminder.setFrequency(selectedFrequency);
            mReminder.setAlarmTimeOfDay(timeOfDay);
            mReminder.setRepeat(isRepeat);
        });
        mManager.scheduleReminderAlarm(mReminder);
    }

    public void updateReminder(String title, int selectedFrequency, int timeOfDay, boolean isRepeat) {
        if (mReminder != null) {
            mManager.getRealm().executeTransaction(realm -> {
                mReminder.setTitle(title);
                mReminder.setFrequency(selectedFrequency);
                mReminder.setAlarmTimeOfDay(timeOfDay);
                mReminder.setRepeat(isRepeat);
            });
            mManager.scheduleReminderAlarm(mReminder);
        } else {
            throw new RuntimeException("You must set a reminder using setReminderToUpdate() before calling updateReminder()");
        }
    }

    public void setReminderToUpdate(int reminderId) {
        mReminder = mManager.getReminderById(reminderId);
    }

    public String getReminderTitle() {
        return mReminder.getTitle();
    }

    public int getSelectedFrequency() {
        return mReminder.getFrequency();
    }

    public int getTimeOfDay() {
        return mReminder.getAlarmTimeOfDay();
    }

    public Reminder getReminder() {
        return mReminder;
    }

    public boolean getIsRepeat() {
        return mReminder.isRepeat();
    }

    public void onDestroy() {
        mManager.close();
        mManager = null;
    }
}
