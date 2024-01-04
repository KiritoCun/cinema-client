package vn.udn.dut.cinema.admin.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.excel.core.ExcelResult;
import vn.udn.dut.cinema.common.excel.utils.ExcelUtil;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.constant.SystemConstants;
import vn.udn.dut.cinema.system.domain.bo.SysDeptBo;
import vn.udn.dut.cinema.system.domain.bo.SysUserBo;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserExportVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserImportVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserInfoVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;
import vn.udn.dut.cinema.system.domain.vo.UserInfoVo;
import vn.udn.dut.cinema.system.listener.SysUserImportListener;
import vn.udn.dut.cinema.system.service.ISysDeptService;
import vn.udn.dut.cinema.system.service.ISysPostService;
import vn.udn.dut.cinema.system.service.ISysRoleService;
import vn.udn.dut.cinema.system.service.ISysTenantService;
import vn.udn.dut.cinema.system.service.ISysUserService;

/**
 * User Info
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

	private final ISysUserService userService;
	private final ISysRoleService roleService;
	private final ISysPostService postService;
	private final ISysDeptService deptService;
	private final ISysTenantService tenantService;

	/**
	 * get user list
	 */
	@SaCheckPermission("system:user:list")
	@GetMapping("/list")
	public TableDataInfo<SysUserVo> list(SysUserBo user, PageQuery pageQuery) {
		user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
		return userService.selectPageUserList(user, pageQuery);
	}

	/**
	 * export user list
	 */
	@Log(title = "User management", businessType = BusinessType.EXPORT)
	@SaCheckPermission("system:user:export")
	@PostMapping("/export")
	public void export(SysUserBo user, HttpServletResponse response) {
		user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
		List<SysUserVo> list = userService.selectUserList(user);
		List<SysUserExportVo> listVo = MapstructUtils.convert(list, SysUserExportVo.class);
		ExcelUtil.exportExcel(listVo, "user_data", SysUserExportVo.class, response);
	}

	/**
	 * Import Data
	 *
	 * @param file          Importing files
	 * @param updateSupport Whether to update existing data
	 */
	@Log(title = "User management", businessType = BusinessType.IMPORT)
	@SaCheckPermission("system:user:import")
	@PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public R<Void> importData(@RequestPart("file") MultipartFile file, boolean updateSupport) throws Exception {
		ExcelResult<SysUserImportVo> result = ExcelUtil.importExcel(file.getInputStream(), SysUserImportVo.class,
				new SysUserImportListener(updateSupport));
		return R.ok(result.getAnalysis());
	}

	/**
	 * Get Import Template
	 */
	@PostMapping("/importTemplate")
	public void importTemplate(HttpServletResponse response) {
		ExcelUtil.exportExcel(new ArrayList<>(), "user_data", SysUserImportVo.class, response);
	}

	/**
	 * Get user information
	 *
	 * @return User Info
	 */
	@GetMapping("/getInfo")
	public R<UserInfoVo> getInfo() {
		UserInfoVo userInfoVo = new UserInfoVo();
		LoginUser loginUser = LoginHelper.getLoginUser();
		if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
			// Super administrators need to clear dynamic tenants if user information is
			// reloaded
			TenantHelper.clearDynamic();
		}
		SysUserVo user = userService.selectUserById(loginUser.getUserId());
		userInfoVo.setUser(user);
		userInfoVo.setPermissions(loginUser.getMenuPermission());
		userInfoVo.setRoles(loginUser.getRolePermission());
		return R.ok(userInfoVo);
	}

	/**
	 * Get detailed information based on user ID
	 *
	 * @param userId User ID
	 */
	@SaCheckPermission("system:user:query")
	@GetMapping(value = { "/", "/{userId}" })
	public R<SysUserInfoVo> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
		userService.checkUserDataScope(userId);
		SysUserInfoVo userInfoVo = new SysUserInfoVo();
		List<SysRoleVo> roles = roleService.selectRoleAll(SystemConstants.SYSTEM_TYPE_SYSTEM);
		userInfoVo
				.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
		userInfoVo.setPosts(postService.selectPostAll());
		if (ObjectUtil.isNotNull(userId)) {
			SysUserVo sysUser = userService.selectUserById(userId);
			userInfoVo.setUser(sysUser);
			userInfoVo.setRoleIds(StreamUtils.toList(sysUser.getRoles(), SysRoleVo::getRoleId));
			userInfoVo.setPostIds(postService.selectPostListByUserId(userId));
		}
		return R.ok(userInfoVo);
	}

	/**
	 * New users
	 */
	@SaCheckPermission("system:user:add")
	@Log(title = "User management", businessType = BusinessType.INSERT)
	@PostMapping
	public R<Void> add(@Validated @RequestBody SysUserBo user) {
		user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
		if (!userService.checkUserNameUnique(user)) {
			return R.fail("Add new user '" + user.getUserName() + "' failed, the login account already exists");
		} else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
			return R.fail("Add new user '" + user.getUserName() + "' failed, the phone number already exists");
		} else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
			return R.fail("Add new user '" + user.getUserName() + "' failed, email account already exists");
		}
		if (TenantHelper.isEnable()) {
			if (!tenantService.checkAccountBalance(TenantHelper.getTenantId())) {
				return R.fail(
						"The number of users under the current tenant is insufficient, please contact the administrator");
			}
		}
		user.setPassword(BCrypt.hashpw(user.getPassword()));
		return toAjax(userService.insertUser(user));
	}

	/**
	 * modify user
	 */
	@SaCheckPermission("system:user:edit")
	@Log(title = "User management", businessType = BusinessType.UPDATE)
	@PutMapping
	public R<Void> edit(@Validated @RequestBody SysUserBo user) {
		user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
		userService.checkUserAllowed(user.getUserId());
		userService.checkUserDataScope(user.getUserId());
		if (!userService.checkUserNameUnique(user)) {
			return R.fail("Edit user '" + user.getUserName() + "' failed, the login account already exists");
		} else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
			return R.fail("Edit user '" + user.getUserName() + "' failed, phone number already exists");
		} else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
			return R.fail("Edit user '" + user.getUserName() + "' failed, email account already exists");
		}
		return toAjax(userService.updateUser(user));
	}

	/**
	 * delete users
	 *
	 * @param userIds Character ID string
	 */
	@SaCheckPermission("system:user:remove")
	@Log(title = "User management", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
	public R<Void> remove(@PathVariable Long[] userIds) {
		if (ArrayUtil.contains(userIds, LoginHelper.getUserId())) {
			return R.fail("Current user cannot be deleted");
		}
		return toAjax(userService.deleteUserByIds(userIds));
	}

	/**
	 * reset Password
	 */
	@SaCheckPermission("system:user:resetPwd")
	@Log(title = "User management", businessType = BusinessType.UPDATE)
	@PutMapping("/resetPwd")
	public R<Void> resetPwd(@RequestBody SysUserBo user) {
		user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
		userService.checkUserAllowed(user.getUserId());
		userService.checkUserDataScope(user.getUserId());
		user.setPassword(BCrypt.hashpw(user.getPassword()));
		return toAjax(userService.resetUserPwd(user.getUserId(), user.getPassword()));
	}

	/**
	 * status modification
	 */
	@SaCheckPermission("system:user:edit")
	@Log(title = "User management", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public R<Void> changeStatus(@RequestBody SysUserBo user) {
		user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
		userService.checkUserAllowed(user.getUserId());
		userService.checkUserDataScope(user.getUserId());
		return toAjax(userService.updateUserStatus(user.getUserId(), user.getStatus()));
	}

	/**
	 * Obtain the authorized role according to the user ID
	 *
	 * @param userId User ID
	 */
	@SaCheckPermission("system:user:query")
	@GetMapping("/authRole/{userId}")
	public R<SysUserInfoVo> authRole(@PathVariable Long userId) {
		SysUserVo user = userService.selectUserById(userId);
		List<SysRoleVo> roles = roleService.selectRolesByUserId(userId, SystemConstants.SYSTEM_TYPE_SYSTEM);
		SysUserInfoVo userInfoVo = new SysUserInfoVo();
		userInfoVo.setUser(user);
		userInfoVo
				.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
		return R.ok(userInfoVo);
	}

	/**
	 * User authorization role
	 *
	 * @param userId  User ID
	 * @param roleIds Character ID string
	 */
	@SaCheckPermission("system:user:edit")
	@Log(title = "User management", businessType = BusinessType.GRANT)
	@PutMapping("/authRole")
	public R<Void> insertAuthRole(Long userId, Long[] roleIds) {
		userService.checkUserDataScope(userId);
		userService.insertUserAuth(userId, roleIds);
		return R.ok();
	}

	/**
	 * Get a list of department trees
	 */
	@SaCheckPermission("system:user:list")
	@GetMapping("/deptTree")
	public R<List<Tree<Long>>> deptTree(SysDeptBo dept) {
		return R.ok(deptService.selectDeptTreeList(dept));
	}
}
