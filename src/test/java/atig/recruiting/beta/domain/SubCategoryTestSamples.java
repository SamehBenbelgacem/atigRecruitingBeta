package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SubCategory getSubCategorySample1() {
        return new SubCategory().id(1L).title("title1");
    }

    public static SubCategory getSubCategorySample2() {
        return new SubCategory().id(2L).title("title2");
    }

    public static SubCategory getSubCategoryRandomSampleGenerator() {
        return new SubCategory().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString());
    }
}
