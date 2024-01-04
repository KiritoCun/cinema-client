package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Role information view object sys_role
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRole.class)
public class SysRoleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Role ID
     */
    @ExcelProperty(value = "Role ID")
    private Long roleId;

    /**
     * Role name
     */
    @ExcelProperty(value = "Role name")
    private String roleName;

    /**
     * Role key
     */
    @ExcelProperty(value = "Role key")
    private String roleKey;

    /**
     * Role sort
     */
    @ExcelProperty(value = "Role sort")
    private Integer roleSort;

    /**
     * Data scope (1: All data permissions 2: User-defined data permissions 3: Data permissions of this department 4: Data permissions of this department and below)
     */
    @ExcelProperty(value = "Data range", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=All data permissions, 2=Custom data permissions, 3=Data permissions of this department, 4=Data permissions of this department and below, 5=Only personal data permissions")
    private String dataScope;

    /**
     * Whether the menu tree selection items are displayed in association
     */
    @ExcelProperty(value = "Whether the menu tree selection items are displayed in association")
    private Boolean menuCheckStrictly;

    /**
     * Whether the selection items of the department tree are displayed in association
     */
    @ExcelProperty(value = "Whether the selection items of the department tree are displayed in association")
    private Boolean deptCheckStrictly;

    /**
     * Role status (0 normal 1 disabled)
     */
    @ExcelProperty(value = "Status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

    /**
     * Remark
     */
    @ExcelProperty(value = "Remark")
    private String remark;

    /**
     * Creation time
     */
    @ExcelProperty(value = "Creation time")
    private Date createTime;

    /**
     * Does the user have this role ID? Does not exist by default
     */
    private boolean flag = false;

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
    }

}
