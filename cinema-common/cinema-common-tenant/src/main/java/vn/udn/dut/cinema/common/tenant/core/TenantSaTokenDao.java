package vn.udn.dut.cinema.common.tenant.core;

import vn.udn.dut.cinema.common.core.constant.GlobalConstants;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;
import vn.udn.dut.cinema.common.satoken.core.dao.PlusSaTokenDao;

import java.time.Duration;
import java.util.List;

/**
 * SaToken authentication data persistence layer adapts to multi-tenancy
 *
 * @author HoaLD
 */
public class TenantSaTokenDao extends PlusSaTokenDao {

    @Override
    public String get(String key) {
        return super.get(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    @Override
    public void set(String key, String value, long timeout) {
        super.set(GlobalConstants.GLOBAL_REDIS_KEY + key, value, timeout);
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
        super.delete(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * Get the remaining survival time of Value (unit: second)
     */
    @Override
    public long getTimeout(String key) {
        return super.getTimeout(GlobalConstants.GLOBAL_REDIS_KEY + key);
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
        RedisUtils.expire(GlobalConstants.GLOBAL_REDIS_KEY + key, Duration.ofSeconds(timeout));
    }


    /**
     * Get Object, if there is no return empty
     */
    @Override
    public Object getObject(String key) {
        return super.getObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * Write Object, and set survival time (unit: second)
     */
    @Override
    public void setObject(String key, Object object, long timeout) {
        super.setObject(GlobalConstants.GLOBAL_REDIS_KEY + key, object, timeout);
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
        super.deleteObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * Get the remaining survival time of the Object (unit: second)
     */
    @Override
    public long getObjectTimeout(String key) {
        return super.getObjectTimeout(GlobalConstants.GLOBAL_REDIS_KEY + key);
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
        RedisUtils.expire(GlobalConstants.GLOBAL_REDIS_KEY + key, Duration.ofSeconds(timeout));
    }


    /**
     * search data
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        return super.searchData(GlobalConstants.GLOBAL_REDIS_KEY + prefix, keyword, start, size, sortType);
    }
}
