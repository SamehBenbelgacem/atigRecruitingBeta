package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CandidateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Candidate getCandidateSample1() {
        return new Candidate()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .profession("profession1")
            .nbExperience(1)
            .personalEmail("personalEmail1");
    }

    public static Candidate getCandidateSample2() {
        return new Candidate()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .profession("profession2")
            .nbExperience(2)
            .personalEmail("personalEmail2");
    }

    public static Candidate getCandidateRandomSampleGenerator() {
        return new Candidate()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .profession(UUID.randomUUID().toString())
            .nbExperience(intCount.incrementAndGet())
            .personalEmail(UUID.randomUUID().toString());
    }
}
