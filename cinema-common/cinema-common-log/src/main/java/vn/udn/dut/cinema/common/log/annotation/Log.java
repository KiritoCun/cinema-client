package vn.udn.dut.cinema.common.log.annotation;

import java.lang.annotation.*;

import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.log.enums.OperatorType;
import vn.udn.dut.cinema.common.log.enums.SystemType;

/**
 * Custom Action Logging Annotations
 *
 * @author HoaLD
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * module
     */
    String title() default "";

    /**
     * Function
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * operator category
     */
    OperatorType operatorType() default OperatorType.MANAGE;
    
    /**
     * System type
     * @return
     */
    SystemType systemType() default SystemType.ADMIN;

    /**
     * Whether to save the requested parameters
     */
    boolean isSaveRequestData() default true;

    /**
     * Whether to save the parameters of the response
     */
    boolean isSaveResponseData() default true;


    /**
     * Exclude specified request parameters
     */
    String[] excludeParamNames() default {};

}
