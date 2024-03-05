package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DesiderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Desider getDesiderSample1() {
        return new Desider().id(1L).fullName("fullName1").email("email1").mobile("mobile1").role("role1");
    }

    public static Desider getDesiderSample2() {
        return new Desider().id(2L).fullName("fullName2").email("email2").mobile("mobile2").role("role2");
    }

    public static Desider getDesiderRandomSampleGenerator() {
        return new Desider()
            .id(longCount.incrementAndGet())
            .fullName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString())
            .role(UUID.randomUUID().toString());
    }
}
