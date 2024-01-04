package vn.udn.dut.cinema.system.service;

import cn.hutool.core.lang.tree.Tree;
import vn.udn.dut.cinema.system.domain.SysMenu;
import vn.udn.dut.cinema.system.domain.bo.SysMenuBo;
import vn.udn.dut.cinema.system.domain.vo.RouterVo;
import vn.udn.dut.cinema.system.domain.vo.SysMenuVo;

import java.util.List;
import java.util.Set;

/**
 * menu business layer
 *
 * @author HoaLD
 */
public interface ISysMenuService {

    /**
     * Query the system menu list according to the user
     *
     * @param userId User ID
     * @return menu list
     */
    List<SysMenuVo> selectMenuList(Long userId, String systemType);

    /**
     * Query the system menu list according to the user
     *
     * @param menu   menu information
     * @param userId User ID
     * @return menu list
     */
    List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId);

    /**
     * Query permissions based on user ID
     *
     * @param userId User ID
     * @return menu information
     */
    Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * Query permissions based on role ID
     *
     * @param roleId Role ID
     * @return menu information
     */
    Set<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * Query menu tree information based on user ID
     *
     * @param userId User ID
     * @return menu list
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId, String systemType);

    /**
     * Query menu tree information based on role ID
     *
     * @param roleId Role ID
     * @return Select menu list
     */
    List<Long> selectMenuListByRoleId(Long roleId, String systemType);

    /**
     * Query menu tree information based on tenant package ID
     *
     * @param packageId Tenant Package ID
     * @return Select menu list
     */
    List<Long> selectMenuListByPackageId(Long packageId);

    /**
     * The menu needed to build the front-end routing
     *
     * @param menus menu list
     * @return routing list
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

    /**
     * Build the drop-down tree structure required by the front end
     *
     * @param menus menu list
     * @return drop down tree structure list
     */
    List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus);

    /**
     * Query information by menu ID
     *
     * @param menuId menu ID
     * @return menu information
     */
    SysMenuVo selectMenuById(Long menuId);

    /**
     * Whether there is a menu child node
     *
     * @param menuId menu ID
     * @return result true exists false does not exist
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * Query whether there is a role in the menu
     *
     * @param menuId menu ID
     * @return result true exists false does not exist
     */
    boolean checkMenuExistRole(Long menuId);

    /**
     * Added save menu information
     *
     * @param bo menu information
     * @return result
     */
    int insertMenu(SysMenuBo bo);

    /**
     * Modify save menu information
     *
     * @param bo menu information
     * @return result
     */
    int updateMenu(SysMenuBo bo);

    /**
     * Delete menu management information
     *
     * @param menuId menu ID
     * @return result
     */
    int deleteMenuById(Long menuId);

    /**
     * Check if the menu name is unique
     *
     * @param menu menu information
     * @return result
     */
    boolean checkMenuNameUnique(SysMenuBo menu);
}
