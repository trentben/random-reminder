package com.redpill.reminders.screen.reminderlist.dialog;

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
        }
    }

    public void onAddReminderClick() {
        mModel.createNewReminder(mView.getTitleText());
        mView.dismiss();
    }

    public void onUpdateReminderClick() {
        mModel.updateReminder(mView.getTitleText());
        mView.dismiss();
    }
}
