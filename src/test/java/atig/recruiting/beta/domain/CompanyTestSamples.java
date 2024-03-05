package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CompanyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Company getCompanySample1() {
        return new Company()
            .id(1L)
            .name("name1")
            .speciality("speciality1")
            .description("description1")
            .website("website1")
            .location("location1")
            .infoEmail("infoEmail1")
            .phone("phone1");
    }

    public static Company getCompanySample2() {
        return new Company()
            .id(2L)
            .name("name2")
            .speciality("speciality2")
            .description("description2")
            .website("website2")
            .location("location2")
            .infoEmail("infoEmail2")
            .phone("phone2");
    }

    public static Company getCompanyRandomSampleGenerator() {
        return new Company()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .speciality(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .website(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString())
            .infoEmail(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString());
    }
}
