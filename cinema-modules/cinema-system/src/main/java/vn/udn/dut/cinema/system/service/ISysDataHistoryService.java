package vn.udn.dut.cinema.system.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysDataHistoryBo;
import vn.udn.dut.cinema.system.domain.vo.SysDataHistoryVo;

/**
 * System data history service interface
 *
 * @author HoaLD
 * @date 2023-08-23
 */
public interface ISysDataHistoryService {

    /**
     * Query System data history
     */
    SysDataHistoryVo queryById(Long id);

    /**
     * Query System data history list
     */
    TableDataInfo<SysDataHistoryVo> queryPageList(SysDataHistoryBo bo, PageQuery pageQuery);

    /**
     * Query System data history list
     */
    List<SysDataHistoryVo> queryList(SysDataHistoryBo bo);

    /**
     * Verify and delete System data history information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
