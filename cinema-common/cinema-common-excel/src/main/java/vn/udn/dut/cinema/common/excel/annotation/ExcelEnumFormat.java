package vn.udn.dut.cinema.common.excel.annotation;

import java.lang.annotation.*;

/**
 * enum formatting
 *
 * @author HoaLD
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelEnumFormat {

    /**
     * Dictionary enumeration type
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * The corresponding code attribute name in the dictionary enumeration class, the default is code
     */
    String codeField() default "code";

    /**
     * The corresponding text attribute name in the dictionary enumeration class, the default is text
     */
    String textField() default "text";

}
