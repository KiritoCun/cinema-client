package vn.udn.dut.cinema.system.domain;

import lombok.Data;

/**
 * current online session
 *
 * @author HoaLD
 */

@Data
public class SysUserOnline {

    /**
     * session number
     */
    private String tokenId;

    /**
     * Department name
     */
    private String deptName;

    /**
     * user name
     */
    private String userName;

    /**
     * login IP address
     */
    private String ipaddr;

    /**
     * login address
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
     * Log in time
     */
    private Long loginTime;

}
