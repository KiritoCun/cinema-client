package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysLogininfor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * System access record business object sys_logininfor
 *
 * @author HoaLD
 */

@Data
@AutoMapper(target = SysLogininfor.class, reverseConvertGenerate = false)
public class SysLogininforBo {

    /**
     * access ID
     */
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
     * Login status (0 success, 1 failure)
     */
    private String status;

    /**
     * Prompt message
     */
    private String msg;

    /**
     * interview time
     */
    private Date loginTime;

    /**
     * request parameters
     */
    private Map<String, Object> params = new HashMap<>();


}
