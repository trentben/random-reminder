package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.model.Reminder;

public interface ReminderListView {
    void addReminder(Reminder reminder);
    void removeReminder(Reminder reminder);
}
