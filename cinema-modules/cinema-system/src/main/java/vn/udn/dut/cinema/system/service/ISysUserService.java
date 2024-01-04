package vn.udn.dut.cinema.system.service;

import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysUserBo;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;

/**
 * user business layer
 *
 * @author HoaLD
 */
public interface ISysUserService {

	TableDataInfo<SysUserVo> selectPageUserList(SysUserBo user, PageQuery pageQuery);

	/**
	 * Query user list by pagination according to conditions
	 *
	 * @param user User Info
	 * @return User information collection information
	 */
	List<SysUserVo> selectUserList(SysUserBo user);

	/**
	 * Query the list of assigned user roles based on paging conditions
	 *
	 * @param user User Info
	 * @return User information collection information
	 */
	TableDataInfo<SysUserVo> selectAllocatedList(SysUserBo user, PageQuery pageQuery);

	/**
	 * Query the list of unassigned user roles based on paging conditions
	 *
	 * @param user User Info
	 * @return User information collection information
	 */
	TableDataInfo<SysUserVo> selectUnallocatedList(SysUserBo user, PageQuery pageQuery);

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
	 * Query users by user ID
	 *
	 * @param userId User ID
	 * @return User object information
	 */
	SysUserVo selectUserById(Long userId);

	/**
	 * Query the role group to which the user belongs according to the user ID
	 *
	 * @param userName username
	 * @return result
	 */
	String selectUserRoleGroup(String userName);

	/**
	 * Query the post group to which the user belongs according to the user ID
	 *
	 * @param userName username
	 * @return result
	 */
	String selectUserPostGroup(String userName);

	/**
	 * Check if username is unique
	 *
	 * @param user User Info
	 * @return result
	 */
	boolean checkUserNameUnique(SysUserBo user);

	/**
	 * Check whether the mobile phone number is unique
	 *
	 * @param user User Info
	 * @return result
	 */
	boolean checkPhoneUnique(SysUserBo user);

	/**
	 * Check if email is unique
	 *
	 * @param user User Info
	 * @return result
	 */
	boolean checkEmailUnique(SysUserBo user);

	/**
	 * Check if the user allows the operation
	 *
	 * @param userId User ID
	 */
	void checkUserAllowed(Long userId);

	/**
	 * Check if the user has data permissions
	 *
	 * @param userId user id
	 */
	void checkUserDataScope(Long userId);

	/**
	 * Add user information
	 *
	 * @param user User Info
	 * @return result
	 */
	int insertUser(SysUserBo user);

	/**
	 * Registered user information
	 *
	 * @param user User Info
	 * @return result
	 */
	boolean registerUser(SysUserBo user, String tenantId);

	/**
	 * modify user information
	 *
	 * @param user User Info
	 * @return result
	 */
	int updateUser(SysUserBo user);

	/**
	 * User authorization role
	 *
	 * @param userId  User ID
	 * @param roleIds role group
	 */
	void insertUserAuth(Long userId, Long[] roleIds);

	/**
	 * modify user status
	 *
	 * @param userId User ID
	 * @param status account status
	 * @return result
	 */
	int updateUserStatus(Long userId, String status);

	/**
	 * Modify basic user information
	 *
	 * @param user User Info
	 * @return result
	 */
	int updateUserProfile(SysUserBo user);

	/**
	 * Modify user avatar
	 *
	 * @param userId User ID
	 * @param avatar Avatar address
	 * @return result
	 */
	boolean updateUserAvatar(Long userId, Long avatar);

	/**
	 * reset user password
	 *
	 * @param userId   User ID
	 * @param password password
	 * @return result
	 */
	int resetUserPwd(Long userId, String password);

	/**
	 * Delete user by user ID
	 *
	 * @param userId User ID
	 * @return result
	 */
	int deleteUserById(Long userId);

	/**
	 * Batch delete user information
	 *
	 * @param userIds User ID to delete
	 * @return result
	 */
	int deleteUserByIds(Long[] userIds);

	/**
	 * Get list shipping line user by opr and business unit
	 * 
	 * @param opr
	 * @param businessUnit
	 * @return
	 */
	List<SysUserVo> selectShippingLineUserByOprAndBusinessUnit(String opr, String businessUnit);

}
