package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.constant.Constants;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu permission table sys_menu
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3137132635640597264L;

	/**
     * menu ID
     */
    @TableId(value = "menu_id")
    private Long menuId;

    /**
     * parent menu id
     */
    private Long parentId;

    /**
     * menu name
     */
    private String menuName;

    /**
     * display order
     */
    private Integer orderNum;

    /**
     * routing address
     */
    private String path;

    /**
     * component path
     */
    private String component;

    /**
     * routing parameters
     */
    private String queryParam;

    /**
     * Whether it is an external link (0 is 1 is not)
     */
    private String isFrame;

    /**
     * Whether to cache (0 cache, 1 not cache)
     */
    private String isCache;

    /**
     * Type (M catalog C menu F button)
     */
    private String menuType;

    /**
     * Show status (0 show 1 hide)
     */
    private String visible;

    /**
     * Menu status (0 normal 1 disabled)
     */
    private String status;

    /**
     * permission string
     */
    private String perms;

    /**
     * menu icon
     */
    private String icon;

    /**
     * Remark
     */
    private String remark;

    /**
     * System type
     */
    private String systemType;

    /**
     * parent menu name
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * Submenu
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    /**
     * get route name
     */
    public String getRouteName() {
        String routerName = StringUtils.capitalize(path);
        // It is not an external link and is a first-level directory (type is directory)
        if (isMenuFrame()) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * Get routing address
     */
    public String getRouterPath() {
        String routerPath = this.path;
        // How to open the external network from the internal link
        if (getParentId() != 0L && isInnerLink()) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // It is not an external link and is a first-level directory (type is directory)
        if (0L == getParentId() && UserConstants.TYPE_DIR.equals(getMenuType())
            && UserConstants.NO_FRAME.equals(getIsFrame())) {
            routerPath = "/" + this.path;
        }
        // It is not an external link and is a first-level directory (the type is menu)
        else if (isMenuFrame()) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * Get component information
     */
    public String getComponentInfo() {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(this.component)) {
            component = this.component;
        } else if (StringUtils.isEmpty(this.component) && getParentId() != 0L && isInnerLink()) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(this.component) && isParentView()) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * Whether to jump inside the menu
     */
    public boolean isMenuFrame() {
        return getParentId() == 0L && UserConstants.TYPE_MENU.equals(menuType) && isFrame.equals(UserConstants.NO_FRAME);
    }

    /**
     * Whether it is an internal link component
     */
    public boolean isInnerLink() {
        return isFrame.equals(UserConstants.NO_FRAME) && StringUtils.ishttp(path);
    }

    /**
     * Whether it is parent_view component
     */
    public boolean isParentView() {
        return getParentId() != 0L && UserConstants.TYPE_DIR.equals(menuType);
    }

    /**
     * Inner chain domain name special character replacement
     */
    public static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
            new String[]{"", "", "", "/"});
    }
}
