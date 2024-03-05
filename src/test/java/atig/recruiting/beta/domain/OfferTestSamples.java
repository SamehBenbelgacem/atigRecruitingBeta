package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OfferTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Offer getOfferSample1() {
        return new Offer().id(1L).title("title1").description("description1");
    }

    public static Offer getOfferSample2() {
        return new Offer().id(2L).title("title2").description("description2");
    }

    public static Offer getOfferRandomSampleGenerator() {
        return new Offer().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
