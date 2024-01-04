package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysTenantPackageBo;
import vn.udn.dut.cinema.system.domain.vo.SysTenantPackageVo;

import java.util.Collection;
import java.util.List;

/**
 * Tenant package service interface
 *
 * @author HoaLD
 */
public interface ISysTenantPackageService {

    /**
     * Query tenant packages
     */
    SysTenantPackageVo queryById(Long packageId);

    /**
     * Query the list of tenant packages
     */
    TableDataInfo<SysTenantPackageVo> queryPageList(SysTenantPackageBo bo, PageQuery pageQuery);

    /**
     * Query the enabled list of tenant packages
     */
    List<SysTenantPackageVo> selectList();

    /**
     * Query the list of tenant packages
     */
    List<SysTenantPackageVo> queryList(SysTenantPackageBo bo);

    /**
     * New Tenant Package
     */
    Boolean insertByBo(SysTenantPackageBo bo);

    /**
     * Modify Tenant Package
     */
    Boolean updateByBo(SysTenantPackageBo bo);

    /**
     * Modify package status
     */
    int updatePackageStatus(SysTenantPackageBo bo);

    /**
     * Verify and delete tenant package information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
