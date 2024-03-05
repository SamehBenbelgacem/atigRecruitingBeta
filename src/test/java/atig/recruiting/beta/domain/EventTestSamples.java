package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EventTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Event getEventSample1() {
        return new Event().id(1L).title("title1").externalParticipants("externalParticipants1").description("description1");
    }

    public static Event getEventSample2() {
        return new Event().id(2L).title("title2").externalParticipants("externalParticipants2").description("description2");
    }

    public static Event getEventRandomSampleGenerator() {
        return new Event()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .externalParticipants(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
