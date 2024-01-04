package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysDictData;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * Dictionary data view object sys_dict_data
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDictData.class)
public class SysDictDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * dictionary encoding
     */
    @ExcelProperty(value = "Dictionary code")
    private Long dictCode;

    /**
     * dictionary sort
     */
    @ExcelProperty(value = "Dictionary sort")
    private Integer dictSort;

    /**
     * dictionary tag
     */
    @ExcelProperty(value = "Dictionary label")
    private String dictLabel;

    /**
     * Dictionary key value
     */
    @ExcelProperty(value = "Dictionary value")
    private String dictValue;

    /**
     * dictionary type
     */
    @ExcelProperty(value = "Dictionary type")
    private String dictType;

    /**
     * Style properties (additional style extensions)
     */
    private String cssClass;

    /**
     * Table echo style
     */
    private String listClass;

    /**
     * Is it the default (Y is N No)
     */
    @ExcelProperty(value = "Is default", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String isDefault;

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
