package vn.udn.dut.cinema.system.mapper;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysUserRole;

import java.util.List;

/**
 * User and role association table Data layer
 *
 * @author HoaLD
 */
public interface SysUserRoleMapper extends BaseMapperPlus<SysUserRole, SysUserRole> {

    List<Long> selectUserIdsByRoleId(Long roleId);

}
