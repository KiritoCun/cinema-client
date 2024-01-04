package vn.udn.dut.cinema.system.domain.vo;

import lombok.Data;

import java.util.Set;

/**
 * Login user information
 *
 * @author HoaLD
 */
@Data
public class UserInfoVo {

    /**
     * Basic user information
     */
    private SysUserVo user;
   
    /**
     * menu permissions
     */
    private Set<String> permissions;

    /**
     * Role Permissions
     */
    private Set<String> roles;
    
    public String domesticFlag;

}
