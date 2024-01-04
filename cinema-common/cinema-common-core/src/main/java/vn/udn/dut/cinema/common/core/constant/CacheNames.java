package vn.udn.dut.cinema.common.core.constant;

/**
 * Cache group name constant
 * <p>
 * The key format is cacheNames#ttl#maxIdleTime#maxSize
 * <p>
 * ttl expiration time If set to 0, it will not expire, the default is 0
 * maxIdleTime Maximum idle time Clean up idle data according to the LRU algorithm. If it is set to 0, it will not be detected. The default is 0.
 * maxSize The maximum length of the group is to clean up the overflow data according to the LRU algorithm. If it is set to 0, the length is infinite. The default is 0.
 * <p>
 * example: test#60s、test#0#60s、test#0#1m#1000、test#1h#0#500
 *
 * @author HoaLD
 */
public interface CacheNames {

    /**
     * Demonstration case
     */
    String DEMO_CACHE = "demo:cache#60s#10m#20";

    /**
     * System Configuration
     */
    String SYS_CONFIG = "sys_config";

    /**
     * Data Dictionary
     */
    String SYS_DICT = "sys_dict";

    /**
     * tenant
     */
    String SYS_TENANT = GlobalConstants.GLOBAL_REDIS_KEY + "sys_tenant#30d";

    /**
     * User Account
     */
    String SYS_USER_NAME = "sys_user_name#30d";

    /**
     * department
     */
    String SYS_DEPT = "sys_dept#30d";

    /**
     * OSS content
     */
    String SYS_OSS = "sys_oss#30d";

    /**
     * OSS configuration
     */
    String SYS_OSS_CONFIG = "sys_oss_config";

    /**
     * online user
     */
    String ONLINE_TOKEN = "online_tokens";

}
