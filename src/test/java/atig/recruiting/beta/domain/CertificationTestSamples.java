package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CertificationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Certification getCertificationSample1() {
        return new Certification().id(1L).title("title1");
    }

    public static Certification getCertificationSample2() {
        return new Certification().id(2L).title("title2");
    }

    public static Certification getCertificationRandomSampleGenerator() {
        return new Certification().id(longCount.incrementAndGet()).title(UUID.randomUUID().toString());
    }
}
