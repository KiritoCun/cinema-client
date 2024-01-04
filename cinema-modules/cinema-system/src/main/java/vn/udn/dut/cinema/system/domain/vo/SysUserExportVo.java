package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.ReverseAutoMapping;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * User object export VO
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@AutoMapper(target = SysUserVo.class, convertGenerate = false)
public class SysUserExportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    @ExcelProperty(value = "User ID")
    private Long userId;

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
     * Email
     */
    @ExcelProperty(value = "Email")
    private String email;

    /**
     * phone number
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

    /**
     * Last login IP
     */
    @ExcelProperty(value = "Last login IP")
    private String loginIp;

    /**
     * last login time
     */
    @ExcelProperty(value = "Login date")
    private Date loginDate;

    /**
     * Department name
     */
    @ExcelProperty(value = "Department name")
    private String deptName;

    /**
     * Leader
     */
    @ReverseAutoMapping(target = "leader", source = "dept.leader")
    @ExcelProperty(value = "Department head")
    private String leader;

}
