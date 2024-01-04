package vn.udn.dut.cinema.common.translation.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import vn.udn.dut.cinema.common.translation.core.handler.TranslationHandler;

import java.lang.annotation.*;

/**
 * General Translation Notes
 *
 * @author HoaLD
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = TranslationHandler.class)
public @interface Translation {

    /**
     * Type (need to correspond to the {@link TranslationType} annotation type on the implementation class)
     * <p>
     * By default, the value of the current field is taken. If @{@link Translation#mapper()} is set, the value of the mapped field is taken
     */
    String type();

    /**
     * Mapping field (take the value of this field if not empty)
     */
    String mapper() default "";

    /**
     * Other conditions For example: dictionary type(sys_user_sex)
     */
    String other() default "";

}
