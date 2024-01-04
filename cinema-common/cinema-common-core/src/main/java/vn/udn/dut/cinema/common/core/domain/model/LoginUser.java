package vn.udn.dut.cinema.common.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.dto.RoleDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Login User Identity Permissions
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenant ID
     */
    private String tenantId;

    /**
     * User ID
     */
    private Long userId;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * department name
     */
    private String deptName;

    /**
     * unique user ID
     */
    private String token;

    /**
     * user type
     */
    private String userType;

    /**
     * Log in time
     */
    private Long loginTime;

    /**
     * Expiration
     */
    private Long expireTime;

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
     * menu permissions
     */
    private Set<String> menuPermission;

    /**
     * Role Permissions
     */
    private Set<String> rolePermission;

    /**
     * username
     */
    private String username;

    /**
     * role object
     */
    private List<RoleDTO> roles;

    /**
     * Data Permissions Current Role ID
     */
    private Long roleId;

    /**
     * get login id
     */
    public String getLoginId() {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        return userType + ":" + userId;
    }

}
