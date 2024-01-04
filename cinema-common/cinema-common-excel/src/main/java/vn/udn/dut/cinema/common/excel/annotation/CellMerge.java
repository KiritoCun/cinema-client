package vn.udn.dut.cinema.common.excel.annotation;

import java.lang.annotation.*;

import vn.udn.dut.cinema.common.excel.core.CellMergeStrategy;

/**
 * Excel column cell merge (merge the same items in the column)
 *
 * Need to be used with {@link CellMergeStrategy} strategy
 *
 * @author HoaLD
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CellMerge {

	/**
	 * col index
	 */
	int index() default -1;

}
