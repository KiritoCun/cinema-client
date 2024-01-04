package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Role and department association sys_role_dept
 *
 * @author HoaLD
 */

@Data
@TableName("sys_role_dept")
public class SysRoleDept {

    /**
     * character ID
     */
    @TableId(type = IdType.INPUT)
    private Long roleId;

    /**
     * Department ID
     */
    private Long deptId;

}
