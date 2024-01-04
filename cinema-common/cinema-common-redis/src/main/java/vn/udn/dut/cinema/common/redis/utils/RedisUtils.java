package vn.udn.dut.cinema.common.redis.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;

import org.redisson.api.*;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * redis tool class
 *
 * @author HoaLD
 * @version 3.1.0 Add
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisUtils {

    private static final RedissonClient CLIENT = SpringUtils.getBean(RedissonClient.class);

    /**
     * Limiting
     *
     * @param key          current limiting key
     * @param rateType     Current limiting type
     * @param rate         rate
     * @param rateInterval rate interval
     * @return -1 failed
     */
    public static long rateLimiter(String key, RateType rateType, int rate, int rateInterval) {
        RRateLimiter rateLimiter = CLIENT.getRateLimiter(key);
        rateLimiter.trySetRate(rateType, rate, rateInterval, RateIntervalUnit.SECONDS);
        if (rateLimiter.tryAcquire()) {
            return rateLimiter.availablePermits();
        } else {
            return -1L;
        }
    }

    /**
     * Get a client instance
     */
    public static RedissonClient getClient() {
        return CLIENT;
    }

    /**
     * Publish channel messages
     *
     * @param channelKey channel key
     * @param msg        send data
     * @param consumer   custom processing
     */
    public static <T> void publish(String channelKey, T msg, Consumer<T> consumer) {
        RTopic topic = CLIENT.getTopic(channelKey);
        topic.publish(msg);
        consumer.accept(msg);
    }

    public static <T> void publish(String channelKey, T msg) {
        RTopic topic = CLIENT.getTopic(channelKey);
        topic.publish(msg);
    }

    /**
     * Subscribe channel to receive messages
     *
     * @param channelKey channel key
     * @param clazz      message type
     * @param consumer   custom processing
     */
    public static <T> void subscribe(String channelKey, Class<T> clazz, Consumer<T> consumer) {
        RTopic topic = CLIENT.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    /**
     * Cache basic objects, Integer, String, entity classes, etc.
     *
     * @param key   cached key
     * @param value cached value
     */
    public static <T> void setCacheObject(final String key, final T value) {
        setCacheObject(key, value, false);
    }

    /**
     * Cache basic objects, retaining the current object TTL validity period
     *
     * @param key       cached key
     * @param value     cached value
     * @param isSaveTtl Whether to keep the TTL validity period (for example: ttl remaining 90 before set and still 90 after set)
     * @since Redis 6.X and above use setAndKeepTTL to be compatible with 5.X scheme
     */
    public static <T> void setCacheObject(final String key, final T value, final boolean isSaveTtl) {
        RBucket<T> bucket = CLIENT.getBucket(key);
        if (isSaveTtl) {
            try {
                bucket.setAndKeepTTL(value);
            } catch (Exception e) {
                long timeToLive = bucket.remainTimeToLive();
                setCacheObject(key, value, Duration.ofMillis(timeToLive));
            }
        } else {
            bucket.set(value);
        }
    }

    /**
     * Cache basic objects, Integer, String, entity classes, etc.
     *
     * @param key      cached key
     * @param value    cached value
     * @param duration time
     */
    public static <T> void setCacheObject(final String key, final T value, final Duration duration) {
        RBatch batch = CLIENT.createBatch();
        RBucketAsync<T> bucket = batch.getBucket(key);
        bucket.setAsync(value);
        bucket.expireAsync(duration);
        batch.execute();
    }

    /**
     * register object listener
     * <p>
     * The key listener needs to enable redis related configurations such as `notify-keyspace-events`
     *
     * @param key      cached key
     * @param listener Listener configuration
     */
    public static <T> void addObjectListener(final String key, final ObjectListener listener) {
        RBucket<T> result = CLIENT.getBucket(key);
        result.addListener(listener);
    }

    /**
     * set valid time
     *
     * @param key     Redis key
     * @param timeout overtime time
     * @return true=set successfully; false=set failed
     */
    public static boolean expire(final String key, final long timeout) {
        return expire(key, Duration.ofSeconds(timeout));
    }

    /**
     * set valid time
     *
     * @param key      Redis key
     * @param duration overtime time
     * @return true=set successfully; false=set failed
     */
    public static boolean expire(final String key, final Duration duration) {
        RBucket rBucket = CLIENT.getBucket(key);
        return rBucket.expire(duration);
    }

    /**
     * Get the base object of the cache.
     *
     * @param key cache key value
     * @return Cache the data corresponding to the key value
     */
    public static <T> T getCacheObject(final String key) {
        RBucket<T> rBucket = CLIENT.getBucket(key);
        return rBucket.get();
    }

    /**
     * Get key remaining survival time
     *
     * @param key cache key value
     * @return remaining survival time
     */
    public static <T> long getTimeToLive(final String key) {
        RBucket<T> rBucket = CLIENT.getBucket(key);
        return rBucket.remainTimeToLive();
    }

    /**
     * delete single object
     *
     * @param key cached key
     */
    public static boolean deleteObject(final String key) {
        return CLIENT.getBucket(key).delete();
    }

    /**
     * delete collection object
     *
     * @param collection multiple objects
     */
    public static void deleteObject(final Collection collection) {
        RBatch batch = CLIENT.createBatch();
        collection.forEach(t -> {
            batch.getBucket(t.toString()).deleteAsync();
        });
        batch.execute();
    }

    /**
     * Check if the cached object exists
     *
     * @param key cached key
     */
    public static boolean isExistsObject(final String key) {
        return CLIENT.getBucket(key).isExists();
    }

    /**
     * Cache List data
     *
     * @param key      cached key
     * @param dataList List data to be cached
     * @return cached objects
     */
    public static <T> boolean setCacheList(final String key, final List<T> dataList) {
        RList<T> rList = CLIENT.getList(key);
        return rList.addAll(dataList);
    }

    /**
     * Register List Listener
     * <p>
     * The key listener needs to enable redis related configurations such as `notify-keyspace-events`
     *
     * @param key      cached key
     * @param listener Listener configuration
     */
    public static <T> void addListListener(final String key, final ObjectListener listener) {
        RList<T> rList = CLIENT.getList(key);
        rList.addListener(listener);
    }

    /**
     * Get the cached list object
     *
     * @param key cached key
     * @return Cache the data corresponding to the key value
     */
    public static <T> List<T> getCacheList(final String key) {
        RList<T> rList = CLIENT.getList(key);
        return rList.readAll();
    }

    /**
     * Cache Set
     *
     * @param key     cache key
     * @param dataSet cached data
     * @return object for caching data
     */
    public static <T> boolean setCacheSet(final String key, final Set<T> dataSet) {
        RSet<T> rSet = CLIENT.getSet(key);
        return rSet.addAll(dataSet);
    }

    /**
     * Register Set listener
     * <p>
     * The key listener needs to enable redis related configurations such as `notify-keyspace-events`
     *
     * @param key      cached key
     * @param listener Listener configuration
     */
    public static <T> void addSetListener(final String key, final ObjectListener listener) {
        RSet<T> rSet = CLIENT.getSet(key);
        rSet.addListener(listener);
    }

    /**
     * Get cached set
     *
     * @param key cached key
     * @return set object
     */
    public static <T> Set<T> getCacheSet(final String key) {
        RSet<T> rSet = CLIENT.getSet(key);
        return rSet.readAll();
    }

    /**
     * CacheMap
     *
     * @param key     cached key
     * @param dataMap cached data
     */
    public static <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            RMap<String, T> rMap = CLIENT.getMap(key);
            rMap.putAll(dataMap);
        }
    }

    /**
     * Register Map Listener
     * <p>
     * The key listener needs to enable redis related configurations such as `notify-keyspace-events`
     *
     * @param key      cached key
     * @param listener Listener configuration
     */
    public static <T> void addMapListener(final String key, final ObjectListener listener) {
        RMap<String, T> rMap = CLIENT.getMap(key);
        rMap.addListener(listener);
    }

    /**
     * Get the cached Map
     *
     * @param key cached key
     * @return map object
     */
    public static <T> Map<String, T> getCacheMap(final String key) {
        RMap<String, T> rMap = CLIENT.getMap(key);
        return rMap.getAll(rMap.keySet());
    }

    /**
     * Obtain the key list of the cache Map
     *
     * @param key cached key
     * @return key list
     */
    public static <T> Set<String> getCacheMapKeySet(final String key) {
        RMap<String, T> rMap = CLIENT.getMap(key);
        return rMap.keySet();
    }

    /**
     * Store data in Hash
     *
     * @param key   Redis key
     * @param hKey  hash key
     * @param value value
     */
    public static <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        RMap<String, T> rMap = CLIENT.getMap(key);
        rMap.put(hKey, value);
    }

    /**
     * Get the data in the Hash
     *
     * @param key  Redis key
     * @param hKey hash key
     * @return Objects in Hash
     */
    public static <T> T getCacheMapValue(final String key, final String hKey) {
        RMap<String, T> rMap = CLIENT.getMap(key);
        return rMap.get(hKey);
    }

    /**
     * Delete the data in the Hash
     *
     * @param key  Redis key
     * @param hKey hash key
     * @return Objects in Hash
     */
    public static <T> T delCacheMapValue(final String key, final String hKey) {
        RMap<String, T> rMap = CLIENT.getMap(key);
        return rMap.remove(hKey);
    }

    /**
     * Delete the data in the Hash
     *
     * @param key   Redis key
     * @param hKeys hash key
     */
    public static <T> void delMultiCacheMapValue(final String key, final Set<String> hKeys) {
        RBatch batch = CLIENT.createBatch();
        RMapAsync<String, T> rMap = batch.getMap(key);
        for (String hKey : hKeys) {
            rMap.removeAsync(hKey);
        }
        batch.execute();
    }

    /**
     * Get data in multiple Hash
     *
     * @param key   Redis key
     * @param hKeys Hash key set
     * @return Hash object collection
     */
    public static <K, V> Map<K, V> getMultiCacheMapValue(final String key, final Set<K> hKeys) {
        RMap<K, V> rMap = CLIENT.getMap(key);
        return rMap.getAll(hKeys);
    }

    /**
     * set atomic value
     *
     * @param key   Redis key
     * @param value value
     */
    public static void setAtomicValue(String key, long value) {
        RAtomicLong atomic = CLIENT.getAtomicLong(key);
        atomic.set(value);
    }

    /**
     * get atomic value
     *
     * @param key Redis key
     * @return The current value
     */
    public static long getAtomicValue(String key) {
        RAtomicLong atomic = CLIENT.getAtomicLong(key);
        return atomic.get();
    }

    /**
     * Increment atomic value
     *
     * @param key Redis key
     * @return The current value
     */
    public static long incrAtomicValue(String key) {
        RAtomicLong atomic = CLIENT.getAtomicLong(key);
        return atomic.incrementAndGet();
    }

    /**
     * decrement atomic value
     *
     * @param key Redis key
     * @return The current value
     */
    public static long decrAtomicValue(String key) {
        RAtomicLong atomic = CLIENT.getAtomicLong(key);
        return atomic.decrementAndGet();
    }

    /**
     * Get a list of cached primitive objects
     *
     * @param pattern string prefix
     * @return object list
     */
    public static Collection<String> keys(final String pattern) {
        Stream<String> stream = CLIENT.getKeys().getKeysStreamByPattern(pattern);
        return stream.collect(Collectors.toList());
    }

    /**
     * Delete cached base object list
     *
     * @param pattern string prefix
     */
    public static void deleteKeys(final String pattern) {
        CLIENT.getKeys().deleteByPattern(pattern);
    }

    /**
     * Check if key exists in redis
     *
     * @param key key
     */
    public static Boolean hasKey(String key) {
        RKeys rKeys = CLIENT.getKeys();
        return rKeys.countExists(key) > 0;
    }
}
