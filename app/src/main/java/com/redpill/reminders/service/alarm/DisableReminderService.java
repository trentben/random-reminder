package com.redpill.reminders.service.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.redpill.reminders.model.data.Reminder;
import com.redpill.reminders.service.dagger.Injector;
import com.redpill.reminders.service.realm.ReminderManager;

import javax.inject.Inject;

public class DisableReminderService extends IntentService {

    public static final String EXTRA_REMINDER_ID = "ReminderId";
    private static final String TAG = "DisableReminderService";

    @Inject ReminderManager mReminderManager;

    public static Intent getStartIntent(Context context, int reminderId) {
        Intent intent = new Intent(context, DisableReminderService.class);
        intent.putExtra(EXTRA_REMINDER_ID, reminderId);
        return intent;
    }


    public DisableReminderService() {
        super("DisableReminderService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Injector.get().inject(this);

        Reminder reminder = getReminderFromIntent(intent);
        if (reminder != null) {
            mReminderManager.enableReminder(reminder, false);

            //Cancel the notification that started this Service
            getNotificationManager(getApplicationContext()).cancel(reminder.getId());
        }

        closeReminderManager();
    }

//    private void initReminderManager() {
//        mReminderManager = new ReminderManager(getApplicationContext());
//    }

    private void closeReminderManager() {
        mReminderManager.close();
        mReminderManager = null;
    }

    private Reminder getReminderFromIntent(Intent intent) {
        int id = intent.getIntExtra(EXTRA_REMINDER_ID, 0);
        return mReminderManager.getReminderById(id);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }
}
