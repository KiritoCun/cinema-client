package vn.udn.dut.cinema.common.excel.annotation;

import java.lang.annotation.*;

import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * dictionary formatting
 *
 * @author HoaLD
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelDictFormat {

    /**
     * If it is a dictionary type, please set the type value of the dictionary (eg: sys_user_sex)
     */
    String dictType() default "";

    /**
     * Read content to expression (eg: 0=male, 1=female, 2=unknown)
     */
    String readConverterExp() default "";

    /**
     * Separator, read string group content
     */
    String separator() default StringUtils.SEPARATOR;

}
