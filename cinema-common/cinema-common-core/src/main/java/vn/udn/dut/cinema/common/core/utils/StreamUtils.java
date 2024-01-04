package vn.udn.dut.cinema.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * stream stream tool class
 *
 * @author HoaLD
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

    /**
     * Filter the collection
     *
     * @param collection collection to convert
     * @param function   filter method
     * @return filtered list
     */
    public static <E> List<E> filter(Collection<E> collection, Predicate<E> function) {
        if (CollUtil.isEmpty(collection)) {
            return CollUtil.newArrayList();
        }
        // Be careful not to use the new syntax of .toList() here, because returning an immutable List will cause serialization problems
        return collection.stream().filter(function).collect(Collectors.toList());
    }

    /**
     * splicing the collection
     *
     * @param collection collection to convert
     * @param function   splicing method
     * @return spliced ​​list
     */
    public static <E> String join(Collection<E> collection, Function<E, String> function) {
        return join(collection, function, StringUtils.SEPARATOR);
    }

    /**
     * splicing the collection
     *
     * @param collection collection to convert
     * @param function   splicing method
     * @param delimiter  splicing character
     * @return spliced ​​list
     */
    public static <E> String join(Collection<E> collection, Function<E, String> function, CharSequence delimiter) {
        if (CollUtil.isEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        return collection.stream().map(function).filter(Objects::nonNull).collect(Collectors.joining(delimiter));
    }

    /**
     * Sort the collection
     *
     * @param collection collection to convert
     * @param comparing  sorting method
     * @return sorted list
     */
    public static <E> List<E> sorted(Collection<E> collection, Comparator<E> comparing) {
        if (CollUtil.isEmpty(collection)) {
            return CollUtil.newArrayList();
        }
        // Be careful not to use the new syntax of .toList() here, because returning an immutable List will cause serialization problems
        return collection.stream().sorted(comparing).collect(Collectors.toList());
    }

    /**
     * Convert the collection to a map with the same type<br>
     * <B>{@code Collection<V>  ---->  Map<K,V>}</B>
     *
     * @param collection collection to convert
     * @param key        The lambda method that converts V type to K type
     * @param <V>        Generics in collections
     * @param <K>        The key type in the map
     * @return converted map
     */
    public static <V, K> Map<K, V> toIdentityMap(Collection<V> collection, Function<V, K> key) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection.stream().collect(Collectors.toMap(key, Function.identity(), (l, r) -> l));
    }

    /**
     * Convert Collection to map (the value type is different from the generic type of collection)<br>
     * <B>{@code Collection<E> -----> Map<K,V>  }</B>
     *
     * @param collection collection to convert
     * @param key        The lambda method that converts E type to K type
     * @param value      A lambda method that converts type E to type V
     * @param <E>        Generics in collections
     * @param <K>        The key type in the map
     * @param <V>        The value type in the map
     * @return converted map
     */
    public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Function<E, K> key, Function<E, V> value) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection.stream().collect(Collectors.toMap(key, value, (l, r) -> l));
    }

    /**
     * Classify collections into maps according to rules (such as having the same class id)<br>
     * <B>{@code Collection<E> -------> Map<K,List<E>> } </B>
     *
     * @param collection Collections that need to be sorted
     * @param key        classification rules
     * @param <E>        Generics in collections
     * @param <K>        The key type in the map
     * @return Classified map
     */
    public static <E, K> Map<K, List<E>> groupByKey(Collection<E> collection, Function<E, K> key) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection
            .stream()
            .collect(Collectors.groupingBy(key, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * Classify the collection into a two-layer map according to two rules (such as having the same grade id, class id)<br>
     * <B>{@code Collection<E>  --->  Map<T,Map<U,List<E>>> } </B>
     *
     * @param collection Collections that need to be sorted
     * @param key1       The first classification rule
     * @param key2       Rules for the second category
     * @param <E>        collection element type
     * @param <K>        The key type in the first map
     * @param <U>        The key type in the second map
     * @return Classified map
     */
    public static <E, K, U> Map<K, Map<U, List<E>>> groupBy2Key(Collection<E> collection, Function<E, K> key1, Function<E, U> key2) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection
            .stream()
            .collect(Collectors.groupingBy(key1, LinkedHashMap::new, Collectors.groupingBy(key2, LinkedHashMap::new, Collectors.toList())));
    }

    /**
     * Classify the collection into a two-layer map according to two rules (such as having the same grade id, class id)<br>
     * <B>{@code Collection<E>  --->  Map<T,Map<U,E>> } </B>
     *
     * @param collection Collections that need to be sorted
     * @param key1       The first classification rule
     * @param key2       Rules for the second category
     * @param <T>        The key type in the first map
     * @param <U>        The key type in the second map
     * @param <E>        Generics in collections
     * @return Classified map
     */
    public static <E, T, U> Map<T, Map<U, E>> group2Map(Collection<E> collection, Function<E, T> key1, Function<E, U> key2) {
        if (CollUtil.isEmpty(collection) || key1 == null || key2 == null) {
            return MapUtil.newHashMap();
        }
        return collection
            .stream()
            .collect(Collectors.groupingBy(key1, LinkedHashMap::new, Collectors.toMap(key2, Function.identity(), (l, r) -> l)));
    }

    /**
     * Convert collection to List collection, but the generic types of the two are different<br>
     * <B>{@code Collection<E>  ------>  List<T> } </B>
     *
     * @param collection collection to convert
     * @param function   The generic type in the collection is converted into a lambda expression of the list generic type
     * @param <E>        Generics in collections
     * @param <T>        Generics in List
     * @return converted list
     */
    public static <E, T> List<T> toList(Collection<E> collection, Function<E, T> function) {
        if (CollUtil.isEmpty(collection)) {
            return CollUtil.newArrayList();
        }
        return collection
            .stream()
            .map(function)
            .filter(Objects::nonNull)
            // Be careful not to use the new syntax of .toList() here, because returning an immutable List will cause serialization problems
            .collect(Collectors.toList());
    }

    /**
     * Convert collection to Set collection, but the generic types of the two are different<br>
     * <B>{@code Collection<E>  ------>  Set<T> } </B>
     *
     * @param collection collection to convert
     * @param function   The generic type in the collection is transformed into a lambda expression of the set generic type
     * @param <E>        Generics in collections
     * @param <T>        Generics in Set
     * @return Converted Set
     */
    public static <E, T> Set<T> toSet(Collection<E> collection, Function<E, T> function) {
        if (CollUtil.isEmpty(collection) || function == null) {
            return CollUtil.newHashSet();
        }
        return collection
            .stream()
            .map(function)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }


    /**
     * Merge two maps of the same key type
     *
     * @param map1  The first map to be merged
     * @param map2  The second map that needs to be merged
     * @param merge The merged lambda merges the key value1 value2 into the final type, pay attention to the case that the value may be empty
     * @param <K>   The key type in the map
     * @param <X>   The value type of the first map
     * @param <Y>   The value type of the second map
     * @param <V>   The value type of the final map
     * @return The merged map
     */
    public static <K, X, Y, V> Map<K, V> merge(Map<K, X> map1, Map<K, Y> map2, BiFunction<X, Y, V> merge) {
        if (MapUtil.isEmpty(map1) && MapUtil.isEmpty(map2)) {
            return MapUtil.newHashMap();
        } else if (MapUtil.isEmpty(map1)) {
            map1 = MapUtil.newHashMap();
        } else if (MapUtil.isEmpty(map2)) {
            map2 = MapUtil.newHashMap();
        }
        Set<K> key = new HashSet<>();
        key.addAll(map1.keySet());
        key.addAll(map2.keySet());
        Map<K, V> map = new HashMap<>();
        for (K t : key) {
            X x = map1.get(t);
            Y y = map2.get(t);
            V z = merge.apply(x, y);
            if (z != null) {
                map.put(t, z);
            }
        }
        return map;
    }

}
