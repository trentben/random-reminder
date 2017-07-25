package com.redpill.reminders.ui.reminderlist;

import com.redpill.reminders.model.data.Reminder;
import com.redpill.reminders.service.dagger.Injector;
import com.redpill.reminders.service.realm.ReminderManager;

import javax.inject.Inject;

import io.realm.RealmResults;

public class ReminderListModel {

    @Inject ReminderManager mManager;

    public ReminderListModel() {
        Injector.get().inject(this);
    }

    public RealmResults<Reminder> getReminders() {
        return mManager.getReminders();
    }

    public void deleteReminder(Reminder reminder) {
        mManager.removeReminder(reminder.getId());
    }

    public void updateReminderEnable(Reminder reminder, boolean enable) {
        mManager.enableReminder(reminder, enable);
    }

    public void onDestroy() {
        mManager.close();
        mManager = null;
    }
}
