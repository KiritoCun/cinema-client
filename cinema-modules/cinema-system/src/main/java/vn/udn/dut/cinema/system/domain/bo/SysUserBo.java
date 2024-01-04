package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.xss.Xss;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysUser;

/**
 * User information business object sys_user
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUser.class, reverseConvertGenerate = false)
public class SysUserBo extends BaseEntity {

    private static final long serialVersionUID = -3287306280356579833L;
    
    private Long cinemaId;

	/**
     * User ID
     */
    private Long userId;

    /**
     * Department ID
     */
    private Long deptId;

    /**
     * user account
     */
    @Xss(message = "User account cannot contain script characters")
    @NotBlank(message = "User account cannot be empty")
    @Size(min = 0, max = 30, message = "User account cannot be empty")
    private String userName;

    /**
     * User's Nickname
     */
    @Xss(message = "User name cannot contain script characters")
    @Size(min = 0, max = 30, message = "User name length cannot exceed {max} characters")
    private String nickName;

    /**
     * User type (sys_user system user)
     */
    private String userType;

    /**
     * user mailbox
     */
    @Email(message = "Email format is incorrect")
    @Size(min = 0, max = 50, message = "The email length cannot exceed {max} characters")
    private String email;

    /**
     * phone number
     */
    private String phonenumber;

    /**
     * User gender (0 male, 1 female, 2 unknown)
     */
    private String sex;

    /**
     * password
     */
    private String password;

    /**
     * Account status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Remark
     */
    private String remark;
    
    /**
     * System type
     */
    private String systemType;

    /**
     * role group
     */
    @Size(min = 1, message = "User role cannot be empty")
    private Long[] roleIds;

    /**
     * Post group
     */
    private Long[] postIds;

    /**
     * Data Permissions Current Role ID
     */
    private Long roleId;

    public SysUserBo(Long userId) {
        this.userId = userId;
    }

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.userId);
    }

}
