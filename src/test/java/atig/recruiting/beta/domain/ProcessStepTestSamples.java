package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProcessStepTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ProcessStep getProcessStepSample1() {
        return new ProcessStep().id(1L).title("title1").order("order1");
    }

    public static ProcessStep getProcessStepSample2() {
        return new ProcessStep().id(2L).title("title2").order("order2");
    }

    public static ProcessStep getProcessStepRandomSampleGenerator() {
        return new ProcessStep().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).order(UUID.randomUUID().toString());
    }
}
