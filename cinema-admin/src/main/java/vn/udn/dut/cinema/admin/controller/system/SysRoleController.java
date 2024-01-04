package vn.udn.dut.cinema.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.excel.utils.ExcelUtil;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.constant.SystemConstants;
import vn.udn.dut.cinema.system.domain.SysUserRole;
import vn.udn.dut.cinema.system.domain.bo.SysDeptBo;
import vn.udn.dut.cinema.system.domain.bo.SysRoleBo;
import vn.udn.dut.cinema.system.domain.bo.SysUserBo;
import vn.udn.dut.cinema.system.domain.vo.DeptTreeSelectVo;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;
import vn.udn.dut.cinema.system.service.ISysDeptService;
import vn.udn.dut.cinema.system.service.ISysRoleService;
import vn.udn.dut.cinema.system.service.ISysUserService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * role information
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    private final ISysRoleService roleService;
    private final ISysUserService userService;
    private final ISysDeptService deptService;

    /**
     * Get a list of role information
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public TableDataInfo<SysRoleVo> list(SysRoleBo role, PageQuery pageQuery) {
    	role.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        return roleService.selectPageRoleList(role, pageQuery);
    }

    /**
     * Export role information list
     */
    @Log(title = "Role management", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:role:export")
    @PostMapping("/export")
    public void export(SysRoleBo role, HttpServletResponse response) {
    	role.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        List<SysRoleVo> list = roleService.selectRoleList(role);
        ExcelUtil.exportExcel(list, "role_list", SysRoleVo.class, response);
    }

    /**
     * Get detailed information by role number
     *
     * @param roleId character ID
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = "/{roleId}")
    public R<SysRoleVo> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return R.ok(roleService.selectRoleById(roleId));
    }

    /**
     * new role
     */
    @SaCheckPermission("system:role:add")
    @Log(title = "Role management", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysRoleBo role) {
    	role.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        if (!roleService.checkRoleNameUnique(role)) {
            return R.fail("Add new role '" + role.getRoleName() + "' failed, the role name already exists");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return R.fail("Add new role '" + role.getRoleName() + "' failed, the role permission already exists");
        }
        return toAjax(roleService.insertRole(role));

    }

    /**
     * modify save role
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role.getRoleId());
        roleService.checkRoleDataScope(role.getRoleId());
        role.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        if (!roleService.checkRoleNameUnique(role)) {
            return R.fail("Edit role '" + role.getRoleName() + "' failed, role name already exists");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return R.fail("Edit role '" + role.getRoleName() + "' failed, role permission already exists");
        }

        if (roleService.updateRole(role) > 0) {
            roleService.cleanOnlineUserByRole(role.getRoleId());
            return R.ok();
        }
        return R.fail("Edit role '" + role.getRoleName() + "' failed, please contact the administrator");
    }

    /**
     * Modify permission to save data
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public R<Void> dataScope(@RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role.getRoleId());
        roleService.checkRoleDataScope(role.getRoleId());
        return toAjax(roleService.authDataScope(role));
    }

    /**
     * status modification
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role.getRoleId());
        roleService.checkRoleDataScope(role.getRoleId());
        return toAjax(roleService.updateRoleStatus(role.getRoleId(), role.getStatus()));
    }

    /**
     * delete role
     *
     * @param roleIds Character ID string
     */
    @SaCheckPermission("system:role:remove")
    @Log(title = "Role management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public R<Void> remove(@PathVariable Long[] roleIds) {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * Get the role selection box list
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/optionselect")
    public R<List<SysRoleVo>> optionselect() {
        return R.ok(roleService.selectRoleAll(SystemConstants.SYSTEM_TYPE_SYSTEM));
    }

    /**
     * Query the list of assigned user roles
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo<SysUserVo> allocatedList(SysUserBo user, PageQuery pageQuery) {
        user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
    	return userService.selectAllocatedList(user, pageQuery);
    }

    /**
     * Query the list of unassigned user roles
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo<SysUserVo> unallocatedList(SysUserBo user, PageQuery pageQuery) {
    	user.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
    	return userService.selectUnallocatedList(user, pageQuery);
    }

    /**
     * deauthorize user
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public R<Void> cancelAuthUser(@RequestBody SysUserRole userRole) {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * Bulk deauthorize users
     *
     * @param roleId  character ID
     * @param userIds User ID string
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public R<Void> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * Batch selection of user authorization
     *
     * @param roleId  character ID
     * @param userIds User ID string
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "Role management", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public R<Void> selectAuthUserAll(Long roleId, Long[] userIds) {
        roleService.checkRoleDataScope(roleId);
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }

    /**
     * Get the corresponding role department tree list
     *
     * @param roleId character ID
     */
    @SaCheckPermission("system:role:list")
    @GetMapping(value = "/deptTree/{roleId}")
    public R<DeptTreeSelectVo> roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        DeptTreeSelectVo selectVo = new DeptTreeSelectVo();
        selectVo.setCheckedKeys(deptService.selectDeptListByRoleId(roleId));
        selectVo.setDepts(deptService.selectDeptTreeList(new SysDeptBo()));
        return R.ok(selectVo);
    }
}
