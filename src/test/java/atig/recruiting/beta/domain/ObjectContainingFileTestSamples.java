package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectContainingFileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ObjectContainingFile getObjectContainingFileSample1() {
        return new ObjectContainingFile().id(1L);
    }

    public static ObjectContainingFile getObjectContainingFileSample2() {
        return new ObjectContainingFile().id(2L);
    }

    public static ObjectContainingFile getObjectContainingFileRandomSampleGenerator() {
        return new ObjectContainingFile().id(longCount.incrementAndGet());
    }
}
