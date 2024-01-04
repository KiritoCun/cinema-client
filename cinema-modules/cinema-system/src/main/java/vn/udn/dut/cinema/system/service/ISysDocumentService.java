package vn.udn.dut.cinema.system.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysDocumentBo;
import vn.udn.dut.cinema.system.domain.vo.SysDocumentVo;

/**
 * System document service interface
 *
 * @author HoaLD
 * @date 2023-08-23
 */
public interface ISysDocumentService {

    /**
     * Query System document
     */
    SysDocumentVo queryById(Long id);

    /**
     * Query System document list
     */
    TableDataInfo<SysDocumentVo> queryPageList(SysDocumentBo bo, PageQuery pageQuery);

    /**
     * Query System document list
     */
    List<SysDocumentVo> queryList(SysDocumentBo bo);

    /**
     * Add System document
     */
    Boolean insertByBo(SysDocumentBo bo);

    /**
     * Edit System document
     */
    Boolean updateByBo(SysDocumentBo bo);

    /**
     * Verify and delete System document information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
    
    /**
     * modify document status
     *
     * @param id Document ID
     * @param status document status
     * @return result
     */
    int updateDocumentStatus(Long id, String status);
}
