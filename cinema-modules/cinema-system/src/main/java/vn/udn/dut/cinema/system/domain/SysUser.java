package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

import java.util.Date;

/**
 * User object sys_user
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends TenantEntity {

    private static final long serialVersionUID = 1445341316497924617L;
    
    private Long cinemaId;

	/**
     * User ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * user account
     */
    private String userName;

    /**
     * User name
     */
    private String nickName;

    /**
     * User type (sys_user system user)
     */
    private String userType;

    /**
     * Email
     */
    private String email;

    /**
     * phone number
     */
    private String phonenumber;

    /**
     * user gender
     */
    private String sex;

    /**
     * profile picture
     */
    private Long avatar;

    /**
     * password
     */
    @TableField(
        insertStrategy = FieldStrategy.NOT_EMPTY,
        updateStrategy = FieldStrategy.NOT_EMPTY,
        whereStrategy = FieldStrategy.NOT_EMPTY
    )
    private String password;

    /**
     * Account status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Delete flag (0 means exist, 2 means delete)
     */
    @TableLogic
    private String delFlag;

    /**
     * Last login IP
     */
    private String loginIp;

    /**
     * last login time
     */
    private Date loginDate;

    /**
     * Remark
     */
    private String remark;

    /**
     * System type
     */
    private String systemType;

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.userId);
    }

}
