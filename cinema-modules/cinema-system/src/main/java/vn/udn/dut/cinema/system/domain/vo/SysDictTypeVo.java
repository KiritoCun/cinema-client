package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysDictType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * Dictionary type view object sys_dict_type
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDictType.class)
public class SysDictTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * dictionary primary key
     */
    @ExcelProperty(value = "Dictionary ID")
    private Long dictId;

    /**
     * dictionary name
     */
    @ExcelProperty(value = "Dictionary name")
    private String dictName;

    /**
     * dictionary type
     */
    @ExcelProperty(value = "Dictionary type")
    private String dictType;

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
