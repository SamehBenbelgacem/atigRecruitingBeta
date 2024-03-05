package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CandidateAdditionalInfosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CandidateAdditionalInfos getCandidateAdditionalInfosSample1() {
        return new CandidateAdditionalInfos()
            .id(1L)
            .actualSalary(1)
            .expectedSalary(1)
            .location("location1")
            .mobile("mobile1")
            .disponibility("disponibility1");
    }

    public static CandidateAdditionalInfos getCandidateAdditionalInfosSample2() {
        return new CandidateAdditionalInfos()
            .id(2L)
            .actualSalary(2)
            .expectedSalary(2)
            .location("location2")
            .mobile("mobile2")
            .disponibility("disponibility2");
    }

    public static CandidateAdditionalInfos getCandidateAdditionalInfosRandomSampleGenerator() {
        return new CandidateAdditionalInfos()
            .id(longCount.incrementAndGet())
            .actualSalary(intCount.incrementAndGet())
            .expectedSalary(intCount.incrementAndGet())
            .location(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString())
            .disponibility(UUID.randomUUID().toString());
    }
}
