package vn.udn.dut.cinema.common.ratelimiter.annotation;

import java.lang.annotation.*;

import vn.udn.dut.cinema.common.ratelimiter.enums.LimitType;

/**
 * Current limit annotation
 *
 * @author HoaLD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * Current-limiting key, supports the use of Spring el expressions to dynamically obtain parameter values ​​​​on methods
     * The format is something like #code.id #{#code}
     */
    String key() default "";

    /**
     * Current limit time, in seconds
     */
    int time() default 60;

    /**
     * Current limit times
     */
    int count() default 100;

    /**
     * Current limiting type
     */
    LimitType limitType() default LimitType.DEFAULT;

    /**
     * The prompt message supports internationalization and the format is {code}
     */
    String message() default "{rate.limiter.message}";
}
