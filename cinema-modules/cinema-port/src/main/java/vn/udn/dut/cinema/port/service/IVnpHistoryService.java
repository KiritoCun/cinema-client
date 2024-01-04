package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.VnpHistoryBo;
import vn.udn.dut.cinema.port.domain.vo.VnpHistoryVo;


/**
 * VN PAY History service interface
 *
 * @author HoaLD
 * @date 2023-12-26
 */
public interface IVnpHistoryService {

    /**
     * Query VN PAY History
     */
    VnpHistoryVo queryById(Long id);

    /**
     * Query VN PAY History list
     */
    TableDataInfo<VnpHistoryVo> queryPageList(VnpHistoryBo bo, PageQuery pageQuery);

    /**
     * Query VN PAY History list
     */
    List<VnpHistoryVo> queryList(VnpHistoryBo bo);

    /**
     * Add VN PAY History
     */
    Boolean insertByBo(VnpHistoryBo bo);

    /**
     * Edit VN PAY History
     */
    Boolean updateByBo(VnpHistoryBo bo);

    /**
     * Verify and delete VN PAY History information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
    
    /**
     * 
     * @param vnpTransactionId
     * @return
     */
    VnpHistoryVo queryByVnpTransactionId(String vnpTransactionId);
}
