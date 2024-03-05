package atig.recruiting.beta.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmailTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Email getEmailSample1() {
        return new Email()
            .id(1L)
            .from("from1")
            .recipients("recipients1")
            .subject("subject1")
            .text("text1")
            .folder("folder1")
            .signatureText("signatureText1");
    }

    public static Email getEmailSample2() {
        return new Email()
            .id(2L)
            .from("from2")
            .recipients("recipients2")
            .subject("subject2")
            .text("text2")
            .folder("folder2")
            .signatureText("signatureText2");
    }

    public static Email getEmailRandomSampleGenerator() {
        return new Email()
            .id(longCount.incrementAndGet())
            .from(UUID.randomUUID().toString())
            .recipients(UUID.randomUUID().toString())
            .subject(UUID.randomUUID().toString())
            .text(UUID.randomUUID().toString())
            .folder(UUID.randomUUID().toString())
            .signatureText(UUID.randomUUID().toString());
    }
}
