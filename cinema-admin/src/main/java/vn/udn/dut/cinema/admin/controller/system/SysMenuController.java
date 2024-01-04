package vn.udn.dut.cinema.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.TenantConstants;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.constant.SystemConstants;
import vn.udn.dut.cinema.system.domain.SysMenu;
import vn.udn.dut.cinema.system.domain.bo.SysMenuBo;
import vn.udn.dut.cinema.system.domain.vo.MenuTreeSelectVo;
import vn.udn.dut.cinema.system.domain.vo.RouterVo;
import vn.udn.dut.cinema.system.domain.vo.SysMenuVo;
import vn.udn.dut.cinema.system.service.ISysMenuService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * menu information
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    private final ISysMenuService menuService;

    /**
     * Get routing information
     *
     * @return routing information
     */
    @GetMapping("/getRouters")
    public R<List<RouterVo>> getRouters() {
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(LoginHelper.getUserId(), SystemConstants.SYSTEM_TYPE_SYSTEM);
        return R.ok(menuService.buildMenus(menus));
    }

    /**
     * get menu list
     */
    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    public R<List<SysMenuVo>> list(SysMenuBo menu) {
    	menu.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        List<SysMenuVo> menus = menuService.selectMenuList(menu, LoginHelper.getUserId());
        return R.ok(menus);
    }

    /**
     * Get detailed information by menu number
     *
     * @param menuId menu ID
     */
    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public R<SysMenuVo> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.selectMenuById(menuId));
    }

    /**
     * Get menu dropdown tree list
     */
    @SaCheckRole(value = {
            TenantConstants.SUPER_ADMIN_ROLE_KEY,
            TenantConstants.TENANT_ADMIN_ROLE_KEY
    }, mode = SaMode.OR)
    @SaCheckPermission("system:menu:query")
    @GetMapping("/treeselect")
    public R<List<Tree<Long>>> treeselect(SysMenuBo menu) {
    	menu.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        List<SysMenuVo> menus = menuService.selectMenuList(menu, LoginHelper.getUserId());
        return R.ok(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * Load the corresponding role menu list tree
     *
     * @param roleId character ID
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public R<MenuTreeSelectVo> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenuVo> menus = menuService.selectMenuList(LoginHelper.getUserId(), SystemConstants.SYSTEM_TYPE_SYSTEM);
        MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
        selectVo.setCheckedKeys(menuService.selectMenuListByRoleId(roleId, SystemConstants.SYSTEM_TYPE_SYSTEM));
        selectVo.setMenus(menuService.buildMenuTreeSelect(menus));
        return R.ok(selectVo);
    }

    /**
     * Load the corresponding tenant package menu list tree
     *
     * @param packageId Tenant Package ID
     */
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/tenantPackageMenuTreeselect/{packageId}")
    public R<MenuTreeSelectVo> tenantPackageMenuTreeselect(@PathVariable("packageId") Long packageId) {
        List<SysMenuVo> menus = menuService.selectMenuList(LoginHelper.getUserId(), SystemConstants.SYSTEM_TYPE_SYSTEM);
        MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
        selectVo.setCheckedKeys(menuService.selectMenuListByPackageId(packageId));
        selectVo.setMenus(menuService.buildMenuTreeSelect(menus));
        return R.ok(selectVo);
    }

    /**
     * new menu
     */
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:add")
    @Log(title = "Menu management", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysMenuBo menu) {
    	menu.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        if (!menuService.checkMenuNameUnique(menu)) {
            return R.fail("Add menu '" + menu.getMenuName() + "' failed, the menu name already exists");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return R.fail("Add menu'" + menu.getMenuName() + "' failed, the address must start with http(s)://");
        }
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * edit menu
     */
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:edit")
    @Log(title = "Menu management", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysMenuBo menu) {
    	menu.setSystemType(SystemConstants.SYSTEM_TYPE_SYSTEM);
        if (!menuService.checkMenuNameUnique(menu)) {
            return R.fail("Edit menu'" + menu.getMenuName() + "' failed, menu name already exists");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return R.fail("Edit menu '" + menu.getMenuName() + "' failed, address must start with http(s)://");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return R.fail("Edit menu '" + menu.getMenuName() + "' failed, the parent menu cannot select itself");
        }
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * delete menu
     *
     * @param menuId menu ID
     */
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:remove")
    @Log(title = "Menu management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public R<Void> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.warn("There is a submenu, it is not allowed to delete");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.warn("The menu is already assigned and cannot be deleted");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }

}
