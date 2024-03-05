package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ToolTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tool getToolSample1() {
        return new Tool().id(1L).tool("tool1");
    }

    public static Tool getToolSample2() {
        return new Tool().id(2L).tool("tool2");
    }

    public static Tool getToolRandomSampleGenerator() {
        return new Tool().id(longCount.incrementAndGet()).tool(UUID.randomUUID().toString());
    }
}
