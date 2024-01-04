package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysTenantBo;
import vn.udn.dut.cinema.system.domain.vo.SysTenantVo;

import java.util.Collection;
import java.util.List;

/**
 * Tenant Service interface
 *
 * @author HoaLD
 */
public interface ISysTenantService {

    /**
     * query tenant
     */
    SysTenantVo queryById(Long id);

    /**
     * Query tenants based on tenant ID
     */
    SysTenantVo queryByTenantId(String tenantId);

    /**
     * Query the list of tenants
     */
    TableDataInfo<SysTenantVo> queryPageList(SysTenantBo bo, PageQuery pageQuery);

    /**
     * Query the list of tenants
     */
    List<SysTenantVo> queryList(SysTenantBo bo);

    /**
     * New tenant
     */
    Boolean insertByBo(SysTenantBo bo);

    /**
     * modify tenant
     */
    Boolean updateByBo(SysTenantBo bo);

    /**
     * Modify tenant status
     */
    int updateTenantStatus(SysTenantBo bo);

    /**
     * Check if the tenant allows the operation
     */
    void checkTenantAllowed(String tenantId);

    /**
     * Verify and delete tenant information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * Check if the business name is unique
     */
    boolean checkCompanyNameUnique(SysTenantBo bo);

    /**
     * Check account balance
     */
    boolean checkAccountBalance(String tenantId);

    /**
     * Check validity period
     */
    boolean checkExpireTime(String tenantId);

    /**
     * Synchronize Tenant Packages
     */
    Boolean syncTenantPackage(String tenantId, String packageId);
}
