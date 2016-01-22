package pl.mkoi.generator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by DominikD on 2016-01-22.
 */
//TODO generator powinien cacheowac kolejne wyniki np. w pliku, skad pozniej beda pobierane
// dane przy ustanawianiu bezpiecznego polaczenia i jego aktualizacji
public class DomainParametersGenerator {
    private static final int THREAD_POOL_SIZE = 4;
    private static final int RATE = 15;
    private final ScheduledExecutorService scheduler;

    public DomainParametersGenerator() {
        scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
        ;
    }

    public void runGenerator() {
        scheduler.scheduleWithFixedDelay(new DomainParametersJob(), 0, 15, TimeUnit.SECONDS);
    }
}
