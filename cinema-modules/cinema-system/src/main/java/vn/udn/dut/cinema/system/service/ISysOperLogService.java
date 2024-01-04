package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysOperLogBo;
import vn.udn.dut.cinema.system.domain.vo.SysOperLogVo;

import java.util.List;

/**
 * Operation Log Service Layer
 *
 * @author HoaLD
 */
public interface ISysOperLogService {

    TableDataInfo<SysOperLogVo> selectPageOperLogList(SysOperLogBo operLog, PageQuery pageQuery);

    /**
     * Add operation log
     *
     * @param bo operation log object
     */
    void insertOperlog(SysOperLogBo bo);

    /**
     * Query the collection of system operation logs
     *
     * @param operLog operation log object
     * @return Operation log collection
     */
    List<SysOperLogVo> selectOperLogList(SysOperLogBo operLog);

    /**
     * Delete System Operation Logs in Batches
     *
     * @param operIds ID of the operation log to be deleted
     * @return result
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * Query operation log details
     *
     * @param operId operation ID
     * @return operation log object
     */
    SysOperLogVo selectOperLogById(Long operId);

    /**
     * clear operation log
     */
    void cleanOperLog();
}
