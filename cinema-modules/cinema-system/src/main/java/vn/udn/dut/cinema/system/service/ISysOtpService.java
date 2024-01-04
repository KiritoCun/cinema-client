package vn.udn.dut.cinema.system.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysOtpBo;
import vn.udn.dut.cinema.system.domain.vo.SysOtpVo;

/**
 * System otp service interface
 *
 * @author HoaLD
 * @date 2023-10-23
 */
public interface ISysOtpService {

    /**
     * Query System otp
     */
    SysOtpVo queryById(Long id);

    /**
     * Query System otp list
     */
    TableDataInfo<SysOtpVo> queryPageList(SysOtpBo bo, PageQuery pageQuery);

    /**
     * Query System otp list
     */
    List<SysOtpVo> queryList(SysOtpBo bo);

    /**
     * Add System otp
     */
    Boolean insertByBo(SysOtpBo bo);

    /**
     * Edit System otp
     */
    Boolean updateByBo(SysOtpBo bo);

    /**
     * Verify and delete System otp information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
    
    /**
     * 
     * @param refId
     * @param otpType
     * @param phonenumber
     * @param systemType
     * @return
     */
    String createOtp(Long refId, String otpType, String phonenumber, String systemType);
    
    /**
     * 
     * @param otp
     * @param refId
     * @param phonenumber
     * @param otpType
     * @return
     */
    Boolean checkOtp(String otp, Long refId, String phonenumber, String otpType);
}
