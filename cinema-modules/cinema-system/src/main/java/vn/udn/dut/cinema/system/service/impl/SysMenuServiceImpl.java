package vn.udn.dut.cinema.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.core.utils.TreeBuildUtils;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.system.domain.SysMenu;
import vn.udn.dut.cinema.system.domain.SysRole;
import vn.udn.dut.cinema.system.domain.SysRoleMenu;
import vn.udn.dut.cinema.system.domain.SysTenantPackage;
import vn.udn.dut.cinema.system.domain.bo.SysMenuBo;
import vn.udn.dut.cinema.system.domain.vo.MetaVo;
import vn.udn.dut.cinema.system.domain.vo.RouterVo;
import vn.udn.dut.cinema.system.domain.vo.SysMenuVo;
import vn.udn.dut.cinema.system.mapper.SysMenuMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleMenuMapper;
import vn.udn.dut.cinema.system.mapper.SysTenantPackageMapper;
import vn.udn.dut.cinema.system.service.ISysMenuService;

/**
 * Menu business layer processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    private final SysMenuMapper baseMapper;
    private final SysRoleMapper roleMapper;
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysTenantPackageMapper tenantPackageMapper;

    /**
     * Query the system menu list according to the user
     *
     * @param userId User ID
     * @return menu list
     */
    @Override
    public List<SysMenuVo> selectMenuList(Long userId, String systemType) {
        SysMenuBo bo = new SysMenuBo();
        bo.setSystemType(systemType);
    	return selectMenuList(bo, userId);
    }

    /**
     * Query the system menu list
     *
     * @param menu menu information
     * @return menu list
     */
    @Override
    public List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId) {
        List<SysMenuVo> menuList;
        // Admin shows all menu information
        if (LoginHelper.isSuperAdmin(userId)) {
            menuList = baseMapper.selectVoList(new LambdaQueryWrapper<SysMenu>()
                .like(StringUtils.isNotBlank(menu.getMenuName()), SysMenu::getMenuName, menu.getMenuName())
                .eq(StringUtils.isNotBlank(menu.getVisible()), SysMenu::getVisible, menu.getVisible())
                .eq(StringUtils.isNotBlank(menu.getStatus()), SysMenu::getStatus, menu.getStatus())
                .eq(StringUtils.isNotBlank(menu.getSystemType()), SysMenu::getSystemType, menu.getSystemType())
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getOrderNum));
        } else {
            QueryWrapper<SysMenu> wrapper = Wrappers.query();
            wrapper.eq("sur.user_id", userId)
                .like(StringUtils.isNotBlank(menu.getMenuName()), "m.menu_name", menu.getMenuName())
                .eq(StringUtils.isNotBlank(menu.getVisible()), "m.visible", menu.getVisible())
                .eq(StringUtils.isNotBlank(menu.getStatus()), "m.status", menu.getStatus())
                .eq(StringUtils.isNotBlank(menu.getSystemType()), "m.system_type", menu.getSystemType())
                .orderByAsc("m.parent_id")
                .orderByAsc("m.order_num");
            List<SysMenu> list = baseMapper.selectMenuListByUserId(wrapper);
            menuList = MapstructUtils.convert(list, SysMenuVo.class);
        }
        return menuList;
    }

    /**
     * Query permissions based on user ID
     *
     * @param userId User ID
     * @return menu information
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = baseMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.trim()));
            }
        }
        return permsSet;
    }

    /**
     * Query permissions based on role ID
     *
     * @param roleId Role ID
     * @return menu information
     */
    @Override
    public Set<String> selectMenuPermsByRoleId(Long roleId) {
        List<String> perms = baseMapper.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.trim()));
            }
        }
        return permsSet;
    }

    /**
     * Query menu by user ID
     *
     * @param userId user name
     * @return menu list
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId, String systemTYpe) {
        List<SysMenu> menus;
        if (LoginHelper.isSuperAdmin(userId)) {
            menus = baseMapper.selectMenuTreeAll(systemTYpe);
        } else {
            menus = baseMapper.selectMenuTreeByUserId(userId, systemTYpe);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * Query menu tree information based on role ID
     *
     * @param roleId Role ID
     * @return Select menu list
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId, String systemType) {
        SysRole role = roleMapper.selectById(roleId);
        return baseMapper.selectMenuListByRoleId(roleId, role.getMenuCheckStrictly(), systemType);
    }

    /**
     * Query menu tree information based on tenant package ID
     *
     * @param packageId Tenant Package ID
     * @return Select menu list
     */
    @Override
    public List<Long> selectMenuListByPackageId(Long packageId) {
        SysTenantPackage tenantPackage = tenantPackageMapper.selectById(packageId);
        List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);
        if (CollUtil.isEmpty(menuIds)) {
            return List.of();
        }
        List<Long> parentIds = null;
        if (tenantPackage.getMenuCheckStrictly()) {
            parentIds = baseMapper.selectObjs(new LambdaQueryWrapper<SysMenu>()
                .select(SysMenu::getParentId)
                .in(SysMenu::getMenuId, menuIds), Convert::toLong);
        }
        return baseMapper.selectObjs(new LambdaQueryWrapper<SysMenu>()
            .in(SysMenu::getMenuId, menuIds)
            .notIn(CollUtil.isNotEmpty(parentIds), SysMenu::getMenuId, parentIds), Convert::toLong);
    }

    /**
     * The menu needed to build the front-end routing
     *
     * @param menus menu list
     * @return routing list
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(menu.getRouteName());
            router.setPath(menu.getRouterPath());
            router.setComponent(menu.getComponentInfo());
            router.setQuery(menu.getQueryParam());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if (CollUtil.isNotEmpty(cMenus) && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else if (menu.isMenuFrame()) {
                router.setMeta(null);
                router.setComponent(UserConstants.LAYOUT);
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponentInfo());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                children.setQuery(menu.getQueryParam());
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && menu.isInnerLink()) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<>();
                RouterVo children = new RouterVo();
                String routerPath = SysMenu.innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * Build the drop-down tree structure required by the front end
     *
     * @param menus menu list
     * @return drop down tree structure list
     */
    @Override
    public List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus) {
        if (CollUtil.isEmpty(menus)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(menus, (menu, tree) ->
            tree.setId(menu.getMenuId())
                .setParentId(menu.getParentId())
                .setName(menu.getMenuName())
                .setWeight(menu.getOrderNum()));
    }

    /**
     * Query information by menu ID
     *
     * @param menuId menu ID
     * @return menu information
     */
    @Override
    public SysMenuVo selectMenuById(Long menuId) {
        return baseMapper.selectVoById(menuId);
    }

    /**
     * Whether there is a menu child node
     *
     * @param menuId menu ID
     * @return result
     */
    @Override
    public boolean hasChildByMenuId(Long menuId) {
        return baseMapper.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId));
    }

    /**
     * Query the number of menu usage
     *
     * @param menuId menu ID
     * @return result
     */
    @Override
    public boolean checkMenuExistRole(Long menuId) {
        return roleMenuMapper.exists(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, menuId));
    }

    /**
     * Added save menu information
     *
     * @param bo menu information
     * @return result
     */
    @Override
    public int insertMenu(SysMenuBo bo) {
        SysMenu menu = MapstructUtils.convert(bo, SysMenu.class);
        return baseMapper.insert(menu);
    }

    /**
     * Modify save menu information
     *
     * @param bo menu information
     * @return result
     */
    @Override
    public int updateMenu(SysMenuBo bo) {
        SysMenu menu = MapstructUtils.convert(bo, SysMenu.class);
        return baseMapper.updateById(menu);
    }

    /**
     * Delete menu management information
     *
     * @param menuId menu ID
     * @return result
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return baseMapper.deleteById(menuId);
    }

    /**
     * Check if the menu name is unique
     *
     * @param menu menu information
     * @return result
     */
    @Override
    public boolean checkMenuNameUnique(SysMenuBo menu) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysMenu>()
            .eq(SysMenu::getMenuName, menu.getMenuName())
            .eq(SysMenu::getParentId, menu.getParentId())
            .eq(SysMenu::getSystemType, menu.getSystemType())
            .eq(StringUtils.isNotBlank(menu.getSystemType()), SysMenu::getSystemType, menu.getSystemType())
            .ne(ObjectUtil.isNotNull(menu.getMenuId()), SysMenu::getMenuId, menu.getMenuId()));
        return !exist;
    }

    /**
     * Get all child nodes based on the ID of the parent node
     *
     * @param list     classification table
     * @param parentId Incoming parent node ID
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 1. According to the ID of a parent node passed in, traverse all the child nodes of the parent node
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * recursive list
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // get list of child nodes
        List<SysMenu> childList = StreamUtils.filter(list, n -> n.getParentId().equals(t.getMenuId()));
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            // Determine whether there are child nodes
            if (list.stream().anyMatch(n -> n.getParentId().equals(tChild.getMenuId()))) {
                recursionFn(list, tChild);
            }
        }
    }

}
