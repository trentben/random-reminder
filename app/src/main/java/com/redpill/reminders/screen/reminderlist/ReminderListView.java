package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.callback.ObjectCallback;
import com.redpill.reminders.model.Reminder;

import io.realm.RealmResults;

public interface ReminderListView {
    void setReminders(RealmResults<Reminder> reminders);
    void showEditReminderDialog(Reminder reminder);
    void showDeleteConfirmationDialog(ObjectCallback<Boolean> callback);
}
