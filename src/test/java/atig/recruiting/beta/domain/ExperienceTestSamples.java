package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ExperienceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Experience getExperienceSample1() {
        return new Experience().id(1L).company("company1").companySite("companySite1").role("role1").location("location1").tasks("tasks1");
    }

    public static Experience getExperienceSample2() {
        return new Experience().id(2L).company("company2").companySite("companySite2").role("role2").location("location2").tasks("tasks2");
    }

    public static Experience getExperienceRandomSampleGenerator() {
        return new Experience()
            .id(longCount.incrementAndGet())
            .company(UUID.randomUUID().toString())
            .companySite(UUID.randomUUID().toString())
            .role(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString())
            .tasks(UUID.randomUUID().toString());
    }
}
