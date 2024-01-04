package vn.udn.dut.cinema.common.redis.config;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.redis.config.properties.RedissonProperties;
import vn.udn.dut.cinema.common.redis.handler.KeyPrefixHandler;
import vn.udn.dut.cinema.common.redis.manager.PlusSpringCacheManager;

import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * redis configuration
 *
 * @author HoaLD
 */
@Slf4j
@AutoConfiguration
@EnableCaching
@EnableConfigurationProperties(RedissonProperties.class)
public class RedisConfig {

    @Autowired
    private RedissonProperties redissonProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RedissonAutoConfigurationCustomizer redissonCustomizer() {
        return config -> {
            config.setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setCodec(new JsonJacksonCodec(objectMapper));
            RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
            if (ObjectUtil.isNotNull(singleServerConfig)) {
                // Use stand-alone mode
                config.useSingleServer()
                    // Set redis key prefix
                    .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                    .setTimeout(singleServerConfig.getTimeout())
                    .setClientName(singleServerConfig.getClientName())
                    .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                    .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                    .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
            }
            // Cluster configuration method refer to the note below
            RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
            if (ObjectUtil.isNotNull(clusterServersConfig)) {
                config.useClusterServers()
                    // Set redis key prefix
                    .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                    .setTimeout(clusterServersConfig.getTimeout())
                    .setClientName(clusterServersConfig.getClientName())
                    .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
                    .setReadMode(clusterServersConfig.getReadMode())
                    .setSubscriptionMode(clusterServersConfig.getSubscriptionMode());
            }
            log.info("Initialize redis configuration");
        };
    }

    /**
     * Custom Cache Manager Integrate spring-cache
     */
    @Bean
    public CacheManager cacheManager() {
        return new PlusSpringCacheManager();
    }

    /**
     * redis cluster configuration yml
     *
     * --- # redis Cluster configuration (stand-alone and cluster can only open one and the other needs to be commented out)
     * spring:
     *   redis:
     *     cluster:
     *       nodes:
     *         - 192.168.0.100:6379
     *         - 192.168.0.101:6379
     *         - 192.168.0.102:6379
     *     # password
     *     password:
     *     # connection timeout
     *     timeout: 10s
     *     # Whether to enable ssl
     *     ssl: false
     *
     * redisson:
     *   # Number of thread pools
     *   threads: 16
     *   # Number of Netty thread pools
     *   nettyThreads: 32
     *   # cluster configuration
     *   clusterServersConfig:
     *     # client name
     *     clientName: ${cinema.name}
     *     # master minimum number of idle connections
     *     masterConnectionMinimumIdleSize: 32
     *     # master connection pool size
     *     masterConnectionPoolSize: 64
     *     # slave minimum number of idle connections
     *     slaveConnectionMinimumIdleSize: 32
     *     # slave connection pool size
     *     slaveConnectionPoolSize: 64
     *     # Connection idle timeout, unit: milliseconds
     *     idleConnectionTimeout: 10000
     *     # Command waiting timeout, unit: milliseconds
     *     timeout: 3000
     *     # Publish and subscribe connection pool size
     *     subscriptionConnectionPoolSize: 50
     *     # read mode
     *     readMode: "SLAVE"
     *     # subscription model
     *     subscriptionMode: "MASTER"
     */

}
