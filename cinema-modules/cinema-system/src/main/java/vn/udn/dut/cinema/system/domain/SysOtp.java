package vn.udn.dut.cinema.system.domain;

import java.io.Serial;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * System otp object sys_otp
 *
 * @author HoaLD
 * @date 2023-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_otp")
public class SysOtp extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * System otp
     */
    @TableId(value = "id")
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
