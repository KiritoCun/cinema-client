package vn.udn.dut.cinema.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * Data permission group
 *
 * @author HoaLD
 * @version 3.5.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    DataColumn[] value();

}
