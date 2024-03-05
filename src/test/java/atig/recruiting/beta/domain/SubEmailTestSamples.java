package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubEmailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SubEmail getSubEmailSample1() {
        return new SubEmail().id(1L).from("from1").recipients("recipients1").text("text1").signatureText("signatureText1");
    }

    public static SubEmail getSubEmailSample2() {
        return new SubEmail().id(2L).from("from2").recipients("recipients2").text("text2").signatureText("signatureText2");
    }

    public static SubEmail getSubEmailRandomSampleGenerator() {
        return new SubEmail()
            .id(longCount.incrementAndGet())
            .from(UUID.randomUUID().toString())
            .recipients(UUID.randomUUID().toString())
            .text(UUID.randomUUID().toString())
            .signatureText(UUID.randomUUID().toString());
    }
}
