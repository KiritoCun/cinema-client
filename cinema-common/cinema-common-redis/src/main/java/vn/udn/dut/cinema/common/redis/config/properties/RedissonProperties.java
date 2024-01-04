package vn.udn.dut.cinema.common.redis.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson configuration properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    /**
     * redis cache key prefix
     */
    private String keyPrefix;

    /**
     * The number of thread pools, the default value = the number of current processing cores * 2
     */
    private int threads;

    /**
     * The number of Netty thread pools, the default value = the number of current processing cores * 2
     */
    private int nettyThreads;

    /**
     * Stand-alone service configuration
     */
    private SingleServerConfig singleServerConfig;

    /**
     * Cluster service configuration
     */
    private ClusterServersConfig clusterServersConfig;

    @Data
    @NoArgsConstructor
    public static class SingleServerConfig {

        /**
         * client name
         */
        private String clientName;

        /**
         * Minimum number of idle connections
         */
        private int connectionMinimumIdleSize;

        /**
         * connection pool size
         */
        private int connectionPoolSize;

        /**
         * Connection idle timeout, unit: milliseconds
         */
        private int idleConnectionTimeout;

        /**
         * Command waiting timeout, unit: milliseconds
         */
        private int timeout;

        /**
         * Publish and subscribe connection pool size
         */
        private int subscriptionConnectionPoolSize;

    }

    @Data
    @NoArgsConstructor
    public static class ClusterServersConfig {

        /**
         * client name
         */
        private String clientName;

        /**
         * master minimum number of idle connections
         */
        private int masterConnectionMinimumIdleSize;

        /**
         * master connection pool size
         */
        private int masterConnectionPoolSize;

        /**
         * slave minimum number of idle connections
         */
        private int slaveConnectionMinimumIdleSize;

        /**
         * slave connection pool size
         */
        private int slaveConnectionPoolSize;

        /**
         * Connection idle timeout, unit: milliseconds
         */
        private int idleConnectionTimeout;

        /**
         * Command waiting timeout, unit: milliseconds
         */
        private int timeout;

        /**
         * Publish and subscribe connection pool size
         */
        private int subscriptionConnectionPoolSize;

        /**
         * read mode
         */
        private ReadMode readMode;

        /**
         * subscription model
         */
        private SubscriptionMode subscriptionMode;

    }

}
