package com.redpill.reminders.ui.reminderlist;

import com.redpill.reminders.model.callback.ObjectCallback;
import com.redpill.reminders.model.data.Reminder;

import io.realm.RealmResults;

public interface ReminderListView {
    void setReminders(RealmResults<Reminder> reminders);
    void showEditReminderDialog(Reminder reminder);
    void showDeleteConfirmationDialog(ObjectCallback<Boolean> callback);
}
