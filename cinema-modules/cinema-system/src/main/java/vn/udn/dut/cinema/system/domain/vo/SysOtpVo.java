package vn.udn.dut.cinema.system.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysOtp;



/**
 * System otp view object sys_otp
 *
 * @author HoaLD
 * @date 2023-10-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysOtp.class)
public class SysOtpVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * System otp
     */
    private Long id;

    /**
     *
     */
    @ExcelProperty(value = "OTP code")
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
    @ExcelProperty(value = "Expired time")
    private Date expiredTime;

    /**
     *
     */
    @ExcelProperty(value = "Type")
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
    
    /**
     * 
     */
    @ExcelProperty(value = "Create time")
    private Date createTime;
}
