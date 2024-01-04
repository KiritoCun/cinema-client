package vn.udn.dut.cinema.system.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.DateUtils;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.SysOtp;
import vn.udn.dut.cinema.system.domain.bo.SysOtpBo;
import vn.udn.dut.cinema.system.domain.vo.SysOtpVo;
import vn.udn.dut.cinema.system.mapper.SysOtpMapper;
import vn.udn.dut.cinema.system.service.ISysOtpService;

/**
 * System otpService business layer processing
 *
 * @author HoaLD
 * @date 2023-10-23
 */
@RequiredArgsConstructor
@Service
public class SysOtpServiceImpl implements ISysOtpService {

    private final SysOtpMapper baseMapper;

    /**
     * Query System otp
     */
    @Override
    public SysOtpVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * Query System otp list
     */
    @Override
    public TableDataInfo<SysOtpVo> queryPageList(SysOtpBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysOtp> lqw = buildQueryWrapper(bo);
        Page<SysOtpVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * Query System otp list
     */
    @Override
    public List<SysOtpVo> queryList(SysOtpBo bo) {
        LambdaQueryWrapper<SysOtp> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysOtp> buildQueryWrapper(SysOtpBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysOtp> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOtpCode()), SysOtp::getOtpCode, bo.getOtpCode());
        lqw.eq(bo.getRefId() != null, SysOtp::getRefId, bo.getRefId());
        lqw.lt(bo.getExpiredTime() != null, SysOtp::getExpiredTime, bo.getExpiredTime());
        lqw.eq(StringUtils.isNotBlank(bo.getOtpType()), SysOtp::getOtpType, bo.getOtpType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysOtp::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getSystemType()), SysOtp::getSystemType, bo.getSystemType());
        lqw.like(StringUtils.isNoneBlank(bo.getPhonenumber()), SysOtp::getPhonenumber, bo.getPhonenumber());
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null,
        		SysOtp::getCreateTime, params.get("beginTime"), params.get("endTime"));
        lqw.gt(params.get("now") != null, SysOtp::getExpiredTime, params.get("now"));
        return lqw;
    }

    /**
     * Add System otp
     */
    @Override
    public Boolean insertByBo(SysOtpBo bo) {
        SysOtp add = MapstructUtils.convert(bo, SysOtp.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * Edit System otp
     */
    @Override
    public Boolean updateByBo(SysOtpBo bo) {
        SysOtp update = MapstructUtils.convert(bo, SysOtp.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(SysOtp entity){
        //TODO Do some data validation, such as unique constraints
    }

    /**
     * Batch delete System otp
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO Do some business verification to determine whether verification is required
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    
    /**
     * 
     * @param refId
     * @param otpType
     * @param phonenumber
     * @param systemType
     * @return
     */
    @Override
    public String createOtp(Long refId, String otpType, String phonenumber, String systemType) {
    	SysOtpBo bo = new SysOtpBo();
    	bo.setRefId(refId);
    	bo.setOtpType(otpType);
    	bo.setSystemType(systemType);
    	bo.setPhonenumber(phonenumber);
    	Random rnd = new Random();
        int number = rnd.nextInt(999999);
    	bo.setOtpCode(String.format("%06d", number));
    	bo.setExpiredTime(DateUtils.addMinutes(new Date(), 5));
    	insertByBo(bo);
    	return bo.getOtpCode();
    }
    
    /**
     * 
     * @param otp
     * @param refId
     * @param phonenumber
     * @param otpTYpe
     * @return
     */
    @Override
    public Boolean checkOtp(String otp, Long refId, String phonenumber, String otpType) {
    	SysOtpBo bo = new SysOtpBo();
    	bo.setOtpCode(otp);
    	bo.setRefId(refId);
    	bo.setOtpType(otpType);
    	bo.setPhonenumber(phonenumber);
    	Map<String, Object> params = new HashMap<>();
    	params.put("now", new Date());
    	bo.setParams(params);
    	List<SysOtpVo> vos = queryList(bo);
    	if (CollectionUtils.isEmpty(vos)) {
    		return false;
    	}
    	return true;
    }
}
