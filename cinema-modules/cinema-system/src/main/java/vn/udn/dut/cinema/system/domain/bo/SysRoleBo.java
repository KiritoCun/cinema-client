package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysRole;

/**
 * Role information business object sys_role
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRole.class, reverseConvertGenerate = false)
public class SysRoleBo extends BaseEntity {

    private static final long serialVersionUID = 8633974874691517297L;

	/**
     * character ID
     */
    @NotNull(message = "Role id cannot be empty", groups = { EditGroup.class })
    private Long roleId;

    /**
     * Role Name
     */
    @NotBlank(message = "Role name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 30, message = "Role name cannot exceed {max} characters")
    private String roleName;

    /**
     * role permission string
     */
    @NotBlank(message = "Role permission cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "Role permission length cannot exceed {max} characters")
    private String roleKey;

    /**
     * display order
     */
    @NotNull(message = "Display order cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private Integer roleSort;

    /**
     * Data scope (1: All data permissions 2: User-defined data permissions 3: Data permissions of this department 4: Data permissions of this department and below)
     */
    private String dataScope;

    /**
     * Whether the menu tree selection items are displayed in association
     */
    private Boolean menuCheckStrictly;

    /**
     * Whether the selection items of the department tree are displayed in association
     */
    private Boolean deptCheckStrictly;

    /**
     * Role status (0 normal 1 disabled)
     */
    private String status;
    
    /**
     * System type
     */
    private String systemType;

    /**
     * Remark
     */
    private String remark;

    /**
     * menu group
     */
    private Long[] menuIds;

    /**
     * Department Group (Data Permissions)
     */
    private Long[] deptIds;

    public SysRoleBo(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
    }

}
