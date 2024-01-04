package vn.udn.dut.cinema.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.sensitive.annotation.Sensitive;
import vn.udn.dut.cinema.common.sensitive.core.SensitiveStrategy;
import vn.udn.dut.cinema.common.translation.annotation.Translation;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.system.domain.SysUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * User information view object sys_user
 *
 * @author HoaLD
 */
@Data
@AutoMapper(target = SysUser.class)
public class SysUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private Long cinemaId;

    /**
     * User ID
     */
    private Long userId;

    /**
     * tenant ID
     */
    private String tenantId;

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
     * user mailbox
     */
    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    private String email;

    /**
     * phone number
     */
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phonenumber;

    /**
     * User gender (0 male, 1 female, 2 unknown)
     */
    private String sex;

    /**
     * Avatar address
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL)
    private Long avatar;

    /**
     * password
     */
    @JsonIgnore
    @JsonProperty
    private String password;

    /**
     * Account status (0 normal 1 disabled)
     */
    private String status;

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
     * Creation time
     */
    private Date createTime;

    /**
     * department object
     */
    private SysDeptVo dept;

    /**
     * role object
     */
    private List<SysRoleVo> roles;

    /**
     * role group
     */
    private Long[] roleIds;

    /**
     * Post group
     */
    private Long[] postIds;

    /**
     * Data Permissions Current Role ID
     */
    private Long roleId;

    private String deptName;
}
