package pl.mkoi.generator;

import pl.mkoi.ecdh.crypto.model.Polynomial;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Generates domain parameters in seperate thread pools
 * <p/>
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

    /**
     * Starts generation of
     *
     * @param m degree
     * @param a a value of elliptic curve
     * @param b b value of elliptic curve
     */
    public void runGenerator(int m, Polynomial a, Polynomial b) {
        scheduler.scheduleWithFixedDelay(new DomainParametersJob(m, a, b), 0, RATE, TimeUnit.SECONDS);
    }
}
