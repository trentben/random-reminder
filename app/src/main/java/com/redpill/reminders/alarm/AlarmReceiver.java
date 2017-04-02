package com.redpill.reminders.alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderManager;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String REMINDER_ID = "ReminderId";

    private ReminderManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mManager = new ReminderManager(context);

        Reminder reminder = getReminderFromIntent(intent);
        showNotification(context, reminder);
        vibrate(context);


        mManager.updateReminderTime(reminder);

    }

    private void showNotification(Context context, Reminder reminder) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText(reminder.getTitle());

        int notificationId = reminder.getId();
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(notificationId, mBuilder.build());
    }

    private void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(750);
    }

    private Reminder getReminderFromIntent(Intent intent) {
        int reminderId = intent.getIntExtra(REMINDER_ID, 0);
        return mManager.getReminderById(reminderId);
    }

}
