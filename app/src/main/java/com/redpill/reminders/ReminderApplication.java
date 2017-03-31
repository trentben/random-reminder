package com.redpill.reminders;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ReminderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .name("reminders.realm")
                .deleteRealmIfMigrationNeeded()
                .build());
    }
}
