package com.redpill.reminders.service.dagger;

import com.redpill.reminders.service.dagger.component.ApplicationComponent;

public class Injector {

    private static ApplicationComponent sComponent;

    public static void init(ApplicationComponent component) {
        sComponent = component;
    }

    public static ApplicationComponent get() {
        if (sComponent == null) {
            throw new RuntimeException("Injector has not been initialized");
        }
        return sComponent;
    }

}
