package com.redpill.reminders.service.dagger.module;

import android.content.Context;

import com.redpill.reminders.service.realm.ReminderManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ReminderModule {

    private final Context mContext;

    public ReminderModule(Context context) {
        mContext = context;
    }

    @Provides
    public ReminderManager provideReminderManager() {
        return new ReminderManager(mContext);
    }
}
