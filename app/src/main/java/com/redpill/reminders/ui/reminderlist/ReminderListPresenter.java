package com.redpill.reminders.ui.reminderlist;

import com.redpill.reminders.model.data.Reminder;

public class ReminderListPresenter {

    private ReminderListView mView;
    private ReminderListModel mModel;

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

    public void onReminderEnableChange(Reminder reminder, boolean enable) {
        mModel.updateReminderEnable(reminder, enable);
    }

    public void onDestroy() {
        mModel.onDestroy();
        mModel = null;
    }
}
