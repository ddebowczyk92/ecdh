package pl.mkoi.generator;

import pl.mkoi.ecdh.crypto.model.Polynomial;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by DominikD on 2016-01-22.
 */
public class DomainParametersGenerator {
    private static final int THREAD_POOL_SIZE = 4;
    private static final int RATE = 15;
    private final ScheduledExecutorService scheduler;

    public DomainParametersGenerator() {
        scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
        ;
    }

    public void runGenerator(int m, Polynomial a, Polynomial b) {
        scheduler.scheduleWithFixedDelay(new DomainParametersJob(m, a, b), 0, RATE, TimeUnit.SECONDS);
    }
}
