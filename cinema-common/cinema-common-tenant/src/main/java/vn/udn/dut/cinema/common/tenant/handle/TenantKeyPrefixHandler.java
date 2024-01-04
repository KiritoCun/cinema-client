package vn.udn.dut.cinema.common.tenant.handle;

import vn.udn.dut.cinema.common.core.constant.GlobalConstants;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.redis.handler.KeyPrefixHandler;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;

/**
 * Multi-tenant redis cache key prefix processing
 *
 * @author HoaLD
 */
public class TenantKeyPrefixHandler extends KeyPrefixHandler {

    public TenantKeyPrefixHandler(String keyPrefix) {
        super(keyPrefix);
    }

    /**
     * add prefix
     */
    @Override
    public String map(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (StringUtils.contains(name, GlobalConstants.GLOBAL_REDIS_KEY)) {
            return super.map(name);
        }
        String tenantId = TenantHelper.getTenantId();
        if (StringUtils.startsWith(name, tenantId)) {
            // If it exists, return it directly
            return super.map(name);
        }
        return super.map(tenantId + ":" + name);
    }

    /**
     * remove prefix
     */
    @Override
    public String unmap(String name) {
        String unmap = super.unmap(name);
        if (StringUtils.isBlank(unmap)) {
            return null;
        }
        if (StringUtils.contains(name, GlobalConstants.GLOBAL_REDIS_KEY)) {
            return super.unmap(name);
        }
        String tenantId = TenantHelper.getTenantId();
        if (StringUtils.startsWith(unmap, tenantId)) {
            // remove if exists
            return unmap.substring((tenantId + ":").length());
        }
        return unmap;
    }

}
