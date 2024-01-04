package vn.udn.dut.cinema.common.core.manager;

import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.utils.Threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Make sure that the background thread can be closed when the application exits
 *
 * @author HoaLD
 */
@Slf4j
@Component
public class ShutdownManager {

    @Autowired
    @Qualifier("scheduledExecutorService")
    private ScheduledExecutorService scheduledExecutorService;

    @PreDestroy
    public void destroy() {
        shutdownAsyncManager();
    }

    /**
     * Stop executing tasks asynchronously
     */
    private void shutdownAsyncManager() {
        try {
            log.info("====Close background task thread pool====");
            Threads.shutdownAndAwaitTermination(scheduledExecutorService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
