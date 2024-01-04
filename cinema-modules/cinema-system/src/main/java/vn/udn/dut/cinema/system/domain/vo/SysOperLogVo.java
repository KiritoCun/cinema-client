package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysOperLog;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * Operational logging view object sys_oper_log
 *
 * @author HoaLD
 * @date 2023-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysOperLog.class)
public class SysOperLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * log primary key
     */
    @ExcelProperty(value = "Log ID")
    private Long operId;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * module title
     */
    @ExcelProperty(value = "Operation module")
    private String title;

    /**
     * Business Type (0 Others 1 Add 2 Modify 3 Delete)
     */
    @ExcelProperty(value = "Business type", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_oper_type")
    private Integer businessType;

    /**
     * Business type
     */
    private Integer[] businessTypes;

    /**
     * method name
     */
    @ExcelProperty(value = "Method")
    private String method;

    /**
     * request method
     */
    @ExcelProperty(value = "Request method")
    private String requestMethod;

    /**
     * Operation category (0 others 1 background user 2 mobile terminal user)
     */
    @ExcelProperty(value = "Operation type", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=Others, 1=System users, 2=Mobile users")
    private Integer operatorType;

    /**
     * operator
     */
    @ExcelProperty(value = "Operator")
    private String operName;

    /**
     * Department name
     */
    @ExcelProperty(value = "Department name")
    private String deptName;

    /**
     * Request URL
     */
    @ExcelProperty(value = "Operation URL")
    private String operUrl;

    /**
     * host address
     */
    @ExcelProperty(value = "Operation IP")
    private String operIp;

    /**
     * operating location
     */
    @ExcelProperty(value = "Operation location")
    private String operLocation;

    /**
     * Operation Param
     */
    @ExcelProperty(value = "Operation param")
    private String operParam;

    /**
     * return parameter
     */
    @ExcelProperty(value = "JSON result")
    private String jsonResult;

    /**
     * Operating status (0 normal 1 abnormal)
     */
    @ExcelProperty(value = "Status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private Integer status;

    /**
     * Error message
     */
    @ExcelProperty(value = "Error message")
    private String errorMsg;

    /**
     * operating time
     */
    @ExcelProperty(value = "Operation time")
    private Date operTime;

    /**
     * time consuming
     */
    @ExcelProperty(value = "Cost time")
    private Long costTime;
    
    /**
     * System type
     */
    @ExcelProperty(value = "System type", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_system_type")
    private String systemType;
}
