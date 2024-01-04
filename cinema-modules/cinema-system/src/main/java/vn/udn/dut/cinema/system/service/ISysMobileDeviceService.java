package vn.udn.dut.cinema.system.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysMobileDeviceBo;
import vn.udn.dut.cinema.system.domain.vo.SysMobileDeviceVo;

/**
 * Mobile device table service interface
 *
 * @author HoaLD
 * @date 2023-11-10
 */
public interface ISysMobileDeviceService {

    /**
     * Query Mobile device table
     */
    SysMobileDeviceVo queryById(Long id);

    /**
     * Query Mobile device table list
     */
    TableDataInfo<SysMobileDeviceVo> queryPageList(SysMobileDeviceBo bo, PageQuery pageQuery);

    /**
     * Query Mobile device table list
     */
    List<SysMobileDeviceVo> queryList(SysMobileDeviceBo bo);

    /**
     * Add Mobile device table
     */
    Boolean insertByBo(SysMobileDeviceBo bo);

    /**
     * Edit Mobile device table
     */
    Boolean updateByBo(SysMobileDeviceBo bo);

    /**
     * Verify and delete Mobile device table information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
