package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Role table sys_role
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends TenantEntity {

    private static final long serialVersionUID = 867700713137258423L;

	/**
     * Role ID
     */
    @TableId(value = "role_id")
    private Long roleId;

    /**
     * Role name
     */
    private String roleName;

    /**
     * Role key
     */
    private String roleKey;

    /**
     * role sorting
     */
    private Integer roleSort;

    /**
     * Data scope (1: all data permissions; 2: custom data permissions; 3: data permissions of this department; 4: data permissions of this department and below; 5: only personal data permissions)
     */
    private String dataScope;

    /**
     * Whether the menu tree selection items are displayed in association (0: parent and child are not associated with each other, 1: parent and child are associated with each other)
     */
    private Boolean menuCheckStrictly;

    /**
     * Whether the selection items of the department tree are displayed in association (0: parent and child are not associated with each other, 1: parent and child are associated with each other)
     */
    private Boolean deptCheckStrictly;

    /**
     * Role status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Delete flag (0 means exist, 2 means delete)
     */
    @TableLogic
    private String delFlag;

    /**
     * Remark
     */
    private String remark;

    /**
     * System type
     */
    private String systemType;

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

}
