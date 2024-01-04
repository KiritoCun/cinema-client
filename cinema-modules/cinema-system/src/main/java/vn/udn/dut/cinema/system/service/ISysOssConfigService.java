package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysOssConfigBo;
import vn.udn.dut.cinema.system.domain.vo.SysOssConfigVo;

import java.util.Collection;

/**
 * Object storage configuration Service interface
 *
 * @author HoaLD
 * @date 2021-08-13
 */
public interface ISysOssConfigService {

    /**
     * Initialize OSS configuration
     */
    void init();

    /**
     * Query a single
     */
    SysOssConfigVo queryById(Long ossConfigId);

    /**
     * query list
     */
    TableDataInfo<SysOssConfigVo> queryPageList(SysOssConfigBo bo, PageQuery pageQuery);


    /**
     * Insert object storage configuration based on new business objects
     *
     * @param bo Adding new business objects to the object storage configuration
     * @return
     */
    Boolean insertByBo(SysOssConfigBo bo);

    /**
     * Modify the object store configuration based on editing business objects
     *
     * @param bo Object Storage Configuration Editing Business Objects
     * @return
     */
    Boolean updateByBo(SysOssConfigBo bo);

    /**
     * Verify and delete data
     *
     * @param ids     primary key set
     * @param isValid Whether to check, true - check before deletion, false - no check
     * @return
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * Enable disabled state
     */
    int updateOssConfigStatus(SysOssConfigBo bo);

}
