package vn.udn.dut.cinema.common.idempotent.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Custom annotations prevent repeated form submissions
 *
 * @author HoaLD
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

    /**
     * Interval time (ms), less than this time is regarded as repeated submission
     */
    int interval() default 5000;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * The prompt message supports internationalization and the format is {code}
     */
    String message() default "{repeat.submit.message}";

}
