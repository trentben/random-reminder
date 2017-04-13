package com.redpill.reminders.screen.reminderlist;

import com.redpill.reminders.model.Reminder;

public class ReminderListPresenter {

    private final ReminderListView mView;
    private final ReminderListModel mModel;

    public ReminderListPresenter(ReminderListView view, ReminderListModel model) {
        mView = view;
        mModel = model;
    }

    public void onDeleteReminder(Reminder reminder) {
        mView.showDeleteConfirmationDialog(doDelete -> {
            if (doDelete) {
                mModel.deleteReminder(reminder);
            }
        });
    }

    public void onStart() {
        mView.setReminders(mModel.getReminders());

    }

    public void onReminderClick(Reminder reminder) {
        mView.showEditReminderDialog(reminder);
    }
}
