package com.redpill.reminders.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderManager;

import io.realm.Realm;

public class DisableReminderService extends IntentService {

    public static final String EXTRA_REMINDER_ID = "ReminderId";
    private static final String TAG = "DisableReminderService";

    private ReminderManager mReminderManager;

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
        initReminderManager();

        Reminder reminder = getReminderFromIntent(intent);
        if (reminder != null) {
            mReminderManager.updateReminderEnabled(reminder, false);

            //Cancel the notification that started this Service
            getNotificationManager(getApplicationContext()).cancel(reminder.getId());
        }
    }

    private void initReminderManager() {
        mReminderManager = new ReminderManager(getApplicationContext());

    }

    private Reminder getReminderFromIntent(Intent intent) {
        int id = intent.getIntExtra(EXTRA_REMINDER_ID, 0);
        return mReminderManager.getReminderById(id);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }
}
