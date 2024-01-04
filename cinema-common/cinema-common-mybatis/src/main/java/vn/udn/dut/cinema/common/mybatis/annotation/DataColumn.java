package vn.udn.dut.cinema.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * data permission
 *
 * An annotation can only correspond to one template
 *
 * @author HoaLD
 * @version 3.5.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataColumn {

    /**
     * placeholder keywords
     */
    String[] key() default "deptName";

    /**
     * placeholder replacement value
     */
    String[] value() default "dept_id";

}
