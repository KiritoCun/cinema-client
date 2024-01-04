package vn.udn.dut.cinema.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import io.github.linpeilie.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Mapstruct tool class
 * <p>Reference document: <a href="https://mapstruct.plus/introduction/quick-start.html">mapstruct-plus</a></p>
 *
 *
 * @author HoaLD
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapstructUtils {

    private final static Converter CONVERTER = SpringUtils.getBean(Converter.class);

    /**
     * Convert an object of type T to an object of type desc and return
     *
     * @param source data source entity
     * @param desc   Describe object Converted object
     * @return desc
     */
    public static <T, V> V convert(T source, Class<V> desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        if (ObjectUtil.isNull(desc)) {
            return null;
        }
        return CONVERTER.convert(source, desc);
    }

    /**
     * Assign the object of type T to the object of type desc according to the configured mapping field rules and return the object of desc
     *
     * @param source data source entity
     * @param desc   converted object
     * @return desc
     */
    public static <T, V> V convert(T source, V desc) {
        if (ObjectUtil.isNull(source)) {
            return null;
        }
        if (ObjectUtil.isNull(desc)) {
            return null;
        }
        return CONVERTER.convert(source, desc);
    }

    /**
     * Convert a collection of type T to a collection of type desc and return
     *
     * @param sourceList Data Source Entity List
     * @param desc       Describe object Converted object
     * @return desc
     */
    public static <T, V> List<V> convert(List<T> sourceList, Class<V> desc) {
        if (ObjectUtil.isNull(sourceList)) {
            return null;
        }
        if (CollUtil.isEmpty(sourceList)) {
            return CollUtil.newArrayList();
        }
        return CONVERTER.convert(sourceList, desc);
    }

    /**
     * Converts the Map to a collection of type beanClass and returns
     *
     * @param map       Data Sources
     * @param beanClass bean class
     * @return bean object
     */
    public static <T> T convert(Map<String, Object> map, Class<T> beanClass) {
        if (MapUtil.isEmpty(map)) {
            return null;
        }
        if (ObjectUtil.isNull(beanClass)) {
            return null;
        }
        return CONVERTER.convert(map, beanClass);
    }

}
