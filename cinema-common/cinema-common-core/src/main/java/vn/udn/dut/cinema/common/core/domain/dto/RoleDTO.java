package vn.udn.dut.cinema.common.core.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Role
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = -3125628570457559601L;

	/**
     * character ID
     */
    private Long roleId;

    /**
     * Role Name
     */
    private String roleName;

    /**
     * Role Permissions
     */
    private String roleKey;

    /**
     * Data scope (1: all data permissions; 2: custom data permissions; 3: data permissions of this department; 4: data permissions of this department and below; 5: only personal data permissions)
     */
    private String dataScope;

}
