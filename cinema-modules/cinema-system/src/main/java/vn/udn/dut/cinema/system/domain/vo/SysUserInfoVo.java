package vn.udn.dut.cinema.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * User Info
 *
 * @author HoaLD
 */
@Data
public class SysUserInfoVo {

    /**
     * User Info
     */
    private SysUserVo user;

    /**
     * List of role IDs
     */
    private List<Long> roleIds;

    /**
     * role list
     */
    private List<SysRoleVo> roles;

    /**
     * Job ID list
     */
    private List<Long> postIds;

    /**
     * job list
     */
    private List<SysPostVo> posts;

}
