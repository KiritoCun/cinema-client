package vn.udn.dut.cinema.common.core.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * current online session
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
public class UserOnlineDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
