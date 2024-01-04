package vn.udn.dut.cinema.common.satoken.core.dao;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.util.SaFoxUtil;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sa-Token persistence layer interface (using RedisUtils that comes with the framework to achieve protocol unification)
 *
 * @author HoaLD
 */
public class PlusSaTokenDao implements SaTokenDao {

    /**
     * Get Value, if there is no return empty
     */
    @Override
    public String get(String key) {
        return RedisUtils.getCacheObject(key);
    }

    /**
     * Write Value and set the survival time (unit: second)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= NOT_VALUE_EXPIRE) {
            return;
        }
        // Determine whether it is never expired
        if (timeout == NEVER_EXPIRE) {
            RedisUtils.setCacheObject(key, value);
        } else {
            RedisUtils.setCacheObject(key, value, Duration.ofSeconds(timeout));
        }
    }

    /**
     * Modify the specified key-value key-value pair (the expiration time remains unchanged)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = no such key
        if (expire == NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * Delete Value
     */
    @Override
    public void delete(String key) {
        RedisUtils.deleteObject(key);
    }

    /**
     * Get the remaining survival time of Value (unit: second)
     */
    @Override
    public long getTimeout(String key) {
        long timeout = RedisUtils.getTimeToLive(key);
        return timeout < 0 ? timeout : timeout / 1000;
    }

    /**
     * Modify the remaining survival time of Value (unit: second)
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        // Decide if you want to set it permanently
        if (timeout == NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire == NEVER_EXPIRE) {
                // If it is already set to permanent, do nothing
            } else {
                // If it has not been set permanently, then set it again
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        RedisUtils.expire(key, Duration.ofSeconds(timeout));
    }


    /**
     * Get Object, if there is no return empty
     */
    @Override
    public Object getObject(String key) {
        return RedisUtils.getCacheObject(key);
    }

    /**
     * Write Object, and set survival time (unit: second)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        if (timeout == 0 || timeout <= NOT_VALUE_EXPIRE) {
            return;
        }
        // Determine whether it is never expired
        if (timeout == NEVER_EXPIRE) {
            RedisUtils.setCacheObject(key, object);
        } else {
            RedisUtils.setCacheObject(key, object, Duration.ofSeconds(timeout));
        }
    }

    /**
     * Update Object (expiration time remains the same)
     */
    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        // -2 = no such key
        if (expire == NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, object, expire);
    }

    /**
     * Delete Object
     */
    @Override
    public void deleteObject(String key) {
        RedisUtils.deleteObject(key);
    }

    /**
     * Get the remaining survival time of the Object (unit: second)
     */
    @Override
    public long getObjectTimeout(String key) {
        long timeout = RedisUtils.getTimeToLive(key);
        return timeout < 0 ? timeout : timeout / 1000;
    }

    /**
     * Modify the remaining survival time of the Object (unit: second)
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // Decide if you want to set it permanently
        if (timeout == NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == NEVER_EXPIRE) {
                // If it is already set to permanent, do nothing
            } else {
                // If it has not been set permanently, then set it again
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        RedisUtils.expire(key, Duration.ofSeconds(timeout));
    }


    /**
     * search data
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        Collection<String> keys = RedisUtils.keys(prefix + "*" + keyword + "*");
        List<String> list = new ArrayList<>(keys);
        return SaFoxUtil.searchList(list, start, size, sortType);
    }
}
