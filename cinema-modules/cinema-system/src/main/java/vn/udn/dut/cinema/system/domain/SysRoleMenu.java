package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Role and menu association sys_role_menu
 *
 * @author HoaLD
 */

@Data
@TableName("sys_role_menu")
public class SysRoleMenu {

    /**
     * character ID
     */
    @TableId(type = IdType.INPUT)
    private Long roleId;

    /**
     * menu ID
     */
    private Long menuId;

}
