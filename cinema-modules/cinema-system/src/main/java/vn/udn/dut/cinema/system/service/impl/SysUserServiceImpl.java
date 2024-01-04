package vn.udn.dut.cinema.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.CacheNames;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.service.UserService;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.mybatis.helper.DataBaseHelper;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.system.domain.SysDept;
import vn.udn.dut.cinema.system.domain.SysUser;
import vn.udn.dut.cinema.system.domain.SysUserPost;
import vn.udn.dut.cinema.system.domain.SysUserRole;
import vn.udn.dut.cinema.system.domain.bo.SysNotificationBo;
import vn.udn.dut.cinema.system.domain.bo.SysUserBo;
import vn.udn.dut.cinema.system.domain.vo.SysPostVo;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;
import vn.udn.dut.cinema.system.mapper.SysDeptMapper;
import vn.udn.dut.cinema.system.mapper.SysPostMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleMapper;
import vn.udn.dut.cinema.system.mapper.SysUserMapper;
import vn.udn.dut.cinema.system.mapper.SysUserPostMapper;
import vn.udn.dut.cinema.system.mapper.SysUserRoleMapper;
import vn.udn.dut.cinema.system.service.ISysNotificationService;
import vn.udn.dut.cinema.system.service.ISysUserService;

/**
 * User business layer processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements ISysUserService, UserService {

	private final SysUserMapper baseMapper;
	private final SysDeptMapper deptMapper;
	private final SysRoleMapper roleMapper;
	private final SysPostMapper postMapper;
	private final SysUserRoleMapper userRoleMapper;
	private final SysUserPostMapper userPostMapper;
	private final ISysNotificationService notificationService;

	@Override
	public TableDataInfo<SysUserVo> selectPageUserList(SysUserBo user, PageQuery pageQuery) {
		Page<SysUserVo> page = baseMapper.selectPageUserList(pageQuery.build(), this.buildQueryWrapper(user));
		return TableDataInfo.build(page);
	}

	/**
	 * Query user list by pagination according to conditions
	 *
	 * @param user User Info
	 * @return User information collection information
	 */
	@Override
	public List<SysUserVo> selectUserList(SysUserBo user) {
		return baseMapper.selectUserList(this.buildQueryWrapper(user));
	}

	private Wrapper<SysUser> buildQueryWrapper(SysUserBo user) {
		Map<String, Object> params = user.getParams();
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
				.eq(ObjectUtil.isNotNull(user.getUserId()), "u.user_id", user.getUserId())
				.like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
				.eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
				.eq(StringUtils.isNotBlank(user.getSystemType()), "u.system_type", user.getSystemType())
				.like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
				.between(params.get("beginTime") != null && params.get("endTime") != null, "u.create_time",
						params.get("beginTime"), params.get("endTime"))
				.and(ObjectUtil.isNotNull(user.getDeptId()), w -> {
					List<SysDept> deptList = deptMapper.selectList(new LambdaQueryWrapper<SysDept>()
							.select(SysDept::getDeptId).apply(DataBaseHelper.findInSet(user.getDeptId(), "ancestors")));
					List<Long> ids = StreamUtils.toList(deptList, SysDept::getDeptId);
					ids.add(user.getDeptId());
					w.in("u.dept_id", ids);
				});
		return wrapper;
	}

	/**
	 * Query the list of assigned user roles based on paging conditions
	 *
	 * @param user User Info
	 * @return User information collection information
	 */
	@Override
	public TableDataInfo<SysUserVo> selectAllocatedList(SysUserBo user, PageQuery pageQuery) {
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
				.eq(ObjectUtil.isNotNull(user.getRoleId()), "r.role_id", user.getRoleId())
				.like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
				.eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
				.eq(StringUtils.isNotBlank(user.getSystemType()), "u.system_type", user.getSystemType())
				.like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber());
		Page<SysUserVo> page = baseMapper.selectAllocatedList(pageQuery.build(), wrapper);
		return TableDataInfo.build(page);
	}

	/**
	 * Query the list of unassigned user roles based on paging conditions
	 *
	 * @param user User Info
	 * @return User information collection information
	 */
	@Override
	public TableDataInfo<SysUserVo> selectUnallocatedList(SysUserBo user, PageQuery pageQuery) {
		List<Long> userIds = userRoleMapper.selectUserIdsByRoleId(user.getRoleId());
		QueryWrapper<SysUser> wrapper = Wrappers.query();
		wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
				.and(w -> w.ne("r.role_id", user.getRoleId()).or().isNull("r.role_id"))
				.notIn(CollUtil.isNotEmpty(userIds), "u.user_id", userIds)
				.like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
				.like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
				.eq(StringUtils.isNotBlank(user.getSystemType()), "u.system_type", user.getSystemType());
		Page<SysUserVo> page = baseMapper.selectUnallocatedList(pageQuery.build(), wrapper);
		return TableDataInfo.build(page);
	}

	/**
	 * Query users by username
	 *
	 * @param userName username
	 * @return User object information
	 */
	@Override
	public SysUserVo selectUserByUserName(String userName) {
		return baseMapper.selectUserByUserName(userName);
	}

	/**
	 * Query users by phone number
	 *
	 * @param phonenumber Phone number
	 * @return User object information
	 */
	@Override
	public SysUserVo selectUserByPhonenumber(String phonenumber) {
		return baseMapper.selectUserByPhonenumber(phonenumber);
	}

	/**
	 * Query users by user ID
	 *
	 * @param userId User ID
	 * @return User object information
	 */
	@Override
	public SysUserVo selectUserById(Long userId) {
		return baseMapper.selectUserById(userId);
	}

	/**
	 * Query the role group to which the user belongs
	 *
	 * @param userName username
	 * @return result
	 */
	@Override
	public String selectUserRoleGroup(String userName) {
		List<SysRoleVo> list = roleMapper.selectRolesByUserName(userName);
		if (CollUtil.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return StreamUtils.join(list, SysRoleVo::getRoleName);
	}

	/**
	 * Query the post group to which the user belongs
	 *
	 * @param userName username
	 * @return result
	 */
	@Override
	public String selectUserPostGroup(String userName) {
		List<SysPostVo> list = postMapper.selectPostsByUserName(userName);
		if (CollUtil.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return StreamUtils.join(list, SysPostVo::getPostName);
	}

	/**
	 * Check if username is unique
	 *
	 * @param user User Info
	 * @return result
	 */
	@Override
	public boolean checkUserNameUnique(SysUserBo user) {
		boolean exist = baseMapper
				.exists(new LambdaQueryWrapper<SysUser>().eq(SysUser::getDelFlag, UserConstants.USER_NORMAL)
						.eq(SysUser::getUserName, user.getUserName()).eq(SysUser::getSystemType, user.getSystemType())
						.ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
		return !exist;
	}

	/**
	 * Check whether the mobile phone number is unique
	 *
	 * @param user User Info
	 */
	@Override
	public boolean checkPhoneUnique(SysUserBo user) {
		boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
				.eq(SysUser::getDelFlag, UserConstants.USER_NORMAL).eq(SysUser::getPhonenumber, user.getPhonenumber())
				.eq(SysUser::getSystemType, user.getSystemType())
				.ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
		return !exist;
	}

	/**
	 * Check if email is unique
	 *
	 * @param user User Info
	 */
	@Override
	public boolean checkEmailUnique(SysUserBo user) {
		boolean exist = baseMapper
				.exists(new LambdaQueryWrapper<SysUser>().eq(SysUser::getDelFlag, UserConstants.USER_NORMAL)
						.eq(SysUser::getEmail, user.getEmail()).eq(SysUser::getSystemType, user.getSystemType())
						.ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
		return !exist;
	}

	/**
	 * Check if the user allows the operation
	 *
	 * @param userId User ID
	 */
	@Override
	public void checkUserAllowed(Long userId) {
		if (ObjectUtil.isNotNull(userId) && LoginHelper.isSuperAdmin(userId)) {
			throw new ServiceException("Operation super administrator user not allowed");
		}
	}

	/**
	 * Check if the user has data permissions
	 *
	 * @param userId user id
	 */
	@Override
	public void checkUserDataScope(Long userId) {
		if (ObjectUtil.isNull(userId)) {
			return;
		}
		if (LoginHelper.isSuperAdmin()) {
			return;
		}
		if (ObjectUtil.isNull(baseMapper.selectUserById(userId))) {
			throw new ServiceException("No access to user data!");
		}
	}

	/**
	 * Added save user information
	 *
	 * @param user User Info
	 * @return result
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertUser(SysUserBo user) {
		SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
		// Add user information
		int rows = baseMapper.insert(sysUser);
		user.setUserId(sysUser.getUserId());
		// Add user position association
		insertUserPost(user, false);
		// Added user and role management
		insertUserRole(user, false);

//		SysNotificationBo noti = new SysNotificationBo();
//		noti.setUserId(user.getUserId());
//		noti.setTitle("Tài khoản kích hoạt thành công!");
//		noti.setContent("Chào mừng bạn đến với hệ thống Eport - VICT dành cho nhân viên nội bộ Cảng VICT.");
//		notificationService.insertByBo(noti);
		return rows;
	}

	/**
	 * Registered user information
	 *
	 * @param user User Info
	 * @return result
	 */
	@Override
	public boolean registerUser(SysUserBo user, String tenantId) {
		user.setCreateBy(user.getUserId());
		user.setUpdateBy(user.getUserId());
		SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
		sysUser.setTenantId(tenantId);
		return baseMapper.insert(sysUser) > 0;
	}

	/**
	 * Modify and save user information
	 *
	 * @param user User Info
	 * @return result
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateUser(SysUserBo user) {
		// Added user and role management
		insertUserRole(user, true);
		// Add user and post management
		insertUserPost(user, true);
		SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
		// Prevent accidental deletion of data after an incorrect update
		int flag = baseMapper.updateById(sysUser);
		if (flag < 1) {
			throw new ServiceException("Edit user " + user.getUserName() + " information failed");
		}
		return flag;
	}

	/**
	 * User authorization role
	 *
	 * @param userId  User ID
	 * @param roleIds role group
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertUserAuth(Long userId, Long[] roleIds) {
		insertUserRole(userId, roleIds, true);
	}

	/**
	 * modify user status
	 *
	 * @param userId User ID
	 * @param status account status
	 * @return result
	 */
	@Override
	public int updateUserStatus(Long userId, String status) {
		return baseMapper.update(null,
				new LambdaUpdateWrapper<SysUser>().set(SysUser::getStatus, status).eq(SysUser::getUserId, userId));
	}

	/**
	 * Modify basic user information
	 *
	 * @param user User Info
	 * @return result
	 */
	@Override
	public int updateUserProfile(SysUserBo user) {
		return baseMapper.update(null, new LambdaUpdateWrapper<SysUser>()
				.set(ObjectUtil.isNotNull(user.getNickName()), SysUser::getNickName, user.getNickName())
				.set(StringUtils.isNotEmpty(user.getPhonenumber()) && !user.getPhonenumber().contains("*"),
						SysUser::getPhonenumber, user.getPhonenumber())
				.set(StringUtils.isNotEmpty(user.getEmail()) && !user.getEmail().contains("*"), SysUser::getEmail,
						user.getEmail())
				.set(SysUser::getSex, user.getSex()).eq(SysUser::getUserId, user.getUserId()));
	}

	/**
	 * Modify user avatar
	 *
	 * @param userId User ID
	 * @param avatar Avatar address
	 * @return result
	 */
	@Override
	public boolean updateUserAvatar(Long userId, Long avatar) {
		return baseMapper.update(null,
				new LambdaUpdateWrapper<SysUser>().set(SysUser::getAvatar, avatar).eq(SysUser::getUserId, userId)) > 0;
	}

	/**
	 * reset user password
	 *
	 * @param userId   User ID
	 * @param password password
	 * @return result
	 */
	@Override
	public int resetUserPwd(Long userId, String password) {
		return baseMapper.update(null,
				new LambdaUpdateWrapper<SysUser>().set(SysUser::getPassword, password).eq(SysUser::getUserId, userId));
	}

	/**
	 * Add user role information
	 *
	 * @param user  user object
	 * @param clear Clear existing associated data
	 */
	private void insertUserRole(SysUserBo user, boolean clear) {
		this.insertUserRole(user.getUserId(), user.getRoleIds(), clear);
	}

	/**
	 * Add user job information
	 *
	 * @param user  user object
	 * @param clear Clear existing associated data
	 */
	private void insertUserPost(SysUserBo user, boolean clear) {
		Long[] posts = user.getPostIds();
		if (ArrayUtil.isNotEmpty(posts)) {
			if (clear) {
				// Delete user and position association
				userPostMapper
						.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, user.getUserId()));
			}
			// Add user and post management
			List<SysUserPost> list = StreamUtils.toList(List.of(posts), postId -> {
				SysUserPost up = new SysUserPost();
				up.setUserId(user.getUserId());
				up.setPostId(postId);
				return up;
			});
			userPostMapper.insertBatch(list);
		}
	}

	/**
	 * Add user role information
	 *
	 * @param userId  User ID
	 * @param roleIds role group
	 * @param clear   Clear existing associated data
	 */
	private void insertUserRole(Long userId, Long[] roleIds, boolean clear) {
		List<Long> canDoRoleList = null;
		if (ArrayUtil.isNotEmpty(roleIds)) {
			// Determine whether you have the operation authority of this role
			List<SysRoleVo> roles = roleMapper.selectRoleList(new LambdaQueryWrapper<>());
			if (CollUtil.isEmpty(roles)) {
				throw new ServiceException("Does not have permission to access role's data");
			}
			List<Long> roleList = StreamUtils.toList(roles, SysRoleVo::getRoleId);
			if (!LoginHelper.isSuperAdmin(userId)) {
				roleList.remove(UserConstants.SUPER_ADMIN_ID);
			}
			canDoRoleList = StreamUtils.filter(List.of(roleIds), roleList::contains);
			if (CollUtil.isEmpty(canDoRoleList)) {
				throw new ServiceException("Does not have permission to access role's data");
			}
		}
		if (clear) {
			// Delete user and role association
			userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
		}
		if (ArrayUtil.isNotEmpty(roleIds)) {
			// Added user and role management
			List<SysUserRole> list = StreamUtils.toList(canDoRoleList, roleId -> {
				SysUserRole ur = new SysUserRole();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				return ur;
			});
			userRoleMapper.insertBatch(list);
		}
	}

	/**
	 * Delete user by user ID
	 *
	 * @param userId User ID
	 * @return result
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteUserById(Long userId) {
		// Delete user and role association
		userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
		// Delete user and position table
		userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, userId));
		// Prevent data deletion due to failed updates
		int flag = baseMapper.deleteById(userId);
		if (flag < 1) {
			throw new ServiceException("Failed to delete user!");
		}
		return flag;
	}

	/**
	 * Batch delete user information
	 *
	 * @param userIds User ID to delete
	 * @return result
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteUserByIds(Long[] userIds) {
		for (Long userId : userIds) {
			checkUserAllowed(userId);
			checkUserDataScope(userId);
		}
		List<Long> ids = List.of(userIds);
		// Delete user and role association
		userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, ids));
		// Delete user and position table
		userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getUserId, ids));
		// Prevent data deletion due to failed updates
		int flag = baseMapper.deleteBatchIds(ids);
		if (flag < 1) {
			throw new ServiceException("Failed to delete user!");
		}
		return flag;
	}

	@Cacheable(cacheNames = CacheNames.SYS_USER_NAME, key = "#userId")
	@Override
	public String selectUserNameById(Long userId) {
		SysUser sysUser = baseMapper.selectOne(
				new LambdaQueryWrapper<SysUser>().select(SysUser::getUserName).eq(SysUser::getUserId, userId));
		return ObjectUtil.isNull(sysUser) ? null : sysUser.getUserName();
	}

	@Override
	public List<SysUserVo> selectShippingLineUserByOprAndBusinessUnit(String opr, String businessUnit) {
		return baseMapper.selectShippingLineUserByOprAndBusinessUnit(opr, businessUnit);
	}
}
