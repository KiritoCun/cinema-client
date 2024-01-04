package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysConfigBo;
import vn.udn.dut.cinema.system.domain.vo.SysConfigVo;

import java.util.List;

/**
 * Parameter Configuration Service Layer
 *
 * @author HoaLD
 */
public interface ISysConfigService {


    TableDataInfo<SysConfigVo> selectPageConfigList(SysConfigBo config, PageQuery pageQuery);

    /**
     * Query parameter configuration information
     *
     * @param configId parameter configuration ID
     * @return Parameter configuration information
     */
    SysConfigVo selectConfigById(Long configId);

    /**
     * Query parameter configuration information by key name
     *
     * @param configKey parameter key name
     * @return parameter key
     */
    String selectConfigByKey(String configKey);

    /**
     * get register switch
     * @param tenantId tenant id
     * @return true on, false off
     */
    boolean selectRegisterEnabled(String tenantId);

    /**
     * Query parameter configuration list
     *
     * @param config Parameter configuration information
     * @return Parameter configuration collection
     */
    List<SysConfigVo> selectConfigList(SysConfigBo config);

    /**
     * New parameter configuration
     *
     * @param bo Parameter configuration information
     * @return result
     */
    String insertConfig(SysConfigBo bo);

    /**
     * Modify parameter configuration
     *
     * @param bo Parameter configuration information
     * @return result
     */
    String updateConfig(SysConfigBo bo);

    /**
     * Batch delete parameter information
     *
     * @param configIds ID of the parameter to be deleted
     */
    void deleteConfigByIds(Long[] configIds);

    /**
     * Reset parameter cache data
     */
    void resetConfigCache();

    /**
     * Check whether the parameter key name is unique
     *
     * @param config Parameter information
     * @return result
     */
    boolean checkConfigKeyUnique(SysConfigBo config);

}
