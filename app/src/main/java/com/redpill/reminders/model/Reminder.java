package com.redpill.reminders.model;

import io.realm.RealmObject;

public class Reminder extends RealmObject {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
