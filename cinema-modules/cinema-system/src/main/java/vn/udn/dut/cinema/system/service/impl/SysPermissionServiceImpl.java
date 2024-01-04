package vn.udn.dut.cinema.system.service.impl;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.TenantConstants;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.system.service.ISysMenuService;
import vn.udn.dut.cinema.system.service.ISysPermissionService;
import vn.udn.dut.cinema.system.service.ISysRoleService;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * User permission handling
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    private final ISysRoleService roleService;
    private final ISysMenuService menuService;

    /**
     * Get role data permission
     *
     * @param userId  user id
     * @return Role permission information
     */
    @Override
    public Set<String> getRolePermission(Long userId) {
        Set<String> roles = new HashSet<>();
        // Administrators have all permissions
        if (LoginHelper.isSuperAdmin(userId)) {
            roles.add(TenantConstants.SUPER_ADMIN_ROLE_KEY);
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(userId));
        }
        return roles;
    }

    /**
     * Get menu data permissions
     *
     * @param userId  user id
     * @return Menu permission information
     */
    @Override
    public Set<String> getMenuPermission(Long userId) {
        Set<String> perms = new HashSet<>();
        // Administrators have all permissions
        if (LoginHelper.isSuperAdmin(userId)) {
            perms.add("*:*:*");
        } else {
            perms.addAll(menuService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }
}
