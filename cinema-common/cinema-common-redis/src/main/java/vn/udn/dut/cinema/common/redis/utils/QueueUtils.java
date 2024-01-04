package vn.udn.dut.cinema.common.redis.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;

import org.redisson.api.*;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Distributed queue tool
 * Lightweight queue Heavyweight data volume Please use MQ
 * Redis 5.X or above is required
 *
 * @author HoaLD
 * @version 3.6.0 Add
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueueUtils {

    private static final RedissonClient CLIENT = SpringUtils.getBean(RedissonClient.class);


    /**
     * Get a client instance
     */
    public static RedissonClient getClient() {
        return CLIENT;
    }

    /**
     * Add common queue data
     *
     * @param queueName queue name
     * @param data      data
     */
    public static <T> boolean addQueueObject(String queueName, T data) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.offer(data);
    }

    /**
     * Commonly get a queue data and return null if there is no data (delay queue is not supported)
     *
     * @param queueName queue name
     */
    public static <T> T getQueueObject(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.poll();
    }

    /**
     * General delete queue data (delay queue is not supported)
     */
    public static <T> boolean removeQueueObject(String queueName, T data) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.remove(data);
    }

    /**
     * General destruction queue All blocking listeners report errors (delay queues are not supported)
     */
    public static <T> boolean destroyQueue(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        return queue.delete();
    }

    /**
     * Add delay queue data default milliseconds
     *
     * @param queueName queue name
     * @param data      data
     * @param time      delay
     */
    public static <T> void addDelayedQueueObject(String queueName, T data, long time) {
        addDelayedQueueObject(queueName, data, time, TimeUnit.MILLISECONDS);
    }

    /**
     * Add delay queue data
     *
     * @param queueName queue name
     * @param data      data
     * @param time      delay
     * @param timeUnit  unit
     */
    public static <T> void addDelayedQueueObject(String queueName, T data, long time, TimeUnit timeUnit) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        delayedQueue.offer(data, time, timeUnit);
    }

    /**
     * Get a delayed queue data, return null if there is no data
     *
     * @param queueName queue name
     */
    public static <T> T getDelayedQueueObject(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        return delayedQueue.poll();
    }

    /**
     * Delete delayed queue data
     */
    public static <T> boolean removeDelayedQueueObject(String queueName, T data) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        return delayedQueue.remove(data);
    }

    /**
     * Destroy the delay queue All blocked listeners Report an error
     */
    public static <T> void destroyDelayedQueue(String queueName) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = CLIENT.getDelayedQueue(queue);
        delayedQueue.destroy();
    }

    /**
     * Add priority queue data
     *
     * @param queueName queue name
     * @param data      data
     */
    public static <T> boolean addPriorityQueueObject(String queueName, T data) {
        RPriorityBlockingQueue<T> priorityBlockingQueue = CLIENT.getPriorityBlockingQueue(queueName);
        return priorityBlockingQueue.offer(data);
    }

    /**
     * Try setting the bounded queue capacity to limit the number of
     *
     * @param queueName queue name
     * @param capacity  capacity
     */
    public static <T> boolean trySetBoundedQueueCapacity(String queueName, int capacity) {
        RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
        return boundedBlockingQueue.trySetCapacity(capacity);
    }

    /**
     * Try setting the bounded queue capacity to limit the number of
     *
     * @param queueName queue name
     * @param capacity  capacity
     * @param destroy   Existing whether to destroy
     */
    public static <T> boolean trySetBoundedQueueCapacity(String queueName, int capacity, boolean destroy) {
        RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
        if (boundedBlockingQueue.isExists() && destroy) {
            destroyQueue(queueName);
        }
        return boundedBlockingQueue.trySetCapacity(capacity);
    }

    /**
     * Add bounded queue data
     *
     * @param queueName queue name
     * @param data      data
     * @return added successfully true limit reached false
     */
    public static <T> boolean addBoundedQueueObject(String queueName, T data) {
        RBoundedBlockingQueue<T> boundedBlockingQueue = CLIENT.getBoundedBlockingQueue(queueName);
        return boundedBlockingQueue.offer(data);
    }

    /**
     * Subscribe to blocking queues (subscribe to all implementation classes such as: delayed priority bounded etc.)
     */
    public static <T> void subscribeBlockingQueue(String queueName, Consumer<T> consumer) {
        RBlockingQueue<T> queue = CLIENT.getBlockingQueue(queueName);
        queue.subscribeOnElements(consumer);
    }

}
