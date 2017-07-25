package com.redpill.reminders.ui.edit;

import android.content.Context;

import com.redpill.reminders.model.data.Reminder;
import com.redpill.reminders.service.dagger.Injector;
import com.redpill.reminders.service.realm.ReminderManager;

import javax.inject.Inject;

public class EditReminderModel {

    @Inject ReminderManager mManager;

    private Reminder mReminder;

    public EditReminderModel() {
        Injector.get().inject(this);
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
