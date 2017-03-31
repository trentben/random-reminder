package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.model.Reminder;

import io.realm.RealmResults;

public interface ReminderListView {
    void setReminders(RealmResults<Reminder> reminders);
    void addReminder(Reminder reminder);
    void removeReminder(Reminder reminder);
}
