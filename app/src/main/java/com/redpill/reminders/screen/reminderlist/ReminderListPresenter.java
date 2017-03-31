package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.model.Reminder;

public class ReminderListPresenter {

    private final ReminderListView mView;

    public ReminderListPresenter(ReminderListView view) {
        mView = view;
    }

    public void onAddReminder(Reminder reminder) {
        mView.addReminder(reminder);
    }
}
