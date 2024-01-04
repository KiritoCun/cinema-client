package vn.udn.dut.cinema.system.domain.bo;

import java.util.Date;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysOtp;

/**
 * System otp business object sys_otp
 *
 * @author HoaLD
 * @date 2023-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysOtp.class, reverseConvertGenerate = false)
public class SysOtpBo extends BaseEntity {
		
	private static final long serialVersionUID = 1L;
	
    /**
     * System otp
     */
    private Long id;

    /**
     *
     */
    private String otpCode;

    /**
     *
     */
    private Long refId;
    
    /**
     * 
     */
    private String phonenumber;

    /**
     *
     */
    private Date expiredTime;

    /**
     *
     */
    private String otpType;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private String systemType;
}
