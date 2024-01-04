package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysPost;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * Post information view object sys_post
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysPost.class)
public class SysPostVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Job ID
     */
    @ExcelProperty(value = "Position ID")
    private Long postId;

    /**
     * Position code
     */
    @ExcelProperty(value = "Position code")
    private String postCode;

    /**
     * Position name
     */
    @ExcelProperty(value = "Position name")
    private String postName;

    /**
     * Position sort
     */
    @ExcelProperty(value = "Position sort")
    private Integer postSort;

    /**
     * Status (0 normal 1 disabled)
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

}
