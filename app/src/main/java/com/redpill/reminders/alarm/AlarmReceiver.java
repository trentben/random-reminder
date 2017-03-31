package com.redpill.reminders.alarm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.redpill.reminders.R;
import com.redpill.reminders.model.Reminder;
import com.redpill.reminders.realm.ReminderRealm;

import java.util.Calendar;
import java.util.Random;

import io.realm.Realm;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String REMINDER_ID = "ReminderId";

    private ReminderRealm mRealm;

    @Override
    public void onReceive(Context context, Intent intent) {
        mRealm = new ReminderRealm();

        int reminderId = intent.getIntExtra(REMINDER_ID, 0);
        Reminder reminder = mRealm.getReminderById(reminderId);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText(reminder.getTitle());

        // Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

        mRealm.getRealm().executeTransaction(realm -> reminder.setRemindAt(getRandomTime()));
        new AlarmScheduler(context).scheduleAlarm(reminder);
    }

    private long getRandomTime() {
        Calendar time = Calendar.getInstance();

        Random random = new Random();
        int randHours = random.nextInt(4);
        int randMin = random.nextInt(60);

        time.add(Calendar.HOUR, randHours);
        time.add(Calendar.MINUTE, randMin);

        return time.getTimeInMillis();
    }
}
