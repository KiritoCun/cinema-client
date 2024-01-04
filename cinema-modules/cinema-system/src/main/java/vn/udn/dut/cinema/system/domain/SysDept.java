package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * Department table sys_dept
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Department ID
     */
    @TableId(value = "dept_id")
    private Long deptId;

    /**
     * Parent Department ID
     */
    private Long parentId;

    /**
     * Department name
     */
    private String deptName;

    /**
     * display order
     */
    private Integer orderNum;

    /**
     * principal
     */
    private String leader;

    /**
     * contact number
     */
    private String phone;

    /**
     * Mail
     */
    private String email;

    /**
     * Sector Status: 0 Normal, 1 Disabled
     */
    private String status;

    /**
     * Delete flag (0 means exist, 2 means delete)
     */
    @TableLogic
    private String delFlag;

    /**
     * ancestor list
     */
    private String ancestors;

}
