package vn.udn.dut.cinema.system.service.impl;

import org.springframework.stereotype.Service;

import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.common.sensitive.core.SensitiveService;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;

/**
 * desensitization service
 * Default administrator does not filter
 * It needs to be rewritten and implemented according to the business
 *
 * @author HoaLD
 * @version 3.6.0
 */
@Service
public class SysSensitiveServiceImpl implements SensitiveService {

    /**
     * Whether to desensitize
     */
    @Override
    public boolean isSensitive() {
        if (TenantHelper.isEnable()) {
            return !LoginHelper.isSuperAdmin() || !LoginHelper.isTenantAdmin();
        }
        return !LoginHelper.isSuperAdmin();
    }

}
