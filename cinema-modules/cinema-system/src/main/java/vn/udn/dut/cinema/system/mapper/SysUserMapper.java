package vn.udn.dut.cinema.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.annotation.DataColumn;
import vn.udn.dut.cinema.common.mybatis.annotation.DataPermission;
import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysUser;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;

/**
 * user table data layer
 *
 * @author HoaLD
 */
public interface SysUserMapper extends BaseMapperPlus<SysUser, SysUserVo> {

	@DataPermission({ @DataColumn(key = "deptName", value = "dept_id"),
			@DataColumn(key = "userName", value = "user_id") })
	Page<SysUserVo> selectPageUserList(@Param("page") Page<SysUser> page,
			@Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

	/**
	 * Query user list by pagination according to conditions
	 *
	 * @param queryWrapper Query conditions
	 * @return User information collection information
	 */
	@DataPermission({ @DataColumn(key = "deptName", value = "d.dept_id"),
			@DataColumn(key = "userName", value = "u.user_id") })
	List<SysUserVo> selectUserList(@Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

	/**
	 * Query the list of assigned user roles according to paging conditions
	 *
	 * @param queryWrapper Query conditions
	 * @return User information collection information
	 */
	@DataPermission({ @DataColumn(key = "deptName", value = "dept_id"),
			@DataColumn(key = "userName", value = "user_id") })
	Page<SysUserVo> selectAllocatedList(@Param("page") Page<SysUser> page,
			@Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

	/**
	 * Query the list of unassigned user roles based on paging conditions
	 *
	 * @param queryWrapper Query conditions
	 * @return User information collection information
	 */
	@DataPermission({ @DataColumn(key = "deptName", value = "dept_id"),
			@DataColumn(key = "userName", value = "user_id") })
	Page<SysUserVo> selectUnallocatedList(@Param("page") Page<SysUser> page,
			@Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

	/**
	 * Query users by username
	 *
	 * @param userName username
	 * @return User object information
	 */
	SysUserVo selectUserByUserName(String userName);

	/**
	 * Query users by phone number
	 *
	 * @param phonenumber Phone number
	 * @return User object information
	 */
	SysUserVo selectUserByPhonenumber(String phonenumber);

	/**
	 * Query users by email
	 *
	 * @param email Mail
	 * @return User object information
	 */
	SysUserVo selectUserByEmail(String email);

	/**
	 * Query users by username (do not use the tenant plugin)
	 *
	 * @param userName username
	 * @param tenantId tenant id
	 * @return User object information
	 */
	@InterceptorIgnore(tenantLine = "true")
	SysUserVo selectTenantUserByUserName(String userName, String tenantId);

	/**
	 * Query users by mobile phone number (do not use the tenant plugin)
	 *
	 * @param phonenumber Phone number
	 * @param tenantId    tenant id
	 * @return User object information
	 */
	@InterceptorIgnore(tenantLine = "true")
	SysUserVo selectTenantUserByPhonenumber(String phonenumber, String tenantId);

	/**
	 * Query users by email (do not use the tenant plugin)
	 *
	 * @param email    Mail
	 * @param tenantId tenant id
	 * @return User object information
	 */
	@InterceptorIgnore(tenantLine = "true")
	SysUserVo selectTenantUserByEmail(String email, String tenantId);

	/**
	 * Query users by user ID
	 *
	 * @param userId User ID
	 * @return User object information
	 */
	@DataPermission({ @DataColumn(key = "deptName", value = "d.dept_id"),
			@DataColumn(key = "userName", value = "u.user_id") })
	SysUserVo selectUserById(Long userId);

	@Override
	@DataPermission({ @DataColumn(key = "deptName", value = "dept_id"),
			@DataColumn(key = "userName", value = "user_id") })
	int update(@Param(Constants.ENTITY) SysUser user, @Param(Constants.WRAPPER) Wrapper<SysUser> updateWrapper);

	@Override
	@DataPermission({ @DataColumn(key = "deptName", value = "dept_id"),
			@DataColumn(key = "userName", value = "user_id") })
	int updateById(@Param(Constants.ENTITY) SysUser user);

	List<SysUserVo> selectShippingLineUserByOprAndBusinessUnit(String opr, String businessUnit);

}
