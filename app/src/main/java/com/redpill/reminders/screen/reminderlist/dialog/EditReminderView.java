package com.redpill.reminders.screen.reminderlist.dialog;

public interface EditReminderView {
    boolean isUpdateMode();
    int getReminderIdParam();
    String getTitleText();
    void setTitleText(String text);
    void dismiss();
}
