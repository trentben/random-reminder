package com.redpill.reminders.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderManager;
import com.redpill.reminders.screen.home.HomeActivity;
import com.redpill.reminders.util.Constant;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String REMINDER_ID = "ReminderId";

    private ReminderManager mManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mManager = new ReminderManager(context);

        Reminder reminder = getReminderFromIntent(intent);
        showNotification(context, reminder);

        mManager.recordAlarmHistory(reminder, reminder.getRemindAt());
        mManager.enableReminder(reminder, reminder.isRepeat());
    }

    private void showNotification(Context context, Reminder reminder) {
        int reminderId = reminder.getId();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(reminder.getTitle())
                        .setContentIntent(getReminderIntent(context, reminderId))
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)
                        .addAction(0, "DISABLE", getDisableReminderIntent(context, reminderId));

        getNotificationManager(context).notify(reminderId, mBuilder.build());
    }

    private PendingIntent getReminderIntent(Context context, int reminderId) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Constant.FLAG_REMINDER_ID, reminderId);
        return PendingIntent.getActivity(context, reminderId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
    }

    private PendingIntent getDisableReminderIntent(Context context, int reminderId) {
        Intent intent = DisableReminderService.getStartIntent(context, reminderId);
        return PendingIntent.getService(context, reminderId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT );
    }

    private Reminder getReminderFromIntent(Intent intent) {
        int reminderId = intent.getIntExtra(REMINDER_ID, 0);
        return mManager.getReminderById(reminderId);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }
}
