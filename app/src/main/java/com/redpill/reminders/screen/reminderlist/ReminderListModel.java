package com.redpill.reminders.screen.reminderlist;

import io.realm.Realm;

public class ReminderListModel {

    Realm mRealm;

    public ReminderListModel() {
        mRealm = Realm.getDefaultInstance();
    }



}
