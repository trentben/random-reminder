package com.redpill.reminders.screen.reminderlist.dialog;

import com.redpill.reminders.model.Reminder;

public interface EditReminderView {

    int FREQUENCY_LOW = 1;
    int FREQUENCY_MEDIUM = 2;
    int FREQUENCY_HIGH = 3;

    boolean isUpdateMode();
    int getReminderIdParam();
    String getTitleText();
    void setTitleText(String text);
    int getSelectedFrequency();
    void setSelectedFrequency(int frequency);
    int getTimeOfDay();
    void setTimeOfDay(int timeOfDay);
    void navigateToDelete(Reminder reminder);
    void dismiss();

}
