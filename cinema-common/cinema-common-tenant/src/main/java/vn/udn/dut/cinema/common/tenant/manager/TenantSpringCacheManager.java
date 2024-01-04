package vn.udn.dut.cinema.common.tenant.manager;

import org.springframework.cache.Cache;

import vn.udn.dut.cinema.common.core.constant.GlobalConstants;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.redis.manager.PlusSpringCacheManager;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;

/**
 * Rewrite the cacheName processing method to support multi-tenancy
 *
 * @author HoaLD
 */
public class TenantSpringCacheManager extends PlusSpringCacheManager {

    public TenantSpringCacheManager() {
    }

    @Override
    public Cache getCache(String name) {
        if (StringUtils.contains(name, GlobalConstants.GLOBAL_REDIS_KEY)) {
            return super.getCache(name);
        }
        String tenantId = TenantHelper.getTenantId();
        if (StringUtils.startsWith(name, tenantId)) {
            // If it exists, return it directly
            return super.getCache(name);
        }
        return super.getCache(tenantId + ":" + name);
    }

}
