package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * System access record table sys_logininfor
 *
 * @author HoaLD
 */

@Data
@TableName("sys_logininfor")
public class SysLogininfor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "info_id")
    private Long infoId;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * user account
     */
    private String userName;

    /**
     * Login status 0 success 1 failure
     */
    private String status;

    /**
     * login IP address
     */
    private String ipaddr;

    /**
     * login location
     */
    private String loginLocation;

    /**
     * browser type
     */
    private String browser;

    /**
     * operating system
     */
    private String os;

    /**
     * Prompt message
     */
    private String msg;

    /**
     * interview time
     */
    private Date loginTime;

}
