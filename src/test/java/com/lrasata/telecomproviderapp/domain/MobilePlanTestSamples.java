package com.lrasata.telecomproviderapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MobilePlanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MobilePlan getMobilePlanSample1() {
        return new MobilePlan().id(1L).name("name1").internetDataInGB(1).description("description1");
    }

    public static MobilePlan getMobilePlanSample2() {
        return new MobilePlan().id(2L).name("name2").internetDataInGB(2).description("description2");
    }

    public static MobilePlan getMobilePlanRandomSampleGenerator() {
        return new MobilePlan()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .internetDataInGB(intCount.incrementAndGet())
            .description(UUID.randomUUID().toString());
    }
}
