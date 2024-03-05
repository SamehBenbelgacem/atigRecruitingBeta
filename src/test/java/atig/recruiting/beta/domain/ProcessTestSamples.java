package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProcessTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Process getProcessSample1() {
        return new Process().id(1L).title("title1");
    }

    public static Process getProcessSample2() {
        return new Process().id(2L).title("title2");
    }

    public static Process getProcessRandomSampleGenerator() {
        return new Process().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString());
    }
}
