package com.redpill.reminders;

import android.app.Application;

import io.realm.Realm;

public class ReminderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
