package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmailcredentialsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Emailcredentials getEmailcredentialsSample1() {
        return new Emailcredentials().id(1L).username("username1").password("password1");
    }

    public static Emailcredentials getEmailcredentialsSample2() {
        return new Emailcredentials().id(2L).username("username2").password("password2");
    }

    public static Emailcredentials getEmailcredentialsRandomSampleGenerator() {
        return new Emailcredentials()
            .id(longCount.incrementAndGet())
            .username(UUID.randomUUID().toString())
            .password(UUID.randomUUID().toString());
    }
}
