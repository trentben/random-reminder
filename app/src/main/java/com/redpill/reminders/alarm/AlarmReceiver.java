package com.redpill.reminders.alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderRealm;
import com.redpill.reminders.util.Utility;

import java.util.Calendar;
import java.util.Random;

import io.realm.Realm;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String REMINDER_ID = "ReminderId";

    private ReminderRealm mRealm;

    @Override
    public void onReceive(Context context, Intent intent) {

        Reminder reminder = getReminderFromIntent(intent);
        showNotification(context, reminder);
        vibrate(context);


        getReminderRealm().removeReminder(reminder.getId());

    }

    private void showNotification(Context context, Reminder reminder) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText(reminder.getTitle());

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(750);
    }

    private Reminder getReminderFromIntent(Intent intent) {
        int reminderId = intent.getIntExtra(REMINDER_ID, 0);
        return getReminderRealm().getReminderById(reminderId);
    }

    private ReminderRealm getReminderRealm() {
        if (mRealm == null) {
            mRealm = new ReminderRealm();
        }
        return mRealm;
    }

}
