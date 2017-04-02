package com.redpill.reminders.util;

import java.util.Calendar;
import java.util.Random;

public class Utility {
    public static long getRandomTime() {
        Calendar time = Calendar.getInstance();

//        Random random = new Random();
//        int randHours = random.nextInt(4);
//        int randMin = random.nextInt(60);
//
//        time.add(Calendar.HOUR, randHours);
//        time.add(Calendar.MINUTE, randMin);

        time.add(Calendar.SECOND, 10);

        return time.getTimeInMillis();
    }
}
