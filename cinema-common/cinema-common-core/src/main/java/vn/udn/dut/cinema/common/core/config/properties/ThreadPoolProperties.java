package vn.udn.dut.cinema.common.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Thread pool configuration properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

    /**
     * Whether to enable thread pool
     */
    private boolean enabled;

    /**
     * queue max length
     */
    private int queueCapacity;

    /**
     * The idle time allowed by the thread pool maintenance thread
     */
    private int keepAliveSeconds;

}
