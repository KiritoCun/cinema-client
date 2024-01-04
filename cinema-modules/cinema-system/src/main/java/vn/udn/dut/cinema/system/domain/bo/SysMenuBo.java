package vn.udn.dut.cinema.system.domain.bo;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysMenu;

/**
 * Menu Authority Business Object sys_menu
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysMenu.class, reverseConvertGenerate = false)
public class SysMenuBo extends BaseEntity {

    private static final long serialVersionUID = -2323039814896662171L;

	/**
     * menu ID
     */
    @NotNull(message = "Menu id cannot be empty", groups = { EditGroup.class })
    private Long menuId;

    /**
     * parent menu id
     */
    private Long parentId;

    /**
     * menu name
     */
    @NotBlank(message = "Menu name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 500, message = "The menu name cannot exceed {max} characters")
    private String menuName;

    /**
     * display order
     */
    @NotNull(message = "Display order cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private Integer orderNum;

    /**
     * routing address
     */
    @Size(min = 0, max = 200, message = "Routing address cannot exceed {max} characters")
    private String path;

    /**
     * component path
     */
    @Size(min = 0, max = 200, message = "Component path cannot exceed {max} characters")
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
    @NotBlank(message = "Menu type cannot be empty", groups = { AddGroup.class, EditGroup.class })
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 0, max = 100, message = "The length of the permission id cannot exceed {max} characters")
    private String perms;

    /**
     * menu icon
     */
    private String icon;

    /**
     * 
     */
    private String systemType;
    
    /**
     * Remark
     */
    private String remark;


}
