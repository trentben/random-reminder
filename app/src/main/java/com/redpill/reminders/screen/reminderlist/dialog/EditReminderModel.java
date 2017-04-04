package com.redpill.reminders.screen.reminderlist.dialog;

import android.content.Context;

import com.redpill.reminders.alarm.AlarmScheduler;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderManager;

import io.realm.Realm;

public class EditReminderModel {

    private ReminderManager mManager;
    private Reminder mReminder;

    public EditReminderModel(Context context) {
        mManager = new ReminderManager(context);
    }

    public void createNewReminder(String title, int selectedFrequency) {
        mReminder = mManager.createReminder(title);
        mManager.getRealm().executeTransaction(realm -> {
            mReminder.setFrequency(selectedFrequency);
        });
        mManager.updateReminderTime(mReminder);
    }

    public void updateReminder(String title, int selectedFrequency) {
        if (mReminder != null) {
            mManager.getRealm().executeTransaction(realm -> {
                mReminder.setTitle(title);
                mReminder.setFrequency(selectedFrequency);
            });
            mManager.updateReminderTime(mReminder);
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
}
