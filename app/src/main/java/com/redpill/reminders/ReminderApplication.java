package com.redpill.reminders;

import android.app.Application;

import com.redpill.reminders.service.dagger.Injector;
import com.redpill.reminders.service.dagger.component.ApplicationComponent;
import com.redpill.reminders.service.dagger.component.DaggerApplicationComponent;
import com.redpill.reminders.service.dagger.module.ReminderModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ReminderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        initDagger();
    }

    private void initRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .name("reminders.realm")
                .deleteRealmIfMigrationNeeded()
                .build());
    }

    private void initDagger() {
        ApplicationComponent component = DaggerApplicationComponent.builder()
                .reminderModule(new ReminderModule(this))
                .build();

        Injector.init(component);
    }
}
