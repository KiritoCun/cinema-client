package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * User and role association sys_user_role
 *
 * @author HoaLD
 */

@Data
@TableName("sys_user_role")
public class SysUserRole {

    /**
     * User ID
     */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /**
     * Role ID
     */
    private Long roleId;

}
