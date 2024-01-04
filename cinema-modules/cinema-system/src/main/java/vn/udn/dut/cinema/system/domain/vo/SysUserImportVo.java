package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;

/**
 * User object import VO
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
// @Accessors(chain = true) // Import is not allowed and the set method will not be found
public class SysUserImportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    @ExcelProperty(value = "User ID")
    private Long userId;

    /**
     * Department ID
     */
    @ExcelProperty(value = "Department ID")
    private Long deptId;

    /**
     * user account
     */
    @ExcelProperty(value = "User account")
    private String userName;

    /**
     * User name
     */
    @ExcelProperty(value = "User name")
    private String nickName;

    /**
     * email
     */
    @ExcelProperty(value = "Email")
    private String email;

    /**
     * Phone number
     */
    @ExcelProperty(value = "Phone number")
    private String phonenumber;

    /**
     * user gender
     */
    @ExcelProperty(value = "User gender", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_user_sex")
    private String sex;

    /**
     * Account status (0 normal 1 disabled)
     */
    @ExcelProperty(value = "Status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

}
