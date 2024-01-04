package vn.udn.dut.cinema.system.domain.vo;

import lombok.Data;

/**
 * user personal information
 *
 * @author HoaLD
 */
@Data
public class ProfileVo {

    /**
     * User Info
     */
    private SysUserVo user;

    /**
     * The role group the user belongs to
     */
    private String roleGroup;

    /**
     * The user's job group
     */
    private String postGroup;


}
