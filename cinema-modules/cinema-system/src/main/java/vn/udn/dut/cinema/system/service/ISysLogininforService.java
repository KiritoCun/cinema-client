package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysLogininforBo;
import vn.udn.dut.cinema.system.domain.vo.SysLogininforVo;

import java.util.List;

/**
 * System access log status information Service layer
 *
 * @author HoaLD
 */
public interface ISysLogininforService {


    TableDataInfo<SysLogininforVo> selectPageLogininforList(SysLogininforBo logininfor, PageQuery pageQuery);

    /**
     * Added system login log
     *
     * @param bo access log object
     */
    void insertLogininfor(SysLogininforBo bo);

    /**
     * Query the collection of system login logs
     *
     * @param logininfor access log object
     * @return Login record collection
     */
    List<SysLogininforVo> selectLogininforList(SysLogininforBo logininfor);

    /**
     * Delete system login logs in batches
     *
     * @param infoIds Login log ID to be deleted
     * @return result
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * Clear the system login log
     */
    void cleanLogininfor();
}
