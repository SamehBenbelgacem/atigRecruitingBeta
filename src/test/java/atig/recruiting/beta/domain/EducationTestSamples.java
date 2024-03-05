package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EducationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Education getEducationSample1() {
        return new Education().id(1L).diploma("diploma1").establishment("establishment1").mention("mention1").location("location1");
    }

    public static Education getEducationSample2() {
        return new Education().id(2L).diploma("diploma2").establishment("establishment2").mention("mention2").location("location2");
    }

    public static Education getEducationRandomSampleGenerator() {
        return new Education()
            .id(longCount.incrementAndGet())
            .diploma(UUID.randomUUID().toString())
            .establishment(UUID.randomUUID().toString())
            .mention(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString());
    }
}
