package vn.udn.dut.cinema.system.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysMenu;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Menu permission view object sys_menu
 *
 * @author HoaLD
 */
@Data
@AutoMapper(target = SysMenu.class)
public class SysMenuVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * menu ID
     */
    private Long menuId;

    /**
     * menu name
     */
    private String menuName;

    /**
     * parent menu id
     */
    private Long parentId;

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
     * Menu type (M catalog C menu F button)
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
     * Authority ID
     */
    private String perms;

    /**
     * menu icon
     */
    private String icon;

    /**
     * create department
     */
    private Long createDept;

    /**
     * Remark
     */
    private String remark;

    /**
     * Creation time
     */
    private Date createTime;

    /**
     * Submenu
     */
    private List<SysMenuVo> children = new ArrayList<>();

}
