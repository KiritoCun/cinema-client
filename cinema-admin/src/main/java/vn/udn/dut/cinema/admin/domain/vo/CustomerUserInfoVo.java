package vn.udn.dut.cinema.admin.domain.vo;

import java.util.List;

import lombok.Data;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;

/**
 * User Info
 *
 * @author HoaLD
 */
@Data
public class CustomerUserInfoVo {

	/**
	 * List of role IDs
	 */
	private List<Long> roleIds;

	/**
	 * role list
	 */
	private List<SysRoleVo> roles;
}
