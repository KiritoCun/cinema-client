package vn.udn.dut.cinema.port.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.VnpHistory;
import vn.udn.dut.cinema.port.domain.bo.VnpHistoryBo;
import vn.udn.dut.cinema.port.domain.vo.VnpHistoryVo;
import vn.udn.dut.cinema.port.mapper.VnpHistoryMapper;
import vn.udn.dut.cinema.port.service.IVnpHistoryService;

/**
 * VN PAY HistoryService business layer processing
 *
 * @author HoaLD
 * @date 2023-12-26
 */
@RequiredArgsConstructor
@Service
public class VnpHistoryServiceImpl implements IVnpHistoryService {

    private final VnpHistoryMapper baseMapper;

    /**
     * Query VN PAY History
     */
    @Override
    public VnpHistoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * Query VN PAY History list
     */
    @Override
    public TableDataInfo<VnpHistoryVo> queryPageList(VnpHistoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<VnpHistory> lqw = buildQueryWrapper(bo);
        Page<VnpHistoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * Query VN PAY History list
     */
    @Override
    public List<VnpHistoryVo> queryList(VnpHistoryBo bo) {
        LambdaQueryWrapper<VnpHistory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<VnpHistory> buildQueryWrapper(VnpHistoryBo bo) {
//        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<VnpHistory> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTransactionId()), VnpHistory::getTransactionId, bo.getTransactionId());
        lqw.eq(bo.getAmount() != null, VnpHistory::getAmount, bo.getAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getSecureHashType()), VnpHistory::getSecureHashType, bo.getSecureHashType());
        lqw.eq(StringUtils.isNotBlank(bo.getSecureHash()), VnpHistory::getSecureHash, bo.getSecureHash());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderInfo()), VnpHistory::getOrderInfo, bo.getOrderInfo());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), VnpHistory::getStatus, bo.getStatus());
        lqw.eq(bo.getVnpAmount() != null, VnpHistory::getVnpAmount, bo.getVnpAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpTransactionId()), VnpHistory::getVnpTransactionId, bo.getVnpTransactionId());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpOrderInfo()), VnpHistory::getVnpOrderInfo, bo.getVnpOrderInfo());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpBankCode()), VnpHistory::getVnpBankCode, bo.getVnpBankCode());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpBankTranNo()), VnpHistory::getVnpBankTranNo, bo.getVnpBankTranNo());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpCardType()), VnpHistory::getVnpCardType, bo.getVnpCardType());
        lqw.eq(bo.getVnpPayDate() != null, VnpHistory::getVnpPayDate, bo.getVnpPayDate());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpTransactionNo()), VnpHistory::getVnpTransactionNo, bo.getVnpTransactionNo());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpResponseCode()), VnpHistory::getVnpResponseCode, bo.getVnpResponseCode());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpTransactionStatus()), VnpHistory::getVnpTransactionStatus, bo.getVnpTransactionStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpTxnRef()), VnpHistory::getVnpTxnRef, bo.getVnpTxnRef());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpSecureHashType()), VnpHistory::getVnpSecureHashType, bo.getVnpSecureHashType());
        lqw.eq(StringUtils.isNotBlank(bo.getVnpSecureHash()), VnpHistory::getVnpSecureHash, bo.getVnpSecureHash());
        return lqw;
    }

    /**
     * Add VN PAY History
     */
    @Override
    public Boolean insertByBo(VnpHistoryBo bo) {
        VnpHistory add = MapstructUtils.convert(bo, VnpHistory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * Edit VN PAY History
     */
    @Override
    public Boolean updateByBo(VnpHistoryBo bo) {
        VnpHistory update = MapstructUtils.convert(bo, VnpHistory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(VnpHistory entity){
        //Do some data validation, such as unique constraints
    }

    /**
     * Batch delete VN PAY History
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //Do some business verification to determine whether verification is required
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    
    /**
     * 
     * @param vnpTransactionId
     * @return
     */
    @Override
    public VnpHistoryVo queryByVnpTransactionId(String vnpTransactionId) {
    	VnpHistoryBo bo = new VnpHistoryBo();
    	bo.setVnpTransactionId(vnpTransactionId);
    	List<VnpHistoryVo> vos = queryList(bo);
    	if (CollectionUtils.isEmpty(vos)) {
    		return null;
    	}
    	return vos.get(0);
    }
}
