package com.redpill.reminders.screen.reminderlist.dialog;

import com.redpill.reminders.model.Reminder;

public class EditReminderPresenter {

    private EditReminderView mView;
    private EditReminderModel mModel;

    public EditReminderPresenter(EditReminderView view, EditReminderModel model) {
        mView = view;
        mModel = model;
        init();
    }

    private void init() {
        if (mView.isUpdateMode()) {
            mModel.setReminderToUpdate(mView.getReminderIdParam());
            mView.setTitleText(mModel.getReminderTitle());
            mView.setSelectedFrequency(mModel.getSelectedFrequency());
            mView.setTimeOfDay(mModel.getTimeOfDay());
        } else {
            mView.setSelectedFrequency(Reminder.FREQUENCY_MEDIUM);
        }
    }

    public void onAddReminderClick() {
        mModel.createNewReminder(mView.getTitleText(), mView.getSelectedFrequency(), mView.getTimeOfDay());
        mView.dismiss();
    }

    public void onUpdateReminderClick() {
        mModel.updateReminder(mView.getTitleText(), mView.getSelectedFrequency(), mView.getTimeOfDay());
        mView.dismiss();
    }

    public void onDeleteReminder() {
        mView.navigateToDelete(mModel.getReminder());
    }
}
