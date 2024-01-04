package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysConfig;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * Parameter configuration view object sys_config
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysConfig.class)
public class SysConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * parameter primary key
     */
    @ExcelProperty(value = "Parameter ID")
    private Long configId;

    /**
     * parameter name
     */
    @ExcelProperty(value = "Parameter name")
    private String configName;

    /**
     * parameter key name
     */
    @ExcelProperty(value = "Parameter key")
    private String configKey;

    /**
     * parameter key
     */
    @ExcelProperty(value = "Parameter value")
    private String configValue;

    /**
     * System built-in (Y yes N no)
     */
    @ExcelProperty(value = "System built-in", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_yes_no")
    private String configType;

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
