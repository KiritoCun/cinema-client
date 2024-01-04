package vn.udn.dut.cinema.common.ratelimiter.enums;

/**
 * Current limiting type
 *
 * @author HoaLD
 */

public enum LimitType {
    /**
     * Default policy global current limiting
     */
    DEFAULT,

    /**
     * Limit traffic based on requester IP
     */
    IP,

    /**
     * Instance current limiting (cluster multi-backend instance)
     */
    CLUSTER
}
