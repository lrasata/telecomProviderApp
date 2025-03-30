package com.lrasata.telecomproviderapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ApplicationUser getApplicationUserSample1() {
        return new ApplicationUser().id(1L).phoneNumber(1).wallet(1);
    }

    public static ApplicationUser getApplicationUserSample2() {
        return new ApplicationUser().id(2L).phoneNumber(2).wallet(2);
    }

    public static ApplicationUser getApplicationUserRandomSampleGenerator() {
        return new ApplicationUser()
            .id(longCount.incrementAndGet())
            .phoneNumber(intCount.incrementAndGet())
            .wallet(intCount.incrementAndGet());
    }
}
