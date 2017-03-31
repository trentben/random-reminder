package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.model.Reminder;

public class ReminderListPresenter {

    private final ReminderListView mView;
    private final ReminderListModel mModel;

    public ReminderListPresenter(ReminderListView view, ReminderListModel model) {
        mView = view;
        mModel = model;
    }

    public void onAddReminder(Reminder reminder) {
        mModel.addReminder(reminder);
    }

    public void onStart() {
        mView.setReminders(mModel.getReminders());

    }
}
