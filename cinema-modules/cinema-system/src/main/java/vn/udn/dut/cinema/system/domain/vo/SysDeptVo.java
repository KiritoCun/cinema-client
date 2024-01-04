package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysDept;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Department view object sys_dept
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDept.class)
public class SysDeptVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * department id
     */
    @ExcelProperty(value = "Department id")
    private Long deptId;

    /**
     * parent department id
     */
    private Long parentId;

    /**
     * parent department name
     */
    private String parentName;

    /**
     * ancestor list
     */
    private String ancestors;

    /**
     * Department name
     */
    @ExcelProperty(value = "Department name")
    private String deptName;

    /**
     * display order
     */
    private Integer orderNum;

    /**
     * principal
     */
    @ExcelProperty(value = "Principal")
    private String leader;

    /**
     * contact number
     */
    @ExcelProperty(value = "Phone number")
    private String phone;

    /**
     * Email
     */
    @ExcelProperty(value = "Email")
    private String email;

    /**
     * Department status (0 normal 1 disabled)
     */
    @ExcelProperty(value = "Department status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

    /**
     * Creation time
     */
    @ExcelProperty(value = "Creation time")
    private Date createTime;

}
