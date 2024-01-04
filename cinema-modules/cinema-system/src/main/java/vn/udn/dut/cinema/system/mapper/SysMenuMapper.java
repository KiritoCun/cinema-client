package vn.udn.dut.cinema.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysMenu;
import vn.udn.dut.cinema.system.domain.vo.SysMenuVo;

/**
 * menu table data layer
 *
 * @author HoaLD
 */
public interface SysMenuMapper extends BaseMapperPlus<SysMenu, SysMenuVo> {

    /**
     * According to all permissions of the user
     *
     * @return permission list
     */
    List<String> selectMenuPerms();

    /**
     * Query the system menu list according to the user
     *
     * @param queryWrapper Query conditions
     * @return menu list
     */
    List<SysMenu> selectMenuListByUserId(@Param(Constants.WRAPPER) Wrapper<SysMenu> queryWrapper);

    /**
     * Query permissions based on user ID
     *
     * @param userId User ID
     * @return permission list
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * Query permissions based on role ID
     *
     * @param roleId character ID
     * @return permission list
     */
    List<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * Query menu by user ID
     *
     * @return menu list
     */
    default List<SysMenu> selectMenuTreeAll(String systemType) {
        LambdaQueryWrapper<SysMenu> lqw = new LambdaQueryWrapper<SysMenu>()
            .in(SysMenu::getMenuType, UserConstants.TYPE_DIR, UserConstants.TYPE_MENU)
            .eq(SysMenu::getStatus, UserConstants.MENU_NORMAL)
            .eq(SysMenu::getSystemType, systemType)
            .orderByAsc(SysMenu::getParentId)
            .orderByAsc(SysMenu::getOrderNum);
        return this.selectList(lqw);
    }

    /**
     * Query menu by user ID
     *
     * @param userId User ID
     * @return menu list
     */
    List<SysMenu> selectMenuTreeByUserId(@Param("userId") Long userId, @Param("systemType") String systemType);

    /**
     * Query menu tree information based on role ID
     *
     * @param roleId            character ID
     * @param menuCheckStrictly Whether the menu tree selection items are displayed in association
     * @return Select menu list
     */
    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly, @Param("systemType") String systemType);

}
