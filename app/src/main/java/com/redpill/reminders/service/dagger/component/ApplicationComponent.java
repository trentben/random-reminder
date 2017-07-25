package com.redpill.reminders.service.dagger.component;

import com.redpill.reminders.service.alarm.DisableReminderService;
import com.redpill.reminders.service.dagger.module.ReminderModule;
import com.redpill.reminders.ui.edit.EditReminderModel;
import com.redpill.reminders.ui.reminderlist.ReminderListModel;

import dagger.Component;

@Component(modules = {ReminderModule.class})
public interface ApplicationComponent {
    void inject(EditReminderModel editReminderModel);
    void inject(ReminderListModel reminderListModel);
    void inject(DisableReminderService disableReminderService);
}
